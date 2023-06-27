package Classes.graficas;

import Classes.Cliente;
import Classes.Hotel;

import javax.swing.*;
import java.util.List;

public class MenuClientePagamento extends MenuClientesBase {
    public MenuClientePagamento(Hotel h) {
        // Configurações básicas da janela
        super(h, "Seleção de cliente");

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
