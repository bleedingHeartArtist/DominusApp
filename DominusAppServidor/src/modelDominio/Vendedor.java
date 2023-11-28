package modelDominio;
import java.io.Serializable;

public class Vendedor extends Usuario implements Serializable{
    private static final long serialVersionUID = 123l;
    private String cnpj;

    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return super.toString()+ "Vendedor("+')';
    }

    public Vendedor(String cnpj, String nome, String login, String senha, String endereco) {
        super(nome, login, senha, endereco);
        this.cnpj = cnpj;
    }

    public Vendedor(String cnpj, int codUsuario, String nome, String login, String senha, String endereco) {
        super(codUsuario, nome, login, senha, endereco);
        this.cnpj = cnpj;
    }

    public Vendedor(String cnpj, String login, String senha) {
        super(login, senha);
        this.cnpj = cnpj;
    }

    public Vendedor(String cnpj, int codUsuario) {
        super(codUsuario);
        this.cnpj = cnpj;
    }   

    public Vendedor(String nome, String login, String senha, String endereco) {
        super(nome, login, senha, endereco);
    }

    public Vendedor(int codUsuario, String nome, String login, String senha, String endereco) {
        super(codUsuario, nome, login, senha, endereco);
    }

    public Vendedor(String login, String senha) {
        super(login, senha);
    }

    public Vendedor(int codUsuario) {
        super(codUsuario);
    }

    public Vendedor(String cnpj, int codUsuario, String nome, String endereco) {
        super(codUsuario, nome, endereco);
        this.cnpj = cnpj;
    }
}
