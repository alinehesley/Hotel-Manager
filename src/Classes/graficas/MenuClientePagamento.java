package Classes.graficas;

import Classes.Cliente;
import Classes.Hotel;

import javax.swing.*;
import java.util.List;

public class MenuClientePagamento extends MenuClientesBase {
    public MenuClientePagamento(Hotel h) {
        // Configurações básicas da janela
        super(h, "Seleção de cliente");

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

        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> fecharMenu(false));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(closeButton);
    }


}
