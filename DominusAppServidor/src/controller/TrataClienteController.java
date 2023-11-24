package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import model.DepartamentoDao;
import model.MarcaDao;
import model.ProdutoDao;
import model.UsuarioDao;
import model.VendaDao;
import modelDominio.Cliente;
import modelDominio.Departamento;
import modelDominio.Produto;
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
                } else if (comando.equalsIgnoreCase("ListaMarcas")) {
                    MarcaDao marcaDao = new MarcaDao();
                    out.writeObject(marcaDao.getLista());
                } else if (comando.equalsIgnoreCase("ListaDepartamentos")) {
                    DepartamentoDao dptoDao = new DepartamentoDao();
                    out.writeObject(dptoDao.getLista()); 
                } else if (comando.equalsIgnoreCase("ListaProdutosDepartamento")) {
                    out.writeObject("ok");
                    Departamento departamento = (Departamento) in.readObject();
                    ProdutoDao produtoDao = new ProdutoDao();
                    out.writeObject(produtoDao.getListaProdutosDepartamento(departamento));
                } else if (comando.equalsIgnoreCase("ProdutoInserir")) {
                    out.writeObject("ok");
                    Produto produto = (Produto)in.readObject();
                    ProdutoDao produtoDao = new ProdutoDao();
                    out.writeObject(produtoDao.produtoInserir(produto));   
                } else if (comando.equalsIgnoreCase("ListaClientes")) {
                    out.writeObject("ok");
                    Vendedor vendedor = (Vendedor)in.readObject();
                    UsuarioDao usrDao = new UsuarioDao();
                    out.writeObject(usrDao.getListaClientes(vendedor));
                } else if(comando.equalsIgnoreCase("ListaProdutosCompleta")){
                    ProdutoDao produtoDao = new ProdutoDao();
                    out.writeObject(produtoDao.getListaCompleta());
                } else if (comando.equalsIgnoreCase("ProdutoExcluir")) {
                    out.writeObject("ok");
                    Produto produto = (Produto)in.readObject();
                    ProdutoDao produtoDao = new ProdutoDao();
                    out.writeObject(produtoDao.produtoExcluir(produto));
                } else if (comando.equalsIgnoreCase("ClienteInserir")) {
                    out.writeObject("ok");
                    Cliente cliente = (Cliente) in.readObject();
                    UsuarioDao usrDao = new UsuarioDao();
                    out.writeObject(usrDao.clienteInserir(cliente));
                } else if (comando.equalsIgnoreCase("RecuperarSenha")) {
                    out.writeObject("ok");
                    String emailDestinatario = (String) in.readObject();
                    JavaMail javaMail = new JavaMail();             
                    out.writeObject(javaMail.enviaCodRecuperacao(emailDestinatario));
                } else if(comando.equalsIgnoreCase("AlterarSenhaRecup")) {
                    out.writeObject("ok");
                    Usuario usr = (Usuario) in.readObject();
                    UsuarioDao usrDao = new UsuarioDao();
                    out.writeObject(usrDao.alterarSenhaRecup(usr));
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
