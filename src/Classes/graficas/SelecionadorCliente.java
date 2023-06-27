package Classes.graficas;

import Classes.Cliente;
import Classes.Hotel;

import javax.swing.*;
import java.util.List;

public class SelecionadorCliente extends MenuClientesBase {
    public interface SelecaoClienteCallback {
        boolean onClienteSelecionado(Cliente cliente);
    };
    private SelecaoClienteCallback selecaoCallback;

    public SelecionadorCliente(Hotel h) {
        // Configurações básicas da janela
        super(h, "Seleção de cliente");
        selecaoCallback = null;

        JButton selecionarButton = new JButton("Selecionar");
        selecionarButton.addActionListener(e -> {
            Cliente cliente = clientePainel.getCliente();
            if (cliente != null && selecaoCallback != null) {
                if (selecaoCallback.onClienteSelecionado(cliente))
                    fecharMenu(false);
            }
        });
        buttonPanel.add(selecionarButton);

        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> fecharMenu(false));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(closeButton);
    }

    public void setLista(List<Cliente> lista) {
        listaClientes.setLista(lista);
    }

    public void setSelecaoCallback(SelecaoClienteCallback callback) {
        this.selecaoCallback = callback;
    }
    public void clearSelecaoCallback() {
        this.selecaoCallback = null;
    }
}
