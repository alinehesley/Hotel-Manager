package Classes.graficas;

import Classes.Hotel;
import Classes.helpers.DateParser;
import Classes.helpers.LabeledTextField;

import javax.swing.*;

import java.awt.*;
import java.time.LocalDate;

public class MenuCadastroCliente extends MenuBase {
    private LabeledTextField nomeTextField;
    private LabeledTextField cpfTextField;
    private LabeledTextField cpfTitularTextField; // vazio se for titular
    private LabeledTextField nascimentoTextField;

    public MenuCadastroCliente(Hotel hotel) {
        // Configurações básicas da janela
        super(hotel, "Cadastro de Cliente");

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Painel do nome
        JPanel nomePanel = new JPanel();
        nomePanel.setLayout(new BoxLayout(nomePanel, BoxLayout.Y_AXIS));
        nomeTextField = new LabeledTextField("Nome:", 20);
        nomeTextField.setMaximumSize(nomeTextField.getPreferredSize());
        nomePanel.add(nomeTextField);

        // Painel do CPF
        JPanel cpfPanel = new JPanel();
        cpfPanel.setLayout(new BoxLayout(cpfPanel, BoxLayout.Y_AXIS));
        cpfTextField = new LabeledTextField("CPF:", 14);
        cpfTextField.setMaximumSize(cpfTextField.getPreferredSize());
        cpfPanel.add(cpfTextField);

        // Painel do CPF do titular
        JPanel cpfTitularPanel = new JPanel();
        cpfTitularPanel.setLayout(new BoxLayout(cpfTitularPanel, BoxLayout.Y_AXIS));
        cpfTitularTextField = new LabeledTextField("CPF do titular (vazio se titular):", 14);
        cpfTitularTextField.setMaximumSize(cpfTitularTextField.getPreferredSize());
        cpfTitularTextField.getLabel().setHorizontalAlignment(JLabel.CENTER);
        cpfTitularPanel.add(cpfTitularTextField);

        // Painel do nascimento
        JPanel nascimentoPanel = new JPanel();
        nascimentoPanel.setLayout(new BoxLayout(nascimentoPanel, BoxLayout.Y_AXIS));
        nascimentoTextField = new LabeledTextField("Data de nascimento:", 14);
        nascimentoTextField.setMaximumSize(nascimentoTextField.getPreferredSize());
        nascimentoPanel.add(nascimentoTextField);

        // Botão de cadastro
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(e -> cadastrarCliente());
        buttonPanel.add(cadastrarButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> fecharMenu(false));
        buttonPanel.add(voltarButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        // TODO: REMOVER ESSE DEBUG
        JButton cadastrarButtonDebug = new JButton("Cadastrar DEBUG");
        cadastrarButtonDebug.addActionListener(e -> debugInfo());
        buttonPanel.add(cadastrarButtonDebug);

        buttonPanel.add(Box.createHorizontalGlue());

        // Adiciona os painéis ao painel principal
        contentPanel.add(nomePanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(cpfPanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(cpfTitularPanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(nascimentoPanel);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(contentPanel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(buttonPanel);

        // Adiciona o painel principal à janela
        add(mainPanel);
    }

    private void cadastrarCliente() {
        try {
            String nome = nomeTextField.getField().getText();
            String cpf = cpfTextField.getField().getText();
            String cpfTitular = cpfTitularTextField.getField().getText().replaceAll("[^0-9]", "");
            LocalDate dataNascimento = DateParser.parse(nascimentoTextField.getField().getText());

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

    // DEBUG
    private void debugInfo() {
        String nome = "";

        // Gerar um nome aleatório de 5 a 10 caracteres, alternando entre vogais e consoantes e com a primeira letra maiúscula
        int tamanho = (int) (Math.random() * 6 + 5);
        for (int i = 0; i < tamanho; i++) {
            nome += (char) (Math.random() * 26 + 97);
        }
        nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);
        nomeTextField.getField().setText(nome);

        // Gerar um cpf aleatório
        String cpf = "";
        for (int i = 0; i < 9; i++) {
            cpf += (int) (Math.random() * 10);
        }
        int d1 = calculaDigito1Cpf(cpf);
        int d2 = calculaDigito2Cpf(cpf);
        cpf += d1;
        cpf += d2;
        cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
        cpfTextField.getField().setText(cpf);

        LocalDate dataNascimento;
        int ano = (int) (Math.random() * 100 + 1900);
        int mes = (int) (Math.random() * 12 + 1);
        int dia = (int) (Math.random() * 28 + 1);
        nascimentoTextField.getField().setText(LocalDate.of(ano, mes, dia).format(DateParser.FORMATTER));
    }

    // DEBUG
    private static int calculaDigito1Cpf(String CPF) {
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

    // DEBUG
    private static int calculaDigito2Cpf(String CPF) {
        int digito1 = calculaDigito1Cpf(CPF);
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
