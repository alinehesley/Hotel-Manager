package Classes;

import java.time.LocalDate;
import java.util.ArrayList;

class Quarto{
    private boolean disponivel;
    private ArrayList<Cliente> clientes;
    private Frigobar frigobar;
    private int totalCamaCasal;
    private int totalCamaSolteiro;
    private LocalDate dataEntrada;

    public Quarto(int totalCamaCasal, int totalCamaSolteiro){
        this.totalCamaCasal = totalCamaCasal;
        this.totalCamaSolteiro = totalCamaSolteiro;
        this.disponivel = true;
        this.clientes = new ArrayList<Cliente>();
        this.frigobar = Frigobar.criarFrigobarPadrao(this);
    }


}
