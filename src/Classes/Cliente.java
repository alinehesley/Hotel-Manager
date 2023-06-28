package Classes;

import Classes.exceptions.CPFInvalidoException;
import Classes.exceptions.ClienteException;

import java.time.LocalDate;

public abstract class Cliente {
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private double conta;

    public Cliente(String nome, LocalDate dataNascimento, String cpf) throws ClienteException {
        this.nome = nome;
        this.dataNascimento = dataNascimento;

        if (Validacao.validarCPF(cpf) == false)
            throw new CPFInvalidoException(cpf);

        this.cpf = cpf.replaceAll("[^0-9]", "");
        this.conta = 0;
    }

    public Cliente(String nome, LocalDate dataNascimento, String cpf, double conta) throws ClienteException {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.conta = conta;

        if (Validacao.validarCPF(cpf) == false)
            throw new CPFInvalidoException(cpf);

        this.cpf = cpf.replaceAll("[^0-9]", "");
        this.conta = 0;
    }


    public double getConta() {
        return conta;
    }

    public void addConta(double valor) {
        conta += valor;
    }

    public void pagarConta(double valor) {
        conta -= valor;
        if (conta < 0.01)
            conta = 0.0;
    }

    public void pagarConta() {
        conta = 0.0;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", cpf='" + cpf + '\'' +
                ", conta=" + conta +
                '}';
    }

    public String getCpfFormatado() {
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public abstract Quarto getQuarto();
}
