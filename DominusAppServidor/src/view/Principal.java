package view;

import controller.TrataClienteController;
import factory.Conector;
import java.net.ServerSocket;
import java.net.Socket;

public class Principal {
    public static void main(String[] args) {
        ServerSocket servidor;
        int idUnico = 0;
        
        try {
            servidor = new ServerSocket(12345);
            System.out.println("Servidor DominusApp inicializado. Aguardando clientes...");
            
            if (Conector.getConnection() != null) {
                System.out.println("Conectado ao banco de dados com sucesso.");
            }
            
            while (true){
                Socket cliente = servidor.accept();
                System.out.println("Um novo cliente se conectou ao servidor: "+cliente);
                idUnico++;
                
                System.out.println("Iniciando uma thread para o cliente: "+idUnico);
                TrataClienteController clienteController = new TrataClienteController(cliente, idUnico);
                clienteController.start();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
