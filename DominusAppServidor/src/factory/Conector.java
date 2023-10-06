package factory;

import java.sql.*;

public class Conector {
 
    public static Connection getConnection() {
        Connection conexao = null;
        
        try {
            String url = "jdbc:mysql://localhost:3306/";
            String banco = "dominusapp";
            String usuario = "root";
            String senha = "";
            
            conexao = DriverManager.getConnection(url+banco, usuario, senha);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return conexao;
    }
    
}
