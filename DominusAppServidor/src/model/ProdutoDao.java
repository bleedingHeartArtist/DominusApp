package model;

import factory.Conector;
import java.sql.*;
import java.util.ArrayList;
import modelDominio.Departamento;
import modelDominio.Marca;
import modelDominio.Produto;
import modelDominio.Vendedor;

public class ProdutoDao {
    private Connection con;
    
    public ProdutoDao() {
        this.con = Conector.getConnection();
    }
    
    public ArrayList<Produto> getLista(Vendedor vendedor) {
        Statement stmt = null;
        ArrayList<Produto> listaProdutos;
        
        try {
            stmt = con.createStatement();
            
            ResultSet res = stmt.executeQuery(" SELECT PRODUTO.*, MARCA.*, DEPARTAMENTO.*"+
                                              " FROM PRODUTO"+
                                              " INNER JOIN MARCA ON PRODUTO.CODMARCA = MARCA.CODMARCA"+
                                              " INNER JOIN DEPARTAMENTO ON PRODUTO.CODDPTO = DEPARTAMENTO.CODDPTO"+
                                              " WHERE PRODUTO.CODVENDEDOR = "+vendedor.getCodUsuario());
            
            listaProdutos = new ArrayList<>();
            
            while (res.next()) {
                
                Marca marcaProduto = new Marca(res.getInt("CODMARCA"), res.getString("NOMEMARCA"));
                
                Departamento departamentoProduto = new Departamento(res.getInt("CODDPTO"), 
                 res.getString("NOMEDPTO"));
                
                Produto produtoSelecionado = new Produto(res.getInt("CODPRODUTO"), res.getString("NOME"), res.getString("DESCRICAO"),
                res.getFloat("PRECO"), marcaProduto, departamentoProduto, vendedor);
                
                listaProdutos.add(produtoSelecionado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            listaProdutos = null;
        }
        return listaProdutos;
    }
    
}
