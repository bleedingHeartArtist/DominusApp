package model;

import factory.Conector;
import java.sql.*;
import java.util.ArrayList;
import modelDominio.Cliente;
import modelDominio.Departamento;
import modelDominio.ItensVenda;
import modelDominio.Marca;
import modelDominio.Produto;
import modelDominio.Venda;
import modelDominio.Vendedor;

public class VendaDao {
    private Connection con;

    public VendaDao(Connection con) {
        this.con = Conector.getConnection();
    }
    
    //TODO
    public ArrayList<Venda> getLista(Vendedor vendedor) {
        Statement stmt = null;
        ArrayList<Venda> listaVendas;
        ArrayList<ItensVenda> listaItensVenda;
        
        try {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery( " SELECT * FROM ITENSVENDA"+
                                            " LEFT OUTER JOIN PRODUTO ON PRODUTO.CODPRODUTO = ITENS.CODPRODUTO"+
                                            " LEFT OUTER JOIN DEPARTAMENTO ON DEPARTAMENTO.CODDPTO = PRODUTO.CODDPTO"+
                                            " LEFT OUTER JOIN VENDA ON VENDA.CODVENDA = ITENSVENDA.CODVENDA"+
                                            " WHERE PRODUTO.CODVENDEDOR = "+vendedor.getCodUsuario()+
                                            " ORDER BY VENDA.CODVENDA;"); 
            //TODO pegar a lista de produtos
            listaVendas = null;
            
            
            int vendaAtual = -1;
                    
            while (res.next()) {    
                if (vendaAtual != res.getInt("CODVENDA")) {
                    vendaAtual = res.getInt("CODVENDA");
                }
                
                
                Cliente clienteVenda = null;
                listaItensVenda = null;
                
                Marca marcaProduto = new Marca(res.getInt("codMarca"), res.getString("nomeMarca"));
                
                Departamento departamentoProduto = new Departamento(res.getInt("CODDPTO"), 
                 res.getString("NOMEDPTO"));
                
                Vendedor vendedorProduto = new Vendedor(res.getInt("CODVENDEDOR"));
                
                Produto produtoVenda = new Produto(res.getInt("CODPRODUTO"), res.getString("NOME"), res.getString("DESCRICAO"),
                        res.getFloat("PRECO"), marcaProduto, departamentoProduto, vendedorProduto);
                /**Venda venda = new Venda(res.getInt("codvenda"), res.getDate("datavenda"), 
                                    0, listaItensVenda, clienteVenda);*/
            }
            
        } catch (Exception e) {
            listaVendas = null;
        }
        
        return listaVendas;
    }
   
}
