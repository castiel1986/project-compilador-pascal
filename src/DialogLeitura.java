

/*+--------------------------------------------------------------------+
  |   Projeto - COMPILADOR PASCAL SIMPLIFICADO PARA WEB.               |
  |   Por: Luiz Eduardo da Silva (luizedu@unis.edu.br).                |
  |        F�bio Fernandes da Rocha Vicente (fabiovicente@unis.edu.br) |
  +--------------------------------------------------------------------+*/
import java.awt.*;
import java.awt.event.*;

public class DialogLeitura extends Dialog implements ActionListener {
 boolean valorOk = false;
 Button ok, can;
 TextField valor;

 DialogLeitura(Frame frame){
  super(frame, "Leitura de Dados", true);
  setLayout(new FlowLayout());
  valor = new TextField(15);
  add(new Label("Valor :"));
  add(valor);
  valor.addActionListener(this);
  addOKPanel();
  createFrame();
  pack();
  setVisible(true);
  }

 void addOKPanel() {
  Panel p = new Panel();
  p.setLayout(new FlowLayout());
  createButtons( p );
  add( p );
  }

 void createButtons(Panel p) {
  p.add(ok = new Button("OK"));
  ok.addActionListener(this); 
  p.add(can = new Button("Cancela"));
  can.addActionListener(this);
  }

 void createFrame() {
  Dimension d = getToolkit().getScreenSize();
  setLocation(d.width/4,d.height/3);
  }

 public void actionPerformed(ActionEvent ae){
  if(ae.getSource() == ok) {
    valorOk = true;
    setVisible(false);
    }
  else if(ae.getSource() == can) {
    valorOk = false;
    setVisible(false);
    }
  else if (ae.getSource() == valor) {
    valorOk = true;
    setVisible(false);
  }
  }
  
}
