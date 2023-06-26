package Classes;

public class Produto {
    private double valor;
    private int quantidade;
    private String nome;

    Produto(double valor, int quantidade, String nome) {
        this.valor = valor;
        this.quantidade = quantidade;
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
