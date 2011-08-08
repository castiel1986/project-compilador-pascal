
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Everton
 */
public class TestePilhaSem {
    
    
    public int teste;

    public static void main(String[] args) {
        

        ArrayList<ElemTabSimb> el = new ArrayList<ElemTabSimb>();
        ElemTabSimb et = new ElemTabSimb("x", 1);
        
        el.add(et);

        et = new ElemTabSimb("x", 7);

        if(el.contains(et)){
            System.out.println("Contem");
        }


        System.out.println(el.get(0).getNome());
        
        
        LinkedList<ElemPilhaSem> pilha = new LinkedList<ElemPilhaSem>();
        Procedimento proc = new Procedimento(1);
        pilha.add(proc);
        pilha.add(Mecanismo.PAS_VAL);
        pilha.add(Tipo.INTEGER);
        pilha.add(Mecanismo.PAS_VAL);
        pilha.add(Tipo.INTEGER);
        pilha.add(Mecanismo.PAS_END);
        pilha.add(Tipo.INTEGER);


        ListIterator it = pilha.listIterator(pilha.size() - 2*3 - 1);

        while(it.hasNext()){
            System.out.println(it.next());
        }

    }
    
    public void teste(){
        
    }
}
