

/*+--------------------------------------------------------------------+
  |   Projeto - COMPILADOR PASCAL SIMPLIFICADO PARA WEB.               |
  |   Por: Luiz Eduardo da Silva (luizedu@unis.edu.br).                |
  |        F�bio Fernandes da Rocha Vicente (fabiovicente@unis.edu.br) |
  +--------------------------------------------------------------------+*/
import java.awt.*;
import java.util.*;

class Maquina {
  public static final int INPP = 0;
  public static final int AMEM = 1;
  public static final int CRVL = 2;
  public static final int CRCT = 3;
  public static final int CMIG = 4;
  public static final int CMDG = 5;
  public static final int CMMA = 6;
  public static final int CMME = 7;
  public static final int CMAG = 8;
  public static final int CMEG = 9;
  public static final int SOMA = 10;
  public static final int SUBT = 11;
  public static final int MULT = 12;
  public static final int DIVI = 13;
  public static final int INVR = 14;
  public static final int DISJ = 15;
  public static final int CONJ = 16;
  public static final int NEGA = 17;
  public static final int DSVS = 18;
  public static final int DSVF = 19;
  public static final int NADA = 20;
  public static final int FIM  = 21;
  public static final int PARA = 22;
  public static final int ARMZ = 23;
  public static final int IMPR = 24;
  public static final int LEIT = 25;
  public static final int ENPR = 26;
  public static final int RTPR = 27;

  String []nomeInstrucoes = 
  {"INPP", "AMEM", "CRVL", "CRCT", "CMIG", "CMDG", "CMMA", "CMME",
   "CMAG", "CMEG", "SOMA", "SUBT", "MULT", "DIVI", "INVR", "DISJ",
   "CONJ", "NEGA", "DSVS", "DSVF", "NADA", "FIM",  "PARA", "ARMZ",
   "IMPR", "LEIT", "RTPR"};
  
  StringBuilder mepa;
  DialogLeitura dl;
  Vector P = new Vector();
  int L[] = new int[100], 
      M[] = new int[1000],
      i = 0,
      s = 0;
  boolean paraExecucao = false, mostra = false;                                 

  public Maquina (StringBuilder mepa) {
      this.mepa  = mepa;
      i = 0;
      s = 0;
      paraExecucao = false;
      mostra = false;
  }

  public void carregaInstrucoes () {
    String texto = new String (mepa.toString());
    System.out.println("Mepa: "+mepa.toString());
    StringTokenizer tok = new StringTokenizer (texto, "\n");
    int contalinha = 0;
    while (tok.hasMoreTokens()) {
       String linha = tok.nextToken();
       StringBuffer strRotulo    = new StringBuffer(""),
                    strInstrucao = new StringBuffer(""),
                    strOperando  = new StringBuffer("");
       int Rotulo = 0, Operando = 0;
       int i = 0;
       boolean fim = false, achou = false;
       while (!fim) {
          /*----------- ROTULO ----------*/
          if (i == 0 && linha.charAt(i) == 'L') {
              i++;
              strRotulo = new StringBuffer ("");
              while (linha.charAt(i) != ':')
                 strRotulo.append(linha.charAt(i++));
              i++;
              if (strRotulo.length() != 0)
                 Rotulo = Integer.parseInt (strRotulo.toString());
              else
                 Rotulo = 0;
              L[Rotulo] = contalinha;
          }

          /*--------- INSTRUCAO ----------*/
          i++;
          strInstrucao = new StringBuffer ("");
          while (i < linha.length() && linha.charAt(i) != '\t' ) 
            strInstrucao.append (linha.charAt(i++));

          /*---------- OPERANDO ----------*/
          if (i < linha.length()) {
            i++;
            if (linha.charAt(i) == 'L')
               i++;
            strOperando = new StringBuffer ("");
            while (i < linha.length()) 
               strOperando.append (linha.charAt(i++));
            if (strOperando.length() != 0){
                System.out.println("Operando: "+strOperando);
               Operando = Integer.parseInt (strOperando.toString());
            }
            else
              Operando = 0;
          }
          fim = true;          
       }
       i = 0;
       fim = false;
       achou = false;
       String sInstrucao = strInstrucao.toString();
       while (!achou && !fim) {
          if (i >= nomeInstrucoes.length)
             fim = true;
          else if (sInstrucao.compareTo(nomeInstrucoes[i])==0)
                  achou = true;
               else 
                  i++;
       }
       if (!achou)
          System.out.println ("Nao achou = "+sInstrucao);
       P.addElement (new Instrucao (i, Rotulo, Operando));
       contalinha++;
    }
  }
  
  public void iniciaMaquina () {
     i = 0;
     s = 0;
     mostra = false;
     paraExecucao = false;
  }
  
  public boolean terminaExecucao () {
     return paraExecucao;
  }

