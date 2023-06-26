package Classes.graficas;

import Classes.Hotel;

import javax.swing.*;

public class MenuCheckIn extends MenuBase {
    private JTextField cpfTextField;
    private JTextField camasSolteiroTextField;
    private JTextField camasCasalTextField;

    public MenuCheckIn(Hotel h){
        super(h, "Menu Check-in");

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Painel do CPF
        JPanel cpfPanel = new JPanel();
        cpfPanel.add(new JLabel("CPF:"));
        cpfTextField = new JTextField(14);
        cpfPanel.add(cpfTextField);

        // Painel do numero de camas de solteiro
        JPanel camasSolteiroPanel = new JPanel();
        camasSolteiroPanel.add(new JLabel("Numero de camas de solteiro:"));
        camasSolteiroTextField = new JTextField(14);
        camasSolteiroPanel.add(camasSolteiroTextField);

        JPanel camasCasalPanel = new JPanel();
        camasCasalPanel.add(new JLabel("Numero de camas de casal:"));
        camasCasalTextField = new JTextField(14);
        camasCasalPanel.add(camasCasalTextField);

        // Botão de cadastro
        JPanel buttonPanel = new JPanel();
        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(e -> checkIn(h));
        buttonPanel.add(cadastrarButton);

        // Adiciona os painéis ao painel principal
        mainPanel.add(cpfPanel);
        mainPanel.add(camasSolteiroPanel);
        mainPanel.add(camasCasalPanel);
        mainPanel.add(buttonPanel);

        // Adiciona o painel principal à janela
        add(mainPanel);
    }

    private void checkIn(Hotel h){
//        try {
//            String cpf = cpfTextField.getText().replaceAll("[^0-9]", "");
//            int camasSolteiro = Integer.parseInt(camasSolteiroTextField.getText());
//            int camasCasal = Integer.parseInt(camasCasalTextField.getText());
//            if (h.reservarQuarto(cpf, camasSolteiro, camasCasal)) {
//                JOptionPane.showMessageDialog(null, "Check-in realizado com sucesso!");
//            } else {
//                JOptionPane.showMessageDialog(null, "Erro ao realizar check-in!");
//            }
//        } catch (Exception e){
//            JOptionPane.showMessageDialog(null, "Erro ao realizar check-in!");
//        }
        fecharMenu(false);
    }
}
