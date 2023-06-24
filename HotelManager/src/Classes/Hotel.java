package Classes;


import java.util.ArrayList;

class Hotel {
    private ArrayList<Quarto> listaQuartos;
    private ArrayList<Cliente> listaClientes;

    private String nome;
    private String endereco;
    private String telefone;
    private String categoria;

    Hotel(String nome, String endereco, String telefone, String categoria){
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.categoria = categoria;
        this.listaQuartos = new ArrayList<Quarto>();
        this.listaClientes = new ArrayList<Cliente>();
    }

    public void addQuarto(Quarto quarto){
        listaQuartos.add(quarto);
    }
    public void addQuarto(int totalCamaCasal, int totalCamaSolteiro){
        listaQuartos.add(new Quarto(totalCamaCasal, totalCamaSolteiro));
    }


}
