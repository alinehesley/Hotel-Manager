package Classes.graficas;

import Classes.*;
import Classes.exceptions.QuartoIndisponivelException;
import Classes.exceptions.QuartoNaoLocadoException;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class MenuQuartos extends MenuQuartosBase {
    public MenuQuartos(Hotel h) {
        // Configurações básicas da janela
        super(h, "Menu de quartos");

        JButton reservarButton = new JButton("Reservar quarto");
        reservarButton.addActionListener(e -> {
            Quarto quarto = (Quarto) listaQuartos.getSelectedValue();
            if (quarto == null || !quarto.ehDisponivel()) {
                JOptionPane.showMessageDialog(this, "Quarto não disponível");
                return;
            }

            ArrayList<Cliente> clientesTitulares = h.filtrarClientes(cliente -> (cliente instanceof ClienteTitular));

            SelecionadorCliente selecionadorCliente = new SelecionadorCliente(h);
            selecionadorCliente.setSelecaoCallback(cliente -> reservarQuarto(quarto, cliente));
            selecionadorCliente.setLista(clientesTitulares);

            selecionadorCliente.exibirMenu();
        });
        buttonPanel.add(reservarButton);

        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> fecharMenu(true));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(closeButton);
    }

    public boolean reservarQuarto(Quarto quarto, Cliente cliente) {
        quarto = (Quarto) Objects.requireNonNull(quarto);
        cliente = (Cliente) Objects.requireNonNull(cliente);

        if (!(cliente instanceof ClienteTitular)) {
            JOptionPane.showMessageDialog(this, "Cliente não é titular!", "Reservar quarto", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            quarto.fazerCheckIn((ClienteTitular) cliente, LocalDate.now(), LocalDate.MAX);
            JOptionPane.showMessageDialog(this, "Quarto reservado com sucesso");
            listaClientes.refresh();
            return true;
        } catch (QuartoIndisponivelException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Reservar quarto", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean encerrarReserva(Quarto quarto) {
        quarto = (Quarto) Objects.requireNonNull(quarto);
        if (quarto.ehDisponivel()) {
            JOptionPane.showMessageDialog(this, "Quarto não está locado", "Encerrar reserva", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        final ArrayList<Cliente> clientes = new ArrayList<>(quarto.getClientes());

        try {
            quarto.fazerCheckOut();
        } catch (QuartoNaoLocadoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Encerrar reserva", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int pagarConta = JOptionPane.showConfirmDialog(this, "Check-out realizado! Deseja pagar a conta agora?", "Pagar conta", JOptionPane.YES_NO_OPTION);
        if (pagarConta == JOptionPane.YES_OPTION) {
            MenuClientePagamento menuClientePagamento = new MenuClientePagamento(getHotel());
            menuClientePagamento.setLista(clientes);
            menuClientePagamento.exibirMenu();
        }

        return true;
    }
}