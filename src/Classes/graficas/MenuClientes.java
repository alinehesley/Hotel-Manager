package Classes.graficas;

import Classes.Cliente;
import Classes.Hotel;
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

//        JButton removerClienteButton = new JButton("Remover Cliente");
//        removerClienteButton.addActionListener(e -> {
//
//        });
//        buttonPanel.add(removerClienteButton);

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
