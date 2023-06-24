package Classes;


import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {
    private ArrayList<Quarto> listaQuartos;
    private ArrayList<Cliente> listaClientes;

    private String nome;
    private String endereco;
    private String telefone;
    private String categoria;

    public Hotel(String nome, String endereco, String telefone, String categoria){
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

    private boolean cpfJaCadastrado(String cpf){
        for(Cliente c : listaClientes){
            if(c.getCpf().equals(cpf)){
                return true;
            }
        }
        return false;
    }

    public boolean cadastrarClienteTitular(ClienteTitular cliente){

        if(cpfJaCadastrado(cliente.getCpf())){
            System.out.println("Cliente já cadastrado");
            return false;
        }

        listaClientes.add(cliente);

        for(ClienteDependente clienteDependente : cliente.getListaDependentes()){
            cadastrarClienteDependente(clienteDependente);
        }

        return true;
    }
    public boolean cadastrarClienteTitular(String nome, String cpf, LocalDate dataNascimento){
        return cadastrarClienteTitular(new ClienteTitular(nome, dataNascimento, cpf));
    }

    public boolean cadastrarClienteDependente(String nome, String cpf, LocalDate dataNascimento, String cpfTitular){
        Cliente titular = getClienteFromCpf(cpfTitular);
        if(titular == null)return false;
        if(!(titular instanceof ClienteTitular))return false;
        return cadastrarClienteDependente(new ClienteDependente(nome, dataNascimento, cpf,(ClienteTitular) titular));
    }

    public boolean cadastrarClienteDependente(ClienteDependente clienteDependente){
        if(cpfJaCadastrado(clienteDependente.getCpf())){
            System.out.println("Cliente já cadastrado");
            return false;
        }
        listaClientes.add(clienteDependente);
        return true;
    }

    public Cliente getClienteFromCpf(String cpf){
        for(Cliente c : listaClientes){
            if(c.getCpf().equals(cpf)){
                return c;
            }
        }
        return null;
    }

    public boolean reservarQuarto(String cpf, int totalCamaCasal, int totalCamaSolteiro){
        Cliente cliente = getClienteFromCpf(cpf);
        if(cliente == null)return false;
        if(!(cliente instanceof ClienteTitular))return false;
        for(Quarto quarto : listaQuartos){
            if(quarto.getTotalCamaCasal() == totalCamaCasal && quarto.getTotalCamaSolteiro() == totalCamaSolteiro){
                if(reservarQuarto((ClienteTitular) cliente, quarto)){
                    System.out.println("Quarto reservado com sucesso!");
                    return true;
                }
            }
        }
        return false;
    }

    public boolean reservarQuarto(ClienteTitular cliente, Quarto quarto){
        if(quarto.ehDisponivel() == false){
            System.out.println("Quarto já reservado");
            return false;
        }
        return quarto.fazerCheckIn(cliente);
    }

    public Quarto getQuarto(int totalCamaCasal, int totalCamaSolteiro){
        for(Quarto quarto : listaQuartos){
            if(quarto.getTotalCamaCasal() == totalCamaCasal && quarto.getTotalCamaSolteiro() == totalCamaSolteiro){
                return quarto;
            }
        }
        return null;
    }

}
