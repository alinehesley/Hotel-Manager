package Classes.graficas;

import Classes.Cliente;
import Classes.ClienteTitular;
import Classes.Hotel;
import Classes.Quarto;
import Classes.exceptions.CPFInvalidoException;
import Classes.exceptions.ClienteException;
import Classes.exceptions.QuartoException;
import Classes.helpers.DateParser;
import Classes.helpers.LabeledTextField;

import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MenuReserva extends MenuBase {
    private LabeledTextField numeroField;
    private LabeledTextField cpfField;
    private LabeledTextField dataEntradaField;
    private LabeledTextField dataSaidaField;

    public MenuReserva(Hotel hotel) {
        // Configurações básicas da janela
        super(hotel, "Reserva de Quarto");

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Painel do número
        JPanel numeroPanel = new JPanel();
        numeroPanel.setLayout(new BoxLayout(numeroPanel, BoxLayout.Y_AXIS));
        numeroField = new LabeledTextField("         Número:", 14);
        numeroField.setMinimumSize(numeroField.getPreferredSize());
        numeroField.setMaximumSize(numeroField.getPreferredSize());
        numeroPanel.add(numeroField);
        JButton numeroButton = new JButton("Selecionar");
        numeroButton.addActionListener(e -> selecionarQuarto());
        numeroButton.setMaximumSize(numeroButton.getPreferredSize());
        numeroPanel.add(numeroButton);

        // Painel do CPF
        JPanel cpfPanel = new JPanel();
        cpfPanel.setLayout(new BoxLayout(cpfPanel, BoxLayout.Y_AXIS));
        cpfField = new LabeledTextField(" CPF do Titular:", 14);
        cpfField.setMaximumSize(cpfField.getPreferredSize());
        cpfField.setMinimumSize(cpfField.getPreferredSize());
        cpfPanel.add(cpfField);
        JButton cpfButton = new JButton("Selecionar");
        cpfButton.addActionListener(e -> selecionarCliente());
        cpfButton.setMaximumSize(cpfButton.getPreferredSize());
        cpfPanel.add(cpfButton);

        // Painel da data de entrada
        JPanel dataEntradaPanel = new JPanel();
        dataEntradaPanel.setLayout(new BoxLayout(dataEntradaPanel, BoxLayout.Y_AXIS));
        dataEntradaField = new LabeledTextField("Data de Entrada:", 14);
        dataEntradaField.setMinimumSize(dataEntradaField.getPreferredSize());
        dataEntradaField.setMaximumSize(dataEntradaField.getPreferredSize());
        dataEntradaPanel.add(dataEntradaField);
        JButton dataEntradaButton = new JButton("Hoje");
        dataEntradaButton.setPreferredSize(cpfButton.getPreferredSize());
        dataEntradaButton.addActionListener(e -> dataEntradaField.getField().setText(LocalDate.now().format(DateParser.FORMATTER)));
        dataEntradaButton.setMaximumSize(dataEntradaButton.getPreferredSize());
        dataEntradaPanel.add(dataEntradaButton);

        // Painel da data de saida
        JPanel dataSaidaPanel = new JPanel();
        dataSaidaPanel.setLayout(new BoxLayout(dataSaidaPanel, BoxLayout.Y_AXIS));
        dataSaidaField = new LabeledTextField("  Data de Saída:", 14);
        dataSaidaField.setMinimumSize(dataSaidaField.getPreferredSize());
        dataSaidaField.setMaximumSize(dataSaidaField.getPreferredSize());
        dataSaidaPanel.add(dataSaidaField);
        JButton dataSaidaButton = new JButton("Amanhã");
        dataSaidaButton.setPreferredSize(cpfButton.getPreferredSize());
        dataSaidaButton.addActionListener(e -> dataSaidaField.getField().setText(LocalDate.now().plusDays(1).format(DateParser.FORMATTER)));
        dataSaidaButton.setMaximumSize(dataSaidaButton.getPreferredSize());
        dataSaidaPanel.add(dataSaidaButton);

        // Botão de cadastro
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());

        JButton reservarButton = new JButton("Reservar");
        reservarButton.addActionListener(e -> reservarQuarto());
        buttonPanel.add(reservarButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> fecharMenu(false));
        buttonPanel.add(voltarButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        buttonPanel.add(Box.createHorizontalGlue());

        // Adiciona os painéis ao painel principal
        contentPanel.add(numeroPanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(cpfPanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(dataEntradaPanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(dataSaidaPanel);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(contentPanel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(buttonPanel);

        // Adiciona o painel principal à janela
        add(mainPanel);
    }

    public void selecionarCliente() {
        ArrayList<Cliente> clientesDisponiveis = getHotel().filtrarClientes(cliente ->
                (cliente instanceof ClienteTitular) && cliente.getQuarto() == null);

        SelecionadorCliente selecionadorCliente = new SelecionadorCliente(getHotel());
        selecionadorCliente.setSelecaoCallback(cliente -> {
            cpfField.getField().setText(cliente.getCpfFormatado());
            return true;
        });
        selecionadorCliente.setLista(clientesDisponiveis);
        selecionadorCliente.setParent(this);

        selecionadorCliente.exibirMenu();
    }

    public void selecionarQuarto() {
        final Cliente cliente = getHotel().getClienteFromCpf(cpfField.getField().getText());
        final ClienteTitular clienteTitular = (cliente instanceof ClienteTitular) ? (ClienteTitular) cliente : null;

        ArrayList<Quarto> quartosDisponiveis = getHotel().filtrarQuartos(quarto -> {
            if (!quarto.ehDisponivel())
                return false;

            if (clienteTitular == null)
                return true;

            return (quarto.getTotalCamaSolteiro() + 2 * quarto.getTotalCamaCasal() >= clienteTitular.getListaDependentes().size() + 1);
        });

        SelecionadorQuarto selecionadorQuarto = new SelecionadorQuarto(getHotel());
        selecionadorQuarto.setSelecaoCallback(quarto -> {
            numeroField.getField().setText(Integer.toString(quarto.getNumero()));
            return true;
        });
        selecionadorQuarto.setLista(quartosDisponiveis);
        selecionadorQuarto.setParent(this);

        selecionadorQuarto.exibirMenu();
    }

    private void reservarQuarto() {
        try {
            int numero = Integer.parseInt(numeroField.getField().getText());
            String cpf = cpfField.getField().getText();
            LocalDate dataEntrada = DateParser.parse(dataEntradaField.getField().getText());
            LocalDate dataSaida = DateParser.parse(dataSaidaField.getField().getText());

            if (dataSaida.isBefore(dataEntrada))
                throw new Exception("A data de saída deve ser posterior à data de entrada");

            Cliente cliente = getHotel().getClienteFromCpf(cpf);
            if (cliente == null)
                throw new ClienteException("O cliente informado não está cadastrado no sistema");
            if (!(cliente instanceof ClienteTitular))
                throw new ClienteException("O cliente informado não é um cliente titular");

            Quarto quarto = getHotel().getQuartoFromNumero(numero);
            if (quarto == null)
                throw new QuartoException("O quarto informado não está cadastrado no sistema");

            getHotel().reservarQuarto((ClienteTitular) cliente, quarto, dataEntrada, dataSaida);

            JOptionPane.showMessageDialog(this, "Quarto reservado com sucesso!", "Reserva de quarto", JOptionPane.INFORMATION_MESSAGE);
            fecharMenu(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Falha ao reservar quarto: " + e.getMessage(), "Reserva de quarto", JOptionPane.ERROR_MESSAGE);
        }
    }

    public LabeledTextField getNumeroField() {
        return numeroField;
    }

    public LabeledTextField getCpfField() {
        return cpfField;
    }

    public LabeledTextField getDataEntradaField() {
        return dataEntradaField;
    }

    public LabeledTextField getDataSaidaField() {
        return dataSaidaField;
    }
}


