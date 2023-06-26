package Classes;

import Classes.exceptions.CPFInvalidoException;

import java.time.LocalDate;
import java.util.ArrayList;

public class ClienteTitular extends Cliente {
    private ArrayList<ClienteDependente> listaDependentes;
    private Quarto quarto;

    public ClienteTitular(String nome, LocalDate dataNascimento, String cpf) throws CPFInvalidoException {
        super(nome, dataNascimento, cpf);
        this.listaDependentes = new ArrayList<ClienteDependente>();
    }

    protected void addDependente(ClienteDependente dependente) {
        listaDependentes.add(dependente);
    }

    @Override
    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public ArrayList<ClienteDependente> getListaDependentes() {
        return listaDependentes;
    }

    public double getTotalConta() {
        double total = getConta();
        for (ClienteDependente dependente : listaDependentes) {
            total += dependente.getConta();
        }
        return total;
    }
}
