package Classes;

import java.time.LocalDate;
import java.util.ArrayList;

class ClienteTitular extends Cliente {
    private ArrayList<ClienteDependente> listaDependentes;
    private Quarto quarto;

    public ClienteTitular(String nome, LocalDate dataNascimento, String cpf, double gasto){
        super(nome, dataNascimento, cpf, gasto);
        this.listaDependentes = new ArrayList<ClienteDependente>();
    }

    @Override
    public double getContaPagar(){
        double total = gasto;
        for(ClienteDependente dependente : listaDependentes){
            total += dependente.getContaPagar();
        }
        return total;
    }

    @Override
    public void addContaPagar(double valor) {
        gasto += valor;
    }

    protected void addDependente(ClienteDependente dependente){
        listaDependentes.add(dependente);
    }

    public void setQuarto(Quarto quarto){
        this.quarto = quarto;
    }

}
