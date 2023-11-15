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
                                              " WHERE PRODUTO.CODVENDEDOR = "+vendedor.getCodUsuario()+
                                              " AND PRODUTO.ATIVO = 1");
            
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
    
    public ArrayList<Produto> getListaCompleta() {
    Statement stmt = null;
    ArrayList<Produto> listaProdutosCompletos;
    
        try {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT PRODUTO.*, MARCA.*, DEPARTAMENTO.*"+
                                              " FROM PRODUTO"+
                                              " INNER JOIN MARCA ON PRODUTO.CODMARCA = MARCA.CODMARCA"+
                                              " INNER JOIN DEPARTAMENTO ON PRODUTO.CODDPTO = DEPARTAMENTO.CODDPTO"+
                                              " WHERE PRODUTO.ATIVO = 1");
                    
            listaProdutosCompletos = new ArrayList<>();
                    
            while (res.next()) {
                Marca marcaProduto = new Marca(res.getInt("CODMARCA"), res.getString("NOMEMARCA"));
                
                Departamento departamentoProduto = new Departamento(res.getInt("CODDPTO"), 
                 res.getString("NOMEDPTO"));
                
                Vendedor vendedorProduto = new Vendedor(res.getInt("CODVENDEDOR"));
                
                Produto produtoSelecionado = new Produto(res.getInt("CODPRODUTO"), res.getString("NOME"), res.getString("DESCRICAO"), res.getFloat("PRECO"), marcaProduto, departamentoProduto, vendedorProduto);
                
                listaProdutosCompletos.add(produtoSelecionado);
            }
                                              
                    
        } catch (Exception e) {
            e.printStackTrace();
            listaProdutosCompletos = null;
        }
        return listaProdutosCompletos;
}
   
    public boolean produtoInserir(Produto produto) {
        boolean resultado;
        PreparedStatement stmt = null;
        
        try {
            con.setAutoCommit(false);
            String sql = "INSERT INTO PRODUTO (NOME, DESCRICAO, CODDPTO, PRECO, CODMARCA, CODVENDEDOR, ATIVO)"+
                    " VALUES (?,?,?,?,?,?,?)";
            
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setInt(3, produto.getDepartamento().getCodDpto());
            stmt.setFloat(4, produto.getPreco());
            stmt.setInt(5, produto.getMarca().getCodMarca());
            stmt.setInt(6, produto.getVendedor().getCodUsuario());
            stmt.setInt(7, 1);
            
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
    
    public boolean produtoExcluir(Produto produto) {
        boolean resultado;
        PreparedStatement stmt = null;
        
        try {
            con.setAutoCommit(false);
            
            String sql = "UPDATE PRODUTO"+
                         " SET PRODUTO.ATIVO = 2"+
                         " WHERE PRODUTO.CODPRODUTO = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, produto.getCodProduto());
            
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
