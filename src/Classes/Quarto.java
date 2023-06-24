package Classes;

import java.time.LocalDate;
import java.util.ArrayList;

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
    public void reiniciaFrigobar(){
        frigobar = Frigobar.criarFrigobarPadrao(this);
    }

    public boolean fazerCheckIn(ClienteTitular c){
        if(!ehDisponivel()){
            System.out.println("Quarto não disponível");
            return false;
        }
        if(c.getListaDependentes().size() + 1 > getTotalCamaSolteiro() + 2*getTotalCamaCasal()){
            System.out.println("Número de clientes maior que número de camas");
            return false;
        }
        disponivel = false;
        this.titular = c;
        dataEntrada = LocalDate.now();

        clientes.add(c);
        c.setQuarto(this);
        for(ClienteDependente clienteDependente : c.getListaDependentes()){
            clientes.add(clienteDependente);
        }
        return true;
    }

}
