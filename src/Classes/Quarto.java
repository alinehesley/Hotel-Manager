package Classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;

class Quarto{
    private static int totalQuartos = 0;
    private int numero;
    private boolean disponivel;
    private ArrayList<Cliente> clientes;
    private ClienteTitular titular;
    private Frigobar frigobar;
    private int totalCamaCasal;
    private int totalCamaSolteiro;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private float precoEstadia;

    public Quarto(int numero, int totalCamaCasal, int totalCamaSolteiro){
        this.numero = numero;
        this.totalCamaCasal = totalCamaCasal;
        this.totalCamaSolteiro = totalCamaSolteiro;
        this.disponivel = true;
        this.titular = null;
        this.clientes = new ArrayList<Cliente>();
        this.frigobar = Frigobar.criarFrigobarPadrao(this);
        totalQuartos ++;
    }

    public int getNumero(){
        return numero;
    }
    public int getTotalCamaCasal(){
        return totalCamaCasal;
    }
    public int getTotalCamaSolteiro(){
        return totalCamaSolteiro;
    }
    public ClienteTitular getTitular(){
        return titular;
    }
    public boolean ehDisponivel(){
        return disponivel;
    }
    public ArrayList<Cliente> getClientes(){
        return clientes;
    }
    public void setDisponivel(boolean disponivel){
        this.disponivel = true;
    }
    public void reiniciarFrigobar(){
        frigobar = Frigobar.criarFrigobarPadrao(this);
    }

    public boolean fazerCheckIn(ClienteTitular c, LocalDate dataSaida){
        if(!ehDisponivel()){
            System.out.println("Quarto não disponível");
            return false;
        }
        if(c.getListaDependentes().size() + 1 > getTotalCamaSolteiro() + 2*getTotalCamaCasal()){
            System.out.println("Número de clientes maior que número de camas");
            return false;
        }
        this.disponivel = false;
        this.titular = c;
        this.dataEntrada = LocalDate.now();
        this.dataSaida = dataSaida;
        this.clientes.add(c);
        for(ClienteDependente clienteDependente : c.getListaDependentes()){
           this. clientes.add(clienteDependente);
        }
        c.setQuarto(this);
        return true;
    }
    //Reinicilizando o quarto para que possa passar para o próximo cliente
    private void reinicializarQuarto(){
        this.dataEntrada = null;
        this.dataSaida = null;
        this.clientes = null;
        this.titular = null;
        this.disponivel = true;
    }

    public boolean fazerCheckOut(){
        // Função desaloca o cliente do quarto.
        reinicializarQuarto();
        reiniciarFrigobar();
         return true;
    }

    public double calculaPrecoEstadia(){
        // Implementar exception de calcular estadia de quarto indisponível
         if(!disponivel){
        long intervaloEstadia = ChronoUnit.DAYS.between(dataSaida, dataEntrada);
        double preco_estadia = intervaloEstadia*precoEstadia*clientes.size();
        return preco_estadia;
        }else{
            throw new QuartoJaLocadoException("Não é possível fazer checkout. O quarto não está locado.");
        }
        
    }


}
