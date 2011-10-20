package entidade;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Everton
 */
public class Rotulo implements ElemPilhaSem{

    private int rotulo;

    public Rotulo(int rotulo) {
        this.rotulo = rotulo;
    }

    public int getRotulo() {
        return rotulo;
    }

    public void setRotulo(int rotulo) {
        this.rotulo = rotulo;
    }

}
