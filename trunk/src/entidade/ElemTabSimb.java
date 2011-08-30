package entidade;



/**
 *
 * @author Everton
 */
public class ElemTabSimb implements ElemPilhaSem{

    private String nome;
    private int nivel;
    private InfoComplementar infoC;

    public ElemTabSimb(String nome){
        this.nome = nome;
    }
    
    public ElemTabSimb(String nome, int nivel){
        this.nome =  nome;
        this.nivel = nivel;
    }

    public ElemTabSimb(String nome, int nivel, InfoComplementar infoC) {
        this.infoC = infoC;
        this.nome = nome;
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    public InfoComplementar getInfoC() {
        return infoC;
    }

    public void setInfoC(InfoComplementar infoC) {
        this.infoC = infoC;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "ElemTabSimb{" + "nome= " + nome + " nivel= " + nivel + " infoC= " + infoC + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ElemTabSimb other = (ElemTabSimb) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.nivel != other.nivel) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
}
