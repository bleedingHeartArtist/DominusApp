package modelDominio;

import java.io.Serializable;

public class ItensVenda implements Serializable {
    private static final long serialVersionUID = 123l;

    private int codItensVenda;
    private Produto produto;
    private int quantidade;
    private float valorUnitario;
    private float valorTotal;

    public ItensVenda(int codItensVenda, Produto produto, int quantidade, float valorUnitario, float valorTotal) {
        this.codItensVenda = codItensVenda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
    }

    public ItensVenda(Produto produto, int quantidade, float valorUnitario, float valorTotal) {
        //PARA INSERTS
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "ItensVenda{" + "codItensVenda=" + codItensVenda + ", produto=" + produto + ", quantidade=" + quantidade + ", valorUniario=" + valorUnitario + ", valorTotal=" + valorTotal + '}';
    }

    public int getCodItensVenda() {
        return codItensVenda;
    }

    public void setCodItensVenda(int codItensVenda) {
        this.codItensVenda = codItensVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

}
