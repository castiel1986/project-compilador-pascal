/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Everton
 */
public class Variavel implements InfoComplementar, ElemPilhaSem{

    @Override
    public String toString() {
        return "Variavel{" + "deslocamento=" + deslocamento + ", tipo=" + tipo + '}';
    }

    private  int deslocamento;
    private  Tipo tipo;

    public Variavel(){
        
    }

    public Variavel(int deslocamento){
        this.deslocamento = deslocamento;
    }

    public Variavel(int deslocamento, Tipo tipo) {
        this.deslocamento = deslocamento;
        this.tipo = tipo;
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

}
