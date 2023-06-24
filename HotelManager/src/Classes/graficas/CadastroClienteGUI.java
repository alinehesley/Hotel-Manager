package Classes.graficas;

import Classes.Hotel;

import javax.swing.*;

import java.time.LocalDate;

import static Classes.graficas.Book.alturaPadrao;
import static Classes.graficas.Book.larguraPadrao;

public class CadastroClienteGUI extends JFrame {
    private JTextField nomeTextField;
    private JTextField cpfTextField;
    private JTextField cpfTitularTextField; // vazio se for titular
    private JTextField nascimentoTextField;

    public CadastroClienteGUI(Hotel h) {
        // Configurações básicas da janela
        setTitle("Cadastro de Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(larguraPadrao, alturaPadrao);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Painel do nome
        JPanel nomePanel = new JPanel();
        nomePanel.add(new JLabel("Nome:"));
        nomeTextField = new JTextField(20);
        nomePanel.add(nomeTextField);

        // Painel do CPF
        JPanel cpfPanel = new JPanel();
        cpfPanel.add(new JLabel("CPF:"));
        cpfTextField = new JTextField(14);
        cpfPanel.add(cpfTextField);

        // Painel do CPF do titular
        JPanel cpfTitularPanel = new JPanel();
        cpfTitularPanel.add(new JLabel("CPF do titular (vazio se titular):"));
        cpfTitularTextField = new JTextField(14);
        cpfTitularPanel.add(cpfTitularTextField);

        // Painel do nascimento
        JPanel nascimentoPanel = new JPanel();
        nascimentoPanel.add(new JLabel("Data de nascimento:"));
        nascimentoTextField = new JTextField(14);
        nascimentoPanel.add(nascimentoTextField);

        // Botão de cadastro
        JPanel buttonPanel = new JPanel();
        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(e -> cadastrarCliente(h));
        buttonPanel.add(cadastrarButton);

        // Adiciona os painéis ao painel principal
        mainPanel.add(nomePanel);
        mainPanel.add(cpfPanel);
        mainPanel.add(cpfTitularPanel);
        mainPanel.add(nascimentoPanel);
        mainPanel.add(buttonPanel);

        // Adiciona o painel principal à janela
        add(mainPanel);
    }

    private void cadastrarCliente(Hotel h) {
        try{
            String nome = nomeTextField.getText();
            String cpf = cpfTextField.getText();
            String cpfTitular = cpfTitularTextField.getText();
            LocalDate dataNascimento =LocalDate.parse(nascimentoTextField.getText());

            if(cpfTitular.length() < 10){
                // Se o cpf do titular for vazio, o cliente é o titular
                h.cadastrarClienteTitular(nome, cpf, dataNascimento);
            }else{
                h.cadastrarClienteDependente(nome, cpf, dataNascimento, cpfTitular);
            }

            System.out.println("Cliente " + nome +  " cadastrado com sucesso!");
        }catch (Exception e){
            System.out.println("Erro ao cadastrar cliente");
        }
        fecharJanela();
    }

    private void fecharJanela() {
        setVisible(false);
        dispose();
    }

}
