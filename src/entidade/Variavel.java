package entidade;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Everton
 */
public class Variavel implements InfoComplementar, ElemPilhaSem {

    private int deslocamento;
    private Tipo tipo;
    private Mecanismo mecanismo;  /* Identifica se uma variável é parâmetro */

    public Variavel() {
    }

    public Variavel(int deslocamento) {
        this.deslocamento = deslocamento;
    }

    public Variavel(int deslocamento, Tipo tipo) {
        this.deslocamento = deslocamento;
        this.tipo = tipo;
    }
    
    /**
     * 
     * @param deslocamento
     * @param tipo
     * @param mecanismo 
     * Quando a variável é um parâmetro
     */
    public Variavel(int deslocamento, Tipo tipo, Mecanismo mecanismo) {
        this.deslocamento = deslocamento;
        this.tipo = tipo;
        this.mecanismo = mecanismo;
    }

    public int getDeslocamento() {
        return deslocamento;
    }

    public void setDeslocamento(int deslocamento) {
        this.deslocamento = deslocamento;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Mecanismo getMecanismo() {
        return mecanismo;
    }

    public void setMecanismo(Mecanismo mecanismo) {
        this.mecanismo = mecanismo;
    }
    
    public boolean isParametro(){
        
        return mecanismo != null;
        
    }

    @Override
    public String toString() {
        return "Variavel{" + "deslocamento=" + deslocamento + ", tipo=" + tipo + '}';
    }
}
