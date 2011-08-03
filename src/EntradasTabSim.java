/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Everton
 */
public class EntradasTabSim implements ElemPilhaSem{

    private int numEntradas;
    private int nivel;

    public EntradasTabSim(int numEntradas, int nivel) {
        this.numEntradas = numEntradas;
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getNumEntradas() {
        return numEntradas;
    }

    public void setNumEntradas(int numEntradas) {
        this.numEntradas = numEntradas;
    }

}
