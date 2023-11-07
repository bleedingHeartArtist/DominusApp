package model;

import factory.Conector;
import java.sql.*;
import java.util.ArrayList;
import modelDominio.Marca;

public class MarcaDao {
    private Connection con;
    
    public MarcaDao() {
        this.con = Conector.getConnection();
    }
    
    public ArrayList<Marca> getLista() {
        Statement stmt = null;
        ArrayList<Marca> listaMarcas;
        
        try {
            stmt = con.createStatement();
            
            ResultSet res = stmt.executeQuery("SELECT * FROM MARCA");
            
            listaMarcas = new ArrayList<>();
            
            while (res.next()) {
                Marca marcaSelecionada = new Marca(res.getInt("CODMARCA"), res.getString("NOMEMARCA"));
                listaMarcas.add(marcaSelecionada); 
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            listaMarcas = null;
        }
        return listaMarcas;
    }
    
}
