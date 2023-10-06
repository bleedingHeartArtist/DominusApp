package model;

import factory.Conector;
import java.sql.*;
import modelDominio.Cliente;
import modelDominio.Usuario;
import modelDominio.Vendedor;

public class UsuarioDao {
    private Connection con;

    public UsuarioDao() {
        this.con = Conector.getConnection();
    }
    
    public Usuario efetuarLogin(Usuario usuario) {
        PreparedStatement statement = null;
        Usuario usuarioSelect = null;
        ResultSet resultSet = null;
        
        try {
            String select = "SELECT *"
                            +" FROM usuario"
                            +" WHERE login = ? AND senha = ?;";
            statement = con.prepareStatement(select);
            statement.setString(1, usuario.getLogin());
            statement.setString(2, usuario.getSenha());
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                if (resultSet.getInt("tipo") == 1) {
                    usuarioSelect = new Cliente(resultSet.getString("cpf"),resultSet.getInt("codUsuario"), resultSet.getString("nome"),
                                    resultSet.getString("login"), resultSet.getString("senha"), resultSet.getString("endereco"));
                    
                } else {
                    usuarioSelect = new Vendedor(resultSet.getString("cnpj"), resultSet.getInt("codUsuario"), resultSet.getString("nome"),
                                    resultSet.getString("login"), resultSet.getString("senha"), resultSet.getString("endereco"));
                }     
            }  
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {resultSet.close();} catch (Exception e) {/*Ignorado*/}
            try {statement.close();} catch (Exception e) {/*Ignorado*/}
            try {statement.close();} catch (Exception e) {/*Ignorado*/}
            return usuarioSelect;
        }
    }
}
