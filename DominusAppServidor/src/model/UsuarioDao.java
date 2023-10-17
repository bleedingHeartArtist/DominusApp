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
    
    public boolean vendedorInserir(Vendedor vendedor) {
        boolean resultado;
        PreparedStatement stmt = null;
        
        try {
            con.setAutoCommit(false);
            
            String sql = ("INSERT INTO USUARIO (NOME, LOGIN, SENHA, ENDERECO, CNPJ, CPF, TIPO)"+
                          " VALUES(?,?,?,?,?,NULL,2)");
            stmt = con.prepareStatement(sql);
            stmt.setString(1, vendedor.getNome());
            stmt.setString(2, vendedor.getLogin());
            stmt.setString(3, vendedor.getSenha());
            stmt.setString(4, vendedor.getEndereco());
            stmt.setString(5, vendedor.getCnpj());
            
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
