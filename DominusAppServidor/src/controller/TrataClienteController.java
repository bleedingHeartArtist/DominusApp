package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import model.ProdutoDao;
import model.UsuarioDao;
import model.VendaDao;
import modelDominio.Usuario;
import modelDominio.Vendedor;

public class TrataClienteController extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int idUnico;

    public TrataClienteController(Socket socket, int idUnico) {
        this.socket = socket;
        this.idUnico = idUnico;
        
        try {
            this.in = new ObjectInputStream(this.socket.getInputStream());
            this.out = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String comando;
        System.out.println("Esperando dados do cliente...");
        
        try {
            comando = (String) in.readObject();
            
            while (!comando.equalsIgnoreCase("fim")) {
                System.out.println("Cliente "+idUnico+" enviou o comando: "+comando);
                
                if (comando.equalsIgnoreCase("UsuarioEfetuarLogin")) {
                    out.writeObject("ok");
                    Usuario usuario = (Usuario)in.readObject();
                    System.out.println(usuario);
                    
                    UsuarioDao usuarioDao = new UsuarioDao();
                    out.writeObject(usuarioDao.efetuarLogin(usuario));
                    
                } else if (comando.equalsIgnoreCase("ListaVendas")) {
                    out.writeObject("ok");
                    Vendedor vendedor = (Vendedor) in.readObject();
                    VendaDao vendaDao = new VendaDao();
                    out.writeObject(vendaDao.getLista(vendedor));
                    
                } else if (comando.equalsIgnoreCase("VendedorInserir")) {
                    out.writeObject("ok");
                    Vendedor vendedor = (Vendedor) in.readObject();
                    UsuarioDao usuarioDao = new UsuarioDao();
                    out.writeObject(usuarioDao.vendedorInserir(vendedor));
                } else if (comando.equalsIgnoreCase("ListaProdutos")) {
                    out.writeObject("ok");
                    Vendedor vendedor = (Vendedor) in.readObject();
                    ProdutoDao produtoDao = new ProdutoDao();
                    out.writeObject(produtoDao.getLista(vendedor));
                }
                comando = (String) in.readObject();
            }
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        System.out.println("Cliente "+idUnico+" finalizou a conexão");
        
        try {
            this.in.close();
            this.out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    
    
    
    
}
