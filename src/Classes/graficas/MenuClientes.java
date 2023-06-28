package Classes.graficas;

import Classes.Cliente;
import Classes.ClienteTitular;
import Classes.Hotel;
import Classes.Arquivos;
import Classes.exceptions.ClienteException;
import Classes.helpers.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MenuClientes extends MenuClientesBase {
    public MenuClientes(Hotel h) {
        // Configurações básicas da janela
        super(h, "Menu de clientes");

        JButton cadastrarClienteButton = new JButton("Cadastrar");
        cadastrarClienteButton.addActionListener(e -> {
            MenuCadastroCliente menuCadastroCliente = new MenuCadastroCliente(getHotel());
            menuCadastroCliente.setParent(this);
            menuCadastroCliente.exibirMenu();
        });
        buttonPanel.add(cadastrarClienteButton);

        JButton removerClienteButton = new JButton("Remover");
        removerClienteButton.addActionListener(e -> {
            Cliente cliente = clientePainel.getCliente();
            if (cliente != null) {
                int result = JOptionPane.showConfirmDialog(
                        null,
                        "Tem certeza que deseja remover o cliente " + cliente.getNome() + "?",
                        "Remover cliente",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        getHotel().removerCliente(cliente);
                    } catch (ClienteException ex) {
                        JOptionPane.showMessageDialog(
                                null,
                                ex.getMessage(),
                                "Remover cliente",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                    clientePainel.setCliente(null);
                    listaClientes.refresh();
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Selecione um cliente para remover",
                        "Remover cliente",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });
        buttonPanel.add(removerClienteButton);
        removerClienteButton.setVisible(false);

        JButton reservarQuartoButton = new JButton("Reservar quarto");
        reservarQuartoButton.addActionListener(e -> {
            Cliente cliente = clientePainel.getCliente();
            if (cliente == null)
                return;

            MenuReserva menuReserva = new MenuReserva(getHotel());
            menuReserva.setParent(this);
            menuReserva.getCpfField().getField().setText(cliente.getCpfFormatado());
            menuReserva.exibirMenu();
        });
        buttonPanel.add(reservarQuartoButton);
        reservarQuartoButton.setVisible(false);

        JButton pagarContaButton = new JButton("Pagamento");
        pagarContaButton.addActionListener(e -> {
            Cliente cliente = clientePainel.getCliente();
            if (cliente == null)
                return;

            if (cliente.getConta() < 0.01)
                return;

            int pagarConta = JOptionPane.showConfirmDialog(this, String.format("Confirmar pagamento de R$ %.2f?", cliente.getConta()), "Pagamento", JOptionPane.YES_NO_OPTION);
            if (pagarConta == JOptionPane.YES_OPTION) {
                cliente.pagarConta();
            }

            clientePainel.refresh();
        });
        buttonPanel.add(pagarContaButton);
        pagarContaButton.setVisible(false);

        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> {
            h.salvaArquivosCliente();
            fecharMenu(false);
        });
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(closeButton);

        clientePainel.addCallback(cliente -> {
            removerClienteButton.setVisible(cliente != null);
            pagarContaButton.setVisible(cliente != null);
            reservarQuartoButton.setVisible(cliente != null && (cliente instanceof ClienteTitular) && cliente.getQuarto() == null);
        });
    }
}
