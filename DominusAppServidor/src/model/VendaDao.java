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

    public VendaDao() {
        this.con = Conector.getConnection();
    }
    
    //TODO
    public ArrayList<Venda> getLista(Vendedor vendedor) {
        Statement stmt = null;
        ArrayList<Venda> listaVendas;
        ArrayList<ItensVenda> listaItensVendaAux;
        
        try {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery( " SELECT MARCA.*, DEPARTAMENTO.*, PRODUTO.CODVENDEDOR, PRODUTO.CODPRODUTO, PRODUTO.NOME AS NOMEPRODUTO,"+
                                            " PRODUTO.DESCRICAO, PRODUTO.PRECO, ITENSVENDA.*, VENDA.*, USUARIO.CPF, USUARIO.CODUSUARIO, USUARIO.NOME"+
                                            " FROM ITENSVENDA"+
                                            " LEFT OUTER JOIN PRODUTO ON PRODUTO.CODPRODUTO = ITENSVENDA.CODPRODUTO"+
                                            " LEFT OUTER JOIN DEPARTAMENTO ON DEPARTAMENTO.CODDPTO = PRODUTO.CODDPTO"+
                                            " LEFT OUTER JOIN MARCA ON MARCA.CODMARCA = PRODUTO.CODMARCA "+
                                            " LEFT OUTER JOIN VENDA ON VENDA.CODVENDA = ITENSVENDA.CODVENDA"+
                                            " LEFT OUTER JOIN USUARIO ON VENDA.CODCLIENTE = USUARIO.CODUSUARIO"+
                                            " WHERE PRODUTO.CODVENDEDOR = "+vendedor.getCodUsuario()+
                                            " ORDER BY VENDA.CODVENDA;");
            
            listaVendas = new ArrayList<>();     
            listaItensVendaAux = new ArrayList<>();
            Venda venda = null;
            Cliente clienteVenda = null;
            int vendaAtual = -1;
           
            while (res.next()) {   
                
                if (vendaAtual != res.getInt("CODVENDA") && vendaAtual != -1) {  
                    listaVendas.add(venda);
                    listaItensVendaAux.clear();
                }
        
                Marca marcaProduto = new Marca(res.getInt("CODMARCA"), res.getString("NOMEMARCA"));
                
                Departamento departamentoProduto = new Departamento(res.getInt("CODDPTO"), 
                 res.getString("NOMEDPTO"));
                
                Vendedor vendedorProduto = new Vendedor(res.getInt("CODVENDEDOR"));
                
                Produto produtoVenda = new Produto(res.getInt("CODPRODUTO"), res.getString("NOMEPRODUTO"), res.getString("DESCRICAO"),
                        res.getFloat("PRECO"), marcaProduto, departamentoProduto, vendedorProduto);
                
                ItensVenda itemVenda = new ItensVenda(res.getInt("CODITENSVENDA"), produtoVenda, res.getInt("QUANTIDADE"), 
                   res.getInt("VALORUNITARIO"), res.getInt("VALORTOTAL"));
                
                listaItensVendaAux.add(itemVenda);
                
                clienteVenda = new Cliente(res.getString("CPF"), res.getInt("CODUSUARIO"), 
                            res.getString("NOME"));
                
                ArrayList<ItensVenda> listaItensVenda = new ArrayList<>();
                listaItensVenda.addAll(listaItensVendaAux);
                    
                venda = new Venda(res.getInt("CODVENDA"), res.getDate("DATAVENDA"), res.getFloat("VALOR"), 
                           listaItensVenda, clienteVenda);
                  
                vendaAtual = res.getInt("CODVENDA");
                
            }       
            listaVendas.add(venda);
            
            res.close();
            stmt.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            listaVendas = null;
        }
        return listaVendas;
    }
   
}
