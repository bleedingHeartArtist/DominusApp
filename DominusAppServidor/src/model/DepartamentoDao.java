package model;

import factory.Conector;
import java.sql.*;
import java.util.ArrayList;
import modelDominio.Departamento;

public class DepartamentoDao {
    private Connection con;
    
    public DepartamentoDao() {
        this.con = Conector.getConnection();
    }
    
   public ArrayList<Departamento> getLista() {
       Statement stmt = null;
       ArrayList<Departamento> listaDepartamentos;

       try {
           stmt = con.createStatement();
           
           ResultSet res = stmt.executeQuery("SELECT * FROM DEPARTAMENTO");
           
           listaDepartamentos = new ArrayList<>();
           
           while (res.next()) {
               Departamento departamentoSel = new Departamento(res.getInt("CODDPTO"), res.getString("NOMEDPTO")); 
               listaDepartamentos.add(departamentoSel);
           }
           
       } catch (SQLException e) {
           e.printStackTrace();
           listaDepartamentos = null;
       }
       
       return listaDepartamentos;
   }
}
