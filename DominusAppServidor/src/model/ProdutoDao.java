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
    
    public boolean produtoInserir(Produto produto) {
        boolean resultado;
        PreparedStatement stmt = null;
        
        try {
            con.setAutoCommit(false);
            String sql = "INSERT INTO PRODUTO (NOME, DESCRICAO, CODDPTO, PRECO, CODMARCA, CODVENDEDOR)"+
                    " VALUES (?,?,?,?,?,?)";
            
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setInt(3, produto.getDepartamento().getCodDpto());
            stmt.setFloat(4, produto.getPreco());
            stmt.setInt(5, produto.getMarca().getCodMarca());
            stmt.setInt(6, produto.getVendedor().getCodUsuario());
            
            stmt.execute();
            con.commit();
            resultado = true;
            
        } catch (SQLException e) {
            try {
                con.rollback();
                e.printStackTrace();
                resultado = false;
            } catch (SQLException ex) {
                ex.printStackTrace();
                resultado = false;
            }
        } finally {
            try {
                stmt.close();
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                resultado = false;
            }
        }      
        return resultado;
    }  
}
