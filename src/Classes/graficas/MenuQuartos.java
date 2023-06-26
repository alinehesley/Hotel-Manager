package Classes.graficas;

import Classes.Cliente;
import Classes.ClienteTitular;
import Classes.Hotel;
import Classes.Quarto;
import Classes.exceptions.QuartoIndisponivelException;

import javax.swing.*;
import java.time.LocalDate;
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

            SelecionadorCliente selecionadorCliente = new SelecionadorCliente(h);
            selecionadorCliente.setSelecaoCallback(cliente -> reservarQuarto(quarto, cliente));

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
            JOptionPane.showMessageDialog(this, "Cliente não é titular!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            quarto.fazerCheckIn((ClienteTitular) cliente, LocalDate.now(), LocalDate.MAX);
            JOptionPane.showMessageDialog(this, "Quarto reservado com sucesso");
            listaClientes.refresh();
            return true;
        } catch (QuartoIndisponivelException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
