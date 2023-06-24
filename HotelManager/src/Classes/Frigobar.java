package Classes;

import java.util.ArrayList;

class Frigobar {
    private ArrayList<Produto> listaProdutos;
    private double totalVendas;
    private Quarto quarto;

    Frigobar(Quarto quarto){
        this.listaProdutos = new ArrayList<Produto>();
        this.totalVendas = 0;
        this.quarto = quarto;
    }

    public void addProduto(Produto produto){
        listaProdutos.add(produto);
    }

    public boolean retiraProduto(Produto produto){
        if(listaProdutos.remove(produto)){
            totalVendas += produto.getValor();
            return true;
        }
        return false;
    }

    public static Frigobar criarFrigobarPadrao(Quarto quarto){
        Frigobar frigobar = new Frigobar(quarto);
        frigobar.addProduto(ProdutosComuns.maca);
        frigobar.addProduto(ProdutosComuns.refrigerante);
        frigobar.addProduto(ProdutosComuns.agua);
        frigobar.addProduto(ProdutosComuns.doce);
        frigobar.addProduto(ProdutosComuns.salgadinho);
        frigobar.addProduto(ProdutosComuns.banana);

        return frigobar;
    }

}
