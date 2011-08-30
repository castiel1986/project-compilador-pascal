package entidade;



/*+--------------------------------------------------------------------+
  |   Projeto - COMPILADOR PASCAL PARA WEB.                            |
  |   Por: Everton Josué da Silva                                      |
  |        Luiz Eduardo da Silva                                       |
  +--------------------------------------------------------------------+*/

public class Erro {
    
   private int linha, coluna;

   public Erro (int lin, int col) {
      linha = lin;
      coluna = col;
   }
   
   public void setaErro (int lin, int col){
      linha = lin;
      coluna = col;
   }
   
   public int colErro (){
      return coluna;
   }
   
   public int linErro () {
      return linha;
   }
}