package Classes;

class Produto{
    private double valor;
    private int quantidade;
    private String nome;

    Produto(double valor, int quantidade, String nome){
        this.valor = valor;
        this.quantidade = quantidade;
        this.nome = nome;
    }

    public double getValor(){
        return valor;
    }
    
    public int getQuantidade(){
    	return quantidade;
    }

}