  public String executaProximaInstrucao () {
      Instrucao inst;
      inst = (Instrucao)P.elementAt(i);
      switch (inst.inst) {
         case INPP: 
            if (mostra) System.out.print("INPP");
            i = 0; s = -1; i++; 
            return null;
         case AMEM: 
            if (mostra) System.out.print("AMEM");
            s = s + inst.oper; i++;
            return null;
         case ENPR: 
            if (mostra) System.out.print("ENPR");
            s = s + inst.oper; i++;
            return null;
         case RTPR: 
            if (mostra) System.out.print("ENPR");
            s = s + inst.oper; i++;
            return null;
         case CRVL: 
            if (mostra) System.out.print("CRVL");
            s++; M[s] = M[inst.oper]; i++;
            return null;
         case CRCT: 
            if (mostra) System.out.print("CRCT"); 
            s++; M[s] = inst.oper; i++;
            return null;
         case CMIG:
            if (mostra) System.out.print("CMIG"); 
            if (M[s-1] == M[s]) M[s-1] = 1; else M[s-1] = 0; s--; i++;
            return null;
         case CMDG: 
            if (mostra) System.out.print("CMDG"); 
            if (M[s-1] != M[s]) M[s-1] = 1; else M[s-1] = 0; s--; i++;                           
            return null;
         case CMMA: 
            if (mostra) System.out.print("CMMA"); 
            if (M[s-1] > M[s]) M[s-1] = 1; else M[s-1] = 0; s--; i++;
            return null;
         case CMME: 
            if (mostra) System.out.print("CMME"); 
            if (M[s-1] < M[s]) M[s-1] = 1; else M[s-1] = 0; s--; i++;
            return null;
         case CMAG: 
            if (mostra) System.out.print("CMAG");
            if (M[s-1] >= M[s]) M[s-1] = 1; else M[s-1] = 0; s--; i++;
            return null;
         case CMEG: 
            if (mostra) System.out.print("CMEG");
            if (M[s-1] <= M[s]) M[s-1] = 1; else M[s-1] = 0; s--; i++;
            return null;
         case SOMA: 
            if (mostra) System.out.print("SOMA"); 
            M[s-1] = M[s-1]+M[s]; s--; i++;
            return null;
         case SUBT: 
            if (mostra) System.out.print("SUBT"); 
            M[s-1] = M[s-1]-M[s]; s--; i++;
            return null;
         case MULT: 
            if (mostra) System.out.print("MULT"); 
            M[s-1] = M[s-1]*M[s]; s--; i++;
            return null;
         case DIVI:
            if (mostra) System.out.print("DIVI"); 
            M[s-1] = M[s-1] / M[s]; s--; i++;
            return null;
         case INVR: 
            if (mostra) System.out.print("INVR"); 
            M[s] = -M[s]; i++;
            return null;
         case DISJ:
            if (mostra) System.out.print("DISJ"); 
            if (M[s-1]==1||M[s]==1) M[s-1]=1; else M[s-1]=0; s--; i++;
            return null;
         case CONJ: 
            if (mostra) System.out.print("CONJ"); 
            if (M[s-1]==1&&M[s]==1) M[s-1]=1; else M[s-1]=0; s--; i++;
            return null;
         case NEGA: 
            if (mostra) System.out.print("NEGA");
            M[s] = 1-M[s]; i++;
            return null;
         case DSVS: 
            if (mostra) System.out.print("DSVS");
            i = L[inst.oper];
            return null;
         case DSVF:
            if (mostra) System.out.print("DSVF");
            if (M[s]==0) i = L[inst.oper]; else i++; s--;
            return null;
         case NADA: 
            if (mostra) System.out.print("NADA");
            i++;
            return null;
         case FIM:  
            if (mostra) System.out.print("FIM ");
            i++;
            return null;
         case PARA: 
            if (mostra) System.out.print("PARA");
            paraExecucao = true;
            return null;
         case ARMZ:
            if (mostra) System.out.print("ARMZ");
            M[inst.oper] = M[s]; s--; i++;
            return null;
         case IMPR: 
            if (mostra) System.out.print("IMPR"); 
            i++;
            return new String(String.valueOf(M[s--]))+"\n";
         case LEIT: 
            if (mostra) System.out.print("LEIT");
            String valor = new String ("0");
            int val = 0;
            dl = new DialogLeitura (new Frame(""));
            dl.requestFocus();
            if (dl.valorOk) {
                val = Integer.parseInt(dl.valor.getText());
            }
            else
                val = 0;
            dl.dispose();
            s++;
            M[s] = val;
            i++;
            return null;
      }
      if (mostra) 
         System.out.println (", "+inst.rot+", "+inst.oper+", i="+i+", s="+s);          
      return null;
  }
}
