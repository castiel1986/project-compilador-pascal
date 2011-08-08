
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
    private ArrayList<Parametro> parametros;

    public Procedimento(int rotulo) {
        this.parametros = new ArrayList<Parametro>();
        this.rotulo = rotulo;
    }

    public int getRotulo() {
        return rotulo;
    }

    public void setRotulo(int rotulo) {
        this.rotulo = rotulo;
    }

    public Parametro getParametro(int index){
        return parametros.get(index);
    }
    
    public void addParametro(Parametro parametro) {
        this.parametros.add(parametro);
    }
    
    public ArrayList<Parametro> getParametros() {
        return parametros;
    }
}
