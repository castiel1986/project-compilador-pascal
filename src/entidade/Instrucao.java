/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

/**
 *
 * @author luizedu
 */
public class Instrucao {
   public int inst;
   public int rot, oper;
   public Instrucao (int inst, int rot, int oper) {
      this.inst  = inst;
      this.rot   = rot;
      this.oper = oper;
   }
}