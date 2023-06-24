package Classes;

import java.time.LocalDate;

abstract class Cliente{
    protected String nome;
    protected LocalDate dataNascimento;
    protected String cpf;
    protected double gasto;

    public Cliente(String nome, LocalDate dataNascimento, String cpf, double gasto){
        this.nome = nome;
        this.dataNascimento = dataNascimento;

        if (Validacao.validarCPF(cpf) == false){
            throw new IllegalArgumentException("CPF inválido");
        }

        this.cpf = cpf;
        this.gasto = gasto;
    }

    public abstract double getContaPagar();

    public abstract void addContaPagar(double valor);

}
