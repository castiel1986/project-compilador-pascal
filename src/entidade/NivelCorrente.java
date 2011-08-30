package entidade;


import entidade.ElemPilhaSem;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Everton
 */
public class NivelCorrente implements ElemPilhaSem{

    private int nivelCorrente;

    public NivelCorrente(int nivelCorrente) {
        this.nivelCorrente = nivelCorrente;
    }

    public int getNivelCorrente() {
        return nivelCorrente;
    }

    public void setNivelCorrente(int nivelCorrente) {
        this.nivelCorrente = nivelCorrente;
    }
   

}
