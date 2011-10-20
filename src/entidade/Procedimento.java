package entidade;


import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Everton
 */
public class Procedimento implements InfoComplementar, ElemPilhaSem{

    private int rotulo;
    private ArrayList<Variavel> parametros;

    public Procedimento(int rotulo) {
        this.rotulo = rotulo;
    }

    public int getRotulo() {
        return rotulo;
    }

    public void setRotulo(int rotulo) {
        this.rotulo = rotulo;
    }

    public void setParametros(ArrayList<Variavel> parametros) {
        this.parametros = parametros;
    }

    public Variavel getParametro(int index){
        return parametros.get(index);
    }
    
    public void addParametro(Variavel parametro) {
        this.parametros.add(parametro);
    }
    
    public ArrayList<Variavel> getParametros() {
        return parametros;
    }
    
    public int getNumParam(){     
        return this.parametros.size();
    }
}
