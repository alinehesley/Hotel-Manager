package Classes.graficas;

import Classes.Cliente;
import Classes.Hotel;
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

        JButton cadastrarClienteButton = new JButton("Cadastrar Cliente");
        cadastrarClienteButton.addActionListener(e -> {
            MenuCadastroCliente menuCadastroCliente = new MenuCadastroCliente(getHotel());
            menuCadastroCliente.setParent(this);
            menuCadastroCliente.exibirMenu();
        });
        buttonPanel.add(cadastrarClienteButton);

        JButton removerClienteButton = new JButton("Remover Cliente");
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
                    clientePainel.refresh();
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

        // TODO(thiago): Menu para pagar as contas
        JButton pagarContaButton = new JButton("Pagar Conta");
        pagarContaButton.addActionListener(e -> {
            Cliente cliente = clientePainel.getCliente();
            if (cliente != null)
                cliente.pagarConta();

            clientePainel.refresh();
        });
        buttonPanel.add(pagarContaButton);

        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> fecharMenu(false));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(closeButton);
    }
}
