package Classes;

class Produto{
    private double valor;
    private String nome;

    Produto(double valor, String nome){
        this.valor = valor;
        this.nome = nome;
    }

    public double getValor(){
        return valor;
    }

}
