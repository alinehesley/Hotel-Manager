package Classes.graficas;

import Classes.Quarto;
import Classes.helpers.DateParser;
import Classes.helpers.Utils;

import javax.swing.*;
import java.util.ArrayList;

// TODO(thiago): Usar JTextField ao invés de JLabel para facilitar a seleção.
public class QuartoPainel extends JPanel {
    public interface QuartoCallback {
        void onUpdate(Quarto quarto);
    }

    private Quarto quarto;
    private JLabel labelNumero;
    private JLabel labelCamaSolteiro;
    private JLabel labelCamaCasal;
    private JLabel labelData;
    private JLabel labelTitular;
    private JLabel labelDisponivel;
    private ArrayList<QuartoCallback> callbacks;

    public QuartoPainel(Quarto quarto) {
        this.callbacks = new ArrayList<>();
        this.initializeLabels();

        this.add(labelNumero);
        this.add(labelCamaSolteiro);
        this.add(labelCamaCasal);
        this.add(labelData);
        this.add(labelTitular);
        this.add(labelDisponivel);

        this.setQuarto(quarto);
    }

    public QuartoPainel() {
        this(null);
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
        refresh();
    }

    public void refresh() {
        if (quarto == null) {
            labelNumero.setText("Número: ");
            labelCamaSolteiro.setText("Camas de solteiro: ");
            labelCamaCasal.setText("Camas de casal: ");
            labelData.setText("Data: ");
            labelTitular.setText("Titular: ");
            labelDisponivel.setText("Disponível: ");

            for (QuartoCallback callback : callbacks)
                callback.onUpdate(quarto);
            return;
        }

        labelNumero.setText("Número: " + quarto.getNumero());
        labelCamaSolteiro.setText("Camas de solteiro: " + quarto.getTotalCamaSolteiro());
        labelCamaCasal.setText("Camas de casal: " + quarto.getTotalCamaCasal());

        if (quarto.getTitular() != null) {
            if (quarto.getDataEntrada() == null || quarto.getDataSaida() == null)
                throw new RuntimeException("Quarto com titular mas sem data de entrada ou saída");

            labelData.setText("Data: " + quarto.getDataEntrada().format(DateParser.FORMATTER) + " - " + quarto.getDataSaida().format(DateParser.FORMATTER));
            labelTitular.setText("Titular: " + quarto.getTitular().getNome());
            labelData.setVisible(true);
            labelTitular.setVisible(true);
            labelDisponivel.setText("Disponível: Não");
        } else {
            labelData.setText("Data: ");
            labelTitular.setText("Titular: ");
            labelData.setVisible(false);
            labelTitular.setVisible(false);
            labelDisponivel.setText("Disponível: Sim");
        }

        for (QuartoCallback callback : callbacks)
            callback.onUpdate(quarto);
    }

    public void addCallback(QuartoCallback callback) {
        this.callbacks.add(callback);
    }

    public void removeCallback(QuartoCallback callback) {
        this.callbacks.remove(callback);
    }

    public void clearCallbacks() {
        this.callbacks.clear();
    }

    private void initializeLabels() {
        labelNumero = new JLabel();
        labelCamaSolteiro = new JLabel();
        labelCamaCasal = new JLabel();
        labelData = new JLabel();
        labelTitular = new JLabel();
        labelDisponivel = new JLabel();

        labelNumero.addMouseListener(Utils.onMouseClick(e -> {
            if (quarto != null)
                Utils.copyToClipboard(String.valueOf(quarto.getNumero()));
        }));
        labelCamaSolteiro.addMouseListener(Utils.onMouseClick(e -> {
            if (quarto != null)
                Utils.copyToClipboard(String.valueOf(quarto.getTotalCamaSolteiro()));
        }));
        labelCamaCasal.addMouseListener(Utils.onMouseClick(e -> {
            if (quarto != null)
                Utils.copyToClipboard(String.valueOf(quarto.getTotalCamaCasal()));
        }));
        labelData.addMouseListener(Utils.onMouseClick(e -> {
            if (quarto != null && quarto.getDataEntrada() != null && quarto.getDataSaida() != null)
                Utils.copyToClipboard("" + quarto.getDataEntrada().format(DateParser.FORMATTER) + " - " + quarto.getDataSaida().format(DateParser.FORMATTER));
        }));
        labelTitular.addMouseListener(Utils.onMouseClick(e -> {
            if (quarto != null)
                Utils.copyToClipboard("" + quarto.getDataEntrada().format(DateParser.FORMATTER) + " - " + quarto.getDataSaida().format(DateParser.FORMATTER));
        }));
    }
}
