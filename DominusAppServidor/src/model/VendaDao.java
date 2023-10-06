package model;

import factory.Conector;
import java.sql.*;
import java.util.ArrayList;
import modelDominio.Venda;

public class VendaDao {
    private Connection con;

    public VendaDao(Connection con) {
        this.con = Conector.getConnection();
    }
    
    //TODO
    public ArrayList<Venda> getLista() {
        Statement stmt = null;
        ArrayList<Venda> listaVendas;
        
        try {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery( " select * from venda"); 
            
            listaVendas = null;
            
            while (res.next()) {                
                //Venda venda = new Venda(res.getInt("codvenda"), dataVenda, 0, itens, cliente)
            }
            
        } catch (Exception e) {
            listaVendas = null;
        }
        
        return listaVendas;
    }
   
}
