

/*+--------------------------------------------------------------------+
  |   Projeto - COMPILADOR PASCAL SIMPLIFICADO PARA WEB.               |
  |   Por: Luiz Eduardo da Silva (luizedu@unis.edu.br).                |
  |        F�bio Fernandes da Rocha Vicente (fabiovicente@unis.edu.br) |
  +--------------------------------------------------------------------+*/
class Instrucao {
   public int inst, rot, oper;
   public Instrucao (int inst, int rot, int oper) {
      this.inst  = inst;
      this.rot   = rot;
      this.oper  = oper;
   }
}
