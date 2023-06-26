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
    private int totalVagas;

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

    public boolean reservarQuarto(String cpf, int totalCamaCasal, int totalCamaSolteiro, LocalDate dataSaida){
        Cliente cliente = getClienteFromCpf(cpf);
        if(cliente == null)return false;
        if(!(cliente instanceof ClienteTitular))return false;
        for(Quarto quarto : listaQuartos){
            if(quarto.getTotalCamaCasal() == totalCamaCasal && quarto.getTotalCamaSolteiro() == totalCamaSolteiro){
                if(reservarQuarto((ClienteTitular) cliente, quarto, dataSaida)){
                    System.out.println("Quarto reservado com sucesso!");
                    return true;
                }
            }
        }

        return false;
    }

    public boolean reservarQuarto(String cpf, int totalCamaCasal, int totalCamaSolteiro, LocalDate dataSaida, LocalDate dataEntrada){
        Cliente cliente = getClienteFromCpf(cpf);
        if(cliente == null)return false;
        if(!(cliente instanceof ClienteTitular))return false;
        for(Quarto quarto : listaQuartos){
            if(quarto.getTotalCamaCasal() == totalCamaCasal && quarto.getTotalCamaSolteiro() == totalCamaSolteiro){
                if(reservarQuarto((ClienteTitular) cliente, quarto, dataSaida)){
                    System.out.println("Quarto reservado com sucesso!");
                    return true;
                }
            }
        }

        return false;
    }



    public boolean reservarQuarto(ClienteTitular cliente, Quarto quarto, LocalDate dataSaida){
        if(quarto.ehDisponivel() == false){
            System.out.println("Quarto já reservado");
            return false;
        }
        return quarto.fazerCheckIn(cliente, dataSaida);
    }

        public boolean reservarQuarto(ClienteTitular cliente, Quarto quarto, 
                                    LocalDate dataSaida, LocalDate dataEntrada){
        if(quarto.ehDisponivel() == false){
            System.out.println("Quarto já reservado");
            return false;
        }
        return quarto.fazerCheckIn(cliente, dataSaida, dataEntrada);
    }

    public Quarto getQuarto(int totalCamaCasal, int totalCamaSolteiro){
        for(Quarto quarto : listaQuartos){
            if(quarto.getTotalCamaCasal() == totalCamaCasal && quarto.getTotalCamaSolteiro() == totalCamaSolteiro){
                return quarto;
            }
        }
        return null;
    }

    public boolean encerrarReserva(Quarto quarto, double desconto){
        try{
        // Inicia calculando o valor da conta
        double valorEstadia = quarto.calculaPrecoEstadia();
        double valorConsumo = 0;
        // Calculando o valor de consumo
        for(Cliente cliente: quarto.getClientes()){
            valorConsumo += cliente.getContaPagar();
        } 
        //COMO VAMOS CAPTURAR O EHPAGO?
        boolean ehPago;
        double totalConsumo =  valorEstadia + valorConsumo - desconto;
        
        if(totalConsumo < 0){
            totalConsumo = 0.0;
        }
        
        recebePagamento(quarto, ehPago, totalConsumo);
        // Reinicializando os clientes
        for(Cliente cliente: quarto.getClientes()){
            cliente.reinicializa();
        }
        // Reinicializando o quarto
        quarto.fazerCheckOut();

        return true;


        } catch(QuartoNaoLocadoException e){
             System.out.println("Não foi possível fazer o checkout: " + e.getMessage())
            return false;
        }
    }

    private boolean recebePagamento(Quarto quarto, boolean ehPago, double totalConsumo){
        if(ehPago){
            quarto.getTitular().setEhInadimplente(ehPago);
            return true;
        }else{
            quarto.getTitular().setEhInadimplente(ehPago);
            return false;
        }
    }


    // Calcula a capacidade de vagas no quarto
    // Chamado após um checkin ou um checkout
    public void calculaTotalVagas(){
        int capacidade = 0;
        for(Quarto quarto : listaQuartos){
            if(quarto.ehDisponivel()){
                capacidade += 2*quarto.getTotalCamaCasal() + quarto.getTotalCamaCasal();
            }
        }
        this.totalVagas = capacidade;
    }

    public boolean trocaClienteQuarto(Quarto quartoAtual, Quarto novoQuarto){
        // Transfere todos os clientes caso os quartos tenha camas o suficiente
        if(!novoQuarto.ehDisponivel()){
            return false;
        } else if(quartoAtual.getTotalCamaCasal() > novoQuarto.getTotalCamaCasal() | 
                quartoAtual.getTotalCamaSolteiro() > novoQuarto.getTotalCamaSolteiro()){
                return false;
        } else{
            if(reservarQuarto(quartoAtual.getTitular(), novoQuarto, 
                    quartoAtual.getDataEntrada(), quartoAtual.getDataSaida())){
                        quartoAtual.getTitular().setQuarto(novoQuarto);
                        quartoAtual.fazerCheckOut();
                        return true;

            } else{
                return false;
            }
        }

    }

        public boolean trocaClienteQuarto(Quarto quartoAtual, Quarto novoQuarto, 
                                        Cliente novoTitular, ArrayList<Cliente> Novosdependentes){
        // Transfere todos os clientes caso os quartos tenha camas o suficiente
        if(novoTitular instanceof ClienteDependente){
            ClienteTitular titular = new ClienteTitular(novoTitular.getNome(), novoTitular.getDataNascimento(), 
                                                        novoTitular.getCpf());
            
            
        }

    }


}
