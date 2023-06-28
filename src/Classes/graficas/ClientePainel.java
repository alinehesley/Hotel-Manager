package Classes.graficas;

import Classes.Arquivos;
import Classes.Cliente;
import Classes.Hotel;
import Classes.ClienteDependente;
import Classes.helpers.DateParser;
import Classes.helpers.Utils;

import javax.swing.*;
import java.util.ArrayList;

public class ClientePainel extends JPanel {
    public interface ClienteCallback {
        void onUpdate(Cliente cliente);
    }

    private Hotel hotel;
    private Cliente cliente;
    // private Arquivos arquivo = new Arquivos();
    private JLabel labelNome;
    private JLabel labelCPF;
    private JLabel labelDataNascimento;
    private JLabel labelQuarto;
    private JLabel labelTitular;
    private JLabel labelConta;
    private final ArrayList<ClienteCallback> callbacks;

    public ClientePainel(Cliente cliente) {
        this.callbacks = new ArrayList<>();
        this.initializeLabels();

        this.add(labelNome);
        this.add(labelCPF);
        this.add(labelDataNascimento);
        this.add(labelQuarto);
        this.add(labelTitular);
        this.add(labelConta);

        this.setCliente(cliente);
    }

    public ClientePainel() {
        this(null);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        refresh();
    }

    public void refresh() {
        if (cliente == null) {
            labelNome.setText("Nome: ");
            labelCPF.setText("CPF: ");
            labelDataNascimento.setText("Data de nascimento: ");
            labelQuarto.setText("Quarto: ");
            labelTitular.setText("Titular: ");
            labelConta.setText("Conta: ");

            for (ClienteCallback callback : callbacks)
                callback.onUpdate(cliente);
            return;
        }

        labelNome.setText("Nome: " + cliente.getNome());
        labelCPF.setText("CPF: " + cliente.getCpfFormatado());
        labelDataNascimento.setText("Nascimento: " + cliente.getDataNascimento().format(DateParser.FORMATTER));

        if (cliente.getQuarto() != null)
            labelQuarto.setText(String.format("Quarto: %d", cliente.getQuarto().getNumero()));
        else
            labelQuarto.setText("Quarto: (nenhum)");

        if (cliente instanceof ClienteDependente) {
            labelTitular.setText("Titular: " + ((ClienteDependente) cliente).getTitular().getNome());
        } else {
            labelTitular.setText("Titular: (nenhum)");
        }

        labelConta.setText(String.format("Conta: %.2f", cliente.getConta()));

        for (ClienteCallback callback : callbacks)
            callback.onUpdate(cliente);
    }

    public void addCallback(ClienteCallback callback) {
        this.callbacks.add(callback);
    }

    public void removeCallback(ClienteCallback callback) {
        this.callbacks.remove(callback);
    }

    public void clearCallbacks() {
        this.callbacks.clear();
    }

    private void initializeLabels() {
        labelNome = new JLabel();
        labelCPF = new JLabel();
        labelDataNascimento = new JLabel();
        labelQuarto = new JLabel();
        labelTitular = new JLabel();
        labelConta = new JLabel();

        labelNome.addMouseListener(Utils.onMouseClick(e -> {
            if (cliente != null)
                Utils.copyToClipboard(cliente.getNome());
        }));
        labelCPF.addMouseListener(Utils.onMouseClick(e -> {
            if (cliente != null)
                Utils.copyToClipboard(cliente.getCpfFormatado());
        }));
        labelDataNascimento.addMouseListener(Utils.onMouseClick(e -> {
            if (cliente != null)
                Utils.copyToClipboard(cliente.getDataNascimento().format(DateParser.FORMATTER));
        }));
        labelQuarto.addMouseListener(Utils.onMouseClick(e -> {
            if (cliente != null && cliente.getQuarto() != null)
                Utils.copyToClipboard(String.format("%d", cliente.getQuarto().getNumero()));
        }));
        labelTitular.addMouseListener(Utils.onMouseClick(e -> {
            if (cliente instanceof ClienteDependente)
                setCliente(((ClienteDependente) cliente).getTitular());
        }));
        labelConta.addMouseListener(Utils.onMouseClick(e -> {
            if (cliente != null)
                Utils.copyToClipboard(String.format("%.2f", cliente.getConta()));
        }));
    }
}
