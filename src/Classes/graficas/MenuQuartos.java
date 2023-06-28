package Classes.graficas;

import Classes.*;
import Classes.exceptions.ClienteException;
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

            MenuReserva menuReserva = new MenuReserva(h);
            menuReserva.setParent(this);
            menuReserva.getNumeroField().getField().setText(Integer.toString(quarto.getNumero()));
            menuReserva.exibirMenu();
        });
        buttonPanel.add(reservarButton);
        reservarButton.setVisible(false);

        JButton encerrarReserva = new JButton("Encerrar reserva");
        encerrarReserva.addActionListener(e -> {
            Quarto quarto = (Quarto) listaQuartos.getSelectedValue();
            encerrarReserva(quarto);
            refresh();
        });
        buttonPanel.add(encerrarReserva);
        encerrarReserva.setVisible(false);

        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> {
            fecharMenu(true);
        });
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(closeButton);

        quartoPainel.addCallback(quarto -> {
            reservarButton.setVisible(quarto != null && quarto.ehDisponivel());
            encerrarReserva.setVisible(quarto != null && !quarto.ehDisponivel());
        });
    }

    public boolean encerrarReserva(Quarto quarto) {
        quarto = (Quarto) Objects.requireNonNull(quarto);
        if (quarto.ehDisponivel()) {
            JOptionPane.showMessageDialog(this, "Quarto não está locado", "Encerrar reserva", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        final ArrayList<Cliente> clientes = new ArrayList<>(quarto.getClientes());

        try {
            getHotel().encerrarReserva(quarto, 0.0);
        } catch (QuartoNaoLocadoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Encerrar reserva", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int pagarConta = JOptionPane.showConfirmDialog(this, "Check-out realizado! Deseja pagar a conta agora?", "Pagar conta", JOptionPane.YES_NO_OPTION);
        if (pagarConta == JOptionPane.YES_OPTION) {
            MenuClientePagamento menuClientePagamento = new MenuClientePagamento(getHotel());
            menuClientePagamento.setLista(clientes);
            menuClientePagamento.setParent(this);
            menuClientePagamento.exibirMenu();
        }

        return true;
    }
}