package entidade;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Everton
 */
public class Array extends Variavel{
    
    private IndiceArray indiceArray;

    public Array(int deslocamento, Tipo tipo, IndiceArray indiceArray) {
        super(deslocamento, tipo);
        this.indiceArray = indiceArray;
    }

    public Array(int deslocamento, IndiceArray indiceArray) {
        super(deslocamento);
        this.indiceArray = indiceArray;
    }
    
    public Array(int deslocamento, Tipo tipo, IndiceArray indiceArray, Mecanismo mecanismo) {
        super(deslocamento, tipo, mecanismo);
        this.indiceArray = indiceArray;
    }

    public int getLimInferior() {
        return indiceArray.getLimInferior();
    }

    public int getLimSuperior() {
        return this.indiceArray.getLimSuperior();
    }
    
    public void setLimites(IndiceArray indiceArray){
        this.indiceArray = indiceArray;
    }
    
    public int getTamArray(){
        return this.indiceArray.getLimSuperior() - this.indiceArray.getLimInferior() + 1;
    }
    
}
