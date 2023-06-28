package Classes.graficas;

import Classes.Hotel;
import Classes.Quarto;

import javax.swing.*;
import java.util.List;

public class SelecionadorQuarto extends MenuQuartosBase {
    public interface SelecaoQuartoCallback {
        boolean onQuartoSelecionado(Quarto quarto);
    };
    private SelecaoQuartoCallback selecaoCallback;

    public SelecionadorQuarto(Hotel h) {
        // Configurações básicas da janela
        super(h, "Seleção de quarto");
        selecaoCallback = null;

        JButton selecionarButton = new JButton("Selecionar");
        selecionarButton.addActionListener(e -> {
            Quarto quarto = quartoPainel.getQuarto();
            if (quarto != null && selecaoCallback != null) {
                if (selecaoCallback.onQuartoSelecionado(quarto))
                    fecharMenu(false);
            }
        });
        buttonPanel.add(selecionarButton);

        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> fecharMenu(false));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(closeButton);
    }

    public void setLista(List<Quarto> lista) {
        listaQuartos.setLista(lista);
    }

    public void setSelecaoCallback(SelecaoQuartoCallback callback) {
        this.selecaoCallback = callback;
    }
    public void clearSelecaoCallback() {
        this.selecaoCallback = null;
    }
}
