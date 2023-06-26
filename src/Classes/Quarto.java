package Classes;

import Classes.exceptions.QuartoIndisponivelException;
import Classes.exceptions.QuartoNaoLocadoException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;

public class Quarto {
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

    public Quarto(int numero, int totalCamaCasal, int totalCamaSolteiro) {
        this.numero = numero;
        this.totalCamaCasal = totalCamaCasal;
        this.totalCamaSolteiro = totalCamaSolteiro;
        this.disponivel = true;
        this.titular = null;
        this.clientes = new ArrayList<Cliente>();
        this.frigobar = Frigobar.criarFrigobarPadrao(this);
        totalQuartos++;
    }

    public int getNumero() {
        return numero;
    }

    public int getTotalCamaCasal() {
        return totalCamaCasal;
    }

    public int getTotalCamaSolteiro() {
        return totalCamaSolteiro;
    }

    public ClienteTitular getTitular() {
        return titular;
    }

    public boolean ehDisponivel() {
        return disponivel;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = true;
    }

    public void reiniciarFrigobar() {
        frigobar = Frigobar.criarFrigobarPadrao(this);
    }

    public void fazerCheckIn(ClienteTitular c, LocalDate dataEntrada, LocalDate dataSaida) throws QuartoIndisponivelException {
        if (!ehDisponivel())
            throw new QuartoIndisponivelException(numero);

        if (c.getListaDependentes().size() + 1 > getTotalCamaSolteiro() + 2 * getTotalCamaCasal())
            throw new QuartoIndisponivelException("Número de clientes maior que número de camas do quarto %d", numero);

        this.disponivel = false;
        this.titular = c;
        this.dataEntrada = LocalDate.now();
        this.dataSaida = dataSaida;
        this.clientes.add(c);
        for (ClienteDependente clienteDependente : c.getListaDependentes()) {
            this.clientes.add(clienteDependente);
        }

        c.setQuarto(this);
    }

    public boolean fazerCheckOut() {
        if (disponivel)
            return false;

        // Função desaloca o cliente do quarto.
        reinicializarQuarto();
        reiniciarFrigobar();
        return true;
    }

    public double calculaPrecoEstadia() throws QuartoNaoLocadoException {
        if (disponivel)
            throw new QuartoNaoLocadoException(numero);

        long intervaloEstadia = ChronoUnit.DAYS.between(dataSaida, dataEntrada);
        double preco_estadia = intervaloEstadia * precoEstadia * clientes.size();
        return preco_estadia;
    }

    //Reinicilizando o quarto para que possa passar para o próximo cliente
    private void reinicializarQuarto() {
        if (this.titular != null)
            this.titular.setQuarto(null);

        this.dataEntrada = null;
        this.dataSaida = null;
        this.clientes = null;
        this.titular = null;
        this.disponivel = true;
    }

    @Override
    public String toString() {
        return "Quarto " + numero;
    }
}
