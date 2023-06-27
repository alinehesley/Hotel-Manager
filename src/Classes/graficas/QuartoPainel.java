package Classes.graficas;

import Classes.ClienteDependente;
import Classes.Quarto;
import Classes.helpers.DateParser;
import Classes.helpers.Utils;

import javax.swing.*;

// TODO(thiago): Usar JTextField ao invés de JLabel para facilitar a seleção.
public class QuartoPainel extends JPanel {
    private Quarto quarto;
    private JLabel labelNumero;
    private JLabel labelData;
    private JLabel labelTitular;
    private JLabel labelDisponivel;

    public QuartoPainel(Quarto quarto) {
        this.initializeLabels();

        this.add(labelNumero);
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
            labelData.setText("Data: ");
            labelTitular.setText("Titular: ");
            labelDisponivel.setText("Disponível: ");
            return;
        }

        labelNumero.setText("Número: " + quarto.getNumero());

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
    }

    private void initializeLabels() {
        labelNumero = new JLabel();
        labelData = new JLabel();
        labelTitular = new JLabel();
        labelDisponivel = new JLabel();

        labelNumero.addMouseListener(Utils.onMouseClick(e -> {
            if (quarto != null)
                Utils.copyToClipboard(String.valueOf(quarto.getNumero()));
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
