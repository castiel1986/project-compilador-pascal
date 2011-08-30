package entidade;


import entidade.ElemPilhaSem;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Everton
 */
public class Funcao implements InfoComplementar, ElemPilhaSem{
    
    private int rotulo;
    private Tipo tipo;
    private int deslocamento;
    private ArrayList<Parametro> parametros;

    public Funcao(int rotulo, Tipo tipo, int deslocamento) {
        this.rotulo = rotulo;
        this.tipo = tipo;
        this.deslocamento = deslocamento;
        this.parametros = new ArrayList<Parametro>();
    }

    public int getDeslocamento() {
        return deslocamento;
    }

    public void setDeslocamento(int deslocamento) {
        this.deslocamento = deslocamento;
    }
    
    public int getRotulo() {
        return rotulo;
    }

    public void setRotulo(int rotulo) {
        this.rotulo = rotulo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    
    public void addParametro(Parametro parametro) {
        this.parametros.add(parametro);
    }
    
    public Parametro getParametro(int index){
        return parametros.get(index);
    }
    
}
