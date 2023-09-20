package modelDominio;

import java.io.Serializable;

public class Departamento implements Serializable{
    private static final long serialVersionUID = 132l;
    
    private int codDpto;
    private String nomeDpto;

    public Departamento(int codDpto, String nomeDpto) {
        this.codDpto = codDpto;
        this.nomeDpto = nomeDpto;
    }

    public int getCodDpto() {
        return codDpto;
    }

    public void setCodDpto(int codDpto) {
        this.codDpto = codDpto;
    }

    public String getNomeDpto() {
        return nomeDpto;
    }

    public void setNomeDpto(String nomeDpto) {
        this.nomeDpto = nomeDpto;
    }

    @Override
    public String toString() {
        return "Departamento {" + "codDpto=" + codDpto +", nome="+nomeDpto+'}';
    }
    
    
    
}
