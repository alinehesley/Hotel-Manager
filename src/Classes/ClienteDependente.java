package Classes;

import java.time.LocalDate;

class ClienteDependente extends Cliente {
    private ClienteTitular titular;

    ClienteDependente(String nome, LocalDate dataNascimento, String cpf, ClienteTitular titular) {
        super(nome, dataNascimento, cpf);
        this.titular = titular;
        titular.addDependente(this);
    }

    @Override
    public double getContaPagar() {
        return gasto;
    }

    @Override
    public void addContaPagar(double valor) {
        gasto += valor;
    }

    public ClienteTitular getTitular() {
        return titular;
    }

}
