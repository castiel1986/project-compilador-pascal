/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Everton
 */
public class Parametro extends Variavel implements ElemPilhaSem, InfoComplementar{

    private Mecanismo mec;

    @Override
    public String toString() {
        return "Parametro{" + "mec=" + mec + '}';
    }

    public Parametro(){
        
    }

    public Parametro(int deslocamento) {
        super(deslocamento);
    }
    
    public Parametro(Mecanismo mec, int deslocamento, Tipo tipo) {
        super(deslocamento, tipo);
        this.mec = mec;
    }

    public Mecanismo getMec() {
        return mec;
    }

    public void setMec(Mecanismo mec) {
        this.mec = mec;
    }

}
