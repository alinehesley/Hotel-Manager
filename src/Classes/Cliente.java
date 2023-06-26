package Classes;

import java.time.LocalDate;

abstract class Cliente{
    protected String nome;
    protected LocalDate dataNascimento;
    protected String cpf;
    protected double gasto;

    public Cliente(String nome, LocalDate dataNascimento, String cpf){
        this.nome = nome;
        this.dataNascimento = dataNascimento;

        if (Validacao.validarCPF(cpf) == false){
            System.out.println("CPF invalido");
            throw new IllegalArgumentException("CPF invalido");
        }

        this.cpf = cpf;
        this.gasto = 0;
    }

    public abstract double getContaPagar();

    public abstract void addContaPagar(double valor);

    public abstract void reinicializa();

    public String getNome(){
        return nome;
    }
    public String getCpf(){
        return cpf;
    }

}
