package modelDominio;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Produto implements Serializable {
    private static final long serialVersionUID = 123l;
    
    private int codProduto;
    private String nome;
    private String descricao;
    private float preco;
    private Marca marca;
    private Departamento departamento;
    private Vendedor vendedor;

    public Produto(int codProduto, String nome, String descricao, float preco, Marca marca, Departamento departamento, Vendedor vendedor) {
        //SELECTS e UPDATES
        this.codProduto = codProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.marca = marca;
        this.departamento = departamento;
        this.vendedor = vendedor;
    } 

    public Produto(String nome, String descricao, float preco, Marca marca, Departamento departamento, Vendedor vendedor) {
        //INSERTS
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.marca = marca;
        this.departamento = departamento;
        this.vendedor = vendedor;
    }

    public Produto(int codProduto) {
        //DELETE
        this.codProduto = codProduto;
    }
    
    @Override
    public String toString() {
        return "Produto{" + "codProduto=" + codProduto + ", nome=" + nome + ", descricao=" + descricao + ", preco=" + preco+ '}';
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }   

    public int getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPreco() {
        return preco;
    }
    
    public String getPrecoString() {
        DecimalFormat dcf = new DecimalFormat("#,##0.00");
        return dcf.format(this.preco);
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }    
       
}
