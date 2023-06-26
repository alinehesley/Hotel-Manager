package Classes.graficas;

import Classes.Hotel;
import Classes.exceptions.ClienteException;
import Classes.helpers.DateParser;

import javax.swing.*;

import java.time.LocalDate;

public class MenuCadastroCliente extends MenuBase {
    private JTextField nomeTextField;
    private JTextField cpfTextField;
    private JTextField cpfTitularTextField; // vazio se for titular
    private JTextField nascimentoTextField;

    public MenuCadastroCliente(Hotel hotel) {
        // Configurações básicas da janela
        super(hotel, "Cadastro de Cliente");

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
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(e -> cadastrarCliente());
        buttonPanel.add(cadastrarButton);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> fecharMenu(false));
        buttonPanel.add(voltarButton);

        // TODO: REMOVER ESSE DEBUG
        JButton cadastrarButtonDebug = new JButton("Cadastrar DEBUG");
        cadastrarButtonDebug.addActionListener(e -> cadastrarClienteDebug());
        buttonPanel.add(cadastrarButtonDebug);

        // Adiciona os painéis ao painel principal
        mainPanel.add(nomePanel);
        mainPanel.add(cpfPanel);
        mainPanel.add(cpfTitularPanel);
        mainPanel.add(nascimentoPanel);
        mainPanel.add(buttonPanel);

        // Adiciona o painel principal à janela
        add(mainPanel);
    }

    private void cadastrarCliente() {
        try {
            String nome = nomeTextField.getText();
            String cpf = cpfTextField.getText();
            String cpfTitular = cpfTitularTextField.getText().replaceAll("[^0-9]", "");
            LocalDate dataNascimento = DateParser.parse(nascimentoTextField.getText());

            if (cpfTitular.length() == 0) {
                // Se o cpf do titular for vazio, o cliente é o titular
                getHotel().cadastrarClienteTitular(nome, cpf, dataNascimento);
            } else {
                getHotel().cadastrarClienteDependente(nome, cpf, dataNascimento, cpfTitular);
            }

            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            fecharMenu(true);
        } catch (Exception e) {
            // TODO(thiago): Dar mensagens de erro mais específicas
            // TODO(thiago): Conferir se todos os erros são tratados, olhar as outras classes.

            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // TODO: APAGA ISSO
    private void cadastrarClienteDebug() {
        try {
            String nome;
            // Gerar um nome aleatório de 5 a 10 caracteres
            int tamanhoNome = (int) (Math.random() * 6) + 5;
            nome = "";
            for (int i = 0; i < tamanhoNome; i++) {
                nome += (char) ((int) (Math.random() * 26) + 97);
            }

            // Gerar um cpf aleatório
            String cpf = "";
            for (int i = 0; i < 9; i++) {
                cpf += (int) (Math.random() * 10);
            }
            int d1 = CalculaDigito1Cpf(cpf);
            int d2 = CalculaDigito2Cpf(cpf);
            cpf += d1;
            cpf += d2;

            String cpfTitular = cpfTitularTextField.getText().replaceAll("[^0-9]", "");
            LocalDate dataNascimento = DateParser.parse("01/01/1965");

            if (cpfTitular.length() == 0) {
                // Se o cpf do titular for vazio, o cliente é o titular
                getHotel().cadastrarClienteTitular(nome, cpf, dataNascimento);
            } else {
                getHotel().cadastrarClienteDependente(nome, cpf, dataNascimento, cpfTitular);
            }

            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            fecharMenu(true);
        } catch (Exception e) {
            // TODO(thiago): Dar mensagens de erro mais específicas
            // TODO(thiago): Conferir se todos os erros são tratados, olhar as outras classes.

            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // TODO: APAGA ISSO
    private static int CalculaDigito1Cpf(String CPF) {
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (10 - i) * (int) (CPF.charAt(i) - 48);
        }
        int resto = soma % 11;
        if (resto == 0 || resto == 1) {
            return 0;
        }
        return 11 - resto;
    }

    // TODO: APAGA ISSO
    private static int CalculaDigito2Cpf(String CPF) {
        int digito1 = CalculaDigito1Cpf(CPF);
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (11 - i) * (int) (CPF.charAt(i) - 48);
        }
        soma += 2 * digito1;
        int resto = soma % 11;
        if (resto == 0 || resto == 1) {
            return 0;
        }
        return 11 - resto;
    }
}
