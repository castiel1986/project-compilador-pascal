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
public class IndiceArray implements ElemPilhaSem{
    
    private int limInferior;
    private int limSuperior;

    public int getLimInferior() {
        return limInferior;
    }

    public void setLimInferior(int limInferior) {
        this.limInferior = limInferior;
    }

    public int getLimSuperior() {
        return limSuperior;
    }

    public void setLimSuperior(int limSuperior) {
        this.limSuperior = limSuperior;
    }

    public IndiceArray(int limInferior, int limSuperior) {
        this.limInferior = limInferior;
        this.limSuperior = limSuperior;
    }
    
}
