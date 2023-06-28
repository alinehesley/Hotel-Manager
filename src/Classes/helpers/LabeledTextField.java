package Classes.helpers;

import javax.swing.*;

// Essa classe é um wrapper para um JTextField com um JLabel.
public class LabeledTextField extends JPanel {
    private final JLabel label;
    private final JTextField textField;

    public LabeledTextField() {
        label = new JLabel();
        textField = new JTextField();

        this.add(label);
        this.add(textField);
    }

    public LabeledTextField(String labelText, int columns) {
        label = new JLabel(labelText);
        textField = new JTextField(columns);

        this.add(label);
        this.add(textField);
    }

    public JLabel getLabel() {
        return label;
    }

    public JTextField getField() {
        return textField;
    }
}
