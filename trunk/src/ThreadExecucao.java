

/*+--------------------------------------------------------------------+
  |   Projeto - COMPILADOR PASCAL SIMPLIFICADO PARA WEB.               |
  |   Por: Luiz Eduardo da Silva (luizedu@unis.edu.br).                |
  |        F�bio Fernandes da Rocha Vicente (fabiovicente@unis.edu.br) |
  +--------------------------------------------------------------------+*/
import javax.swing.JTextArea;

public class ThreadExecucao extends Thread {

   StringBuilder TextoMepa;
   JTextArea execucao;
   boolean paraExecucao;
   Maquina maqMepa;
   
   public ThreadExecucao (StringBuilder TextoMepa, JTextArea execucao) {
     super();
     this.TextoMepa = TextoMepa;
     this.execucao  = execucao;
   }

   public void run() {
     String result;   
     maqMepa = new Maquina (TextoMepa);
     maqMepa.carregaInstrucoes();
     paraExecucao = false;     
     while (!maqMepa.terminaExecucao() && !paraExecucao) {
        result = maqMepa.executaProximaInstrucao();
        if (result != null) execucao.append (result);
     }
   }
   
   public void para() {
      paraExecucao = true;
   }
}

