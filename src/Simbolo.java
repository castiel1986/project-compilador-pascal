
/*+--------------------------------------------------------------------+
  |   Projeto - COMPILADOR PASCAL.                                     |
  |   Por: Luiz Eduardo da Silva (luizedu@unis.edu.br).                |
  |        Everton Josué da Silva (evertonjsilva31@gmail.com).         |
  +--------------------------------------------------------------------+*/

public class Simbolo extends java_cup.runtime.Symbol {
    
  private int line;
  private int column;

  public Simbolo(int type, int line, int column) {
    this(type, line, column, -1, -1, null);
  }

  public Simbolo(int type, int line, int column, Object value) {
    this(type, line, column, -1, -1, value);
  }

  public Simbolo(int type, int line, int column, int left, int right, Object value) {
    super(type, left, right, value);
    this.line = line;
    this.column = column;
  }

  public int getLine() {
    return line;
  }

  public int getColumn() {
    return column;
  }

  public String toString() {
    return (String)value;
  }

}

