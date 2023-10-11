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
                                            " LEFT OUTER JOIN USUARIO ON VENDA.CODCLIENTE = USUARIO.CODUSUARIO"+
                                            " WHERE PRODUTO.CODVENDEDOR = "+vendedor.getCodUsuario()+
                                            " ORDER BY VENDA.CODVENDA;");
            
            listaVendas = new ArrayList<>();     
            listaItensVenda = new ArrayList<>();
            int vendaAtual = -1;
                    
            while (res.next()) {
                
                Marca marcaProduto = new Marca(res.getInt("codMarca"), res.getString("nomeMarca"));
                
                Departamento departamentoProduto = new Departamento(res.getInt("CODDPTO"), 
                 res.getString("NOMEDPTO"));
                
                Vendedor vendedorProduto = new Vendedor(res.getInt("CODVENDEDOR"));
                
                Produto produtoVenda = new Produto(res.getInt("CODPRODUTO"), res.getString("NOME"), res.getString("DESCRICAO"),
                        res.getFloat("PRECO"), marcaProduto, departamentoProduto, vendedorProduto);
                
                ItensVenda itemVenda = new ItensVenda(res.getInt("CODITENSVENDA"), produtoVenda, res.getInt("QUANTIDADE"), 
                   res.getInt("VALOUNITARIO"), res.getInt("VALORTOTAL"));
                
                listaItensVenda.add(itemVenda);
                
                if (vendaAtual != res.getInt("CODVENDA")) {
                    vendaAtual = res.getInt("CODVENDA");
                    
                    Cliente clienteVenda = new Cliente(res.getString("CPF"), res.getInt("CODUSUARIO"), 
                            res.getString("PRODUTO.NOME"));
                    
                    Venda venda = new Venda(vendaAtual, res.getDate("DATAVENDA"), res.getFloat("VALOR"), 
                                        listaItensVenda, clienteVenda);
                    
                    listaVendas.add(venda);
                    
                    listaItensVenda.clear();
                }
            }
            
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
