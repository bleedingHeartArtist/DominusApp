package modelDominio;

import java.io.Serializable;

public class Cliente extends Usuario implements Serializable{
    private static final long serialVersionUID = 123l;
    private String cpf;

    @Override
    public String toString() {
        return super.toString()+"Cliente ("+')';
    }

    public Cliente(String nome, String login, String senha, String endereco, String cpf) {
        super(nome, login, senha, endereco);
        this.cpf = cpf;
    }

    public Cliente(String cpf, int codUsuario, String nome) {
        super(codUsuario, nome);
        this.cpf = cpf;
    }

    public Cliente(String nome, String login, String senha, String endereco) {
        super(nome, login, senha, endereco);
    }

    public Cliente(int codUsuario, String nome, String login, String senha, String endereco) {
        super(codUsuario, nome, login, senha, endereco);
    }

    public Cliente(String login, String senha) {
        super(login, senha);
    }

    public Cliente(String cpf, int codUsuario, String nome, String login, String senha, String endereco) {
        super(codUsuario, nome, login, senha, endereco);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
