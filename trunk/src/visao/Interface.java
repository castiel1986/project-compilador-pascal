package visao;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Interface.java
 *
 * Created on 02/04/2011, 12:57:04
 */
import controle.ThreadExecucao;
import controle.sintatico;
import entidade.Erro;
import entidade.ArvoreSintatica;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import javax.swing.JFileChooser;
import javax.swing.tree.DefaultMutableTreeNode;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//**
 *
 * @author Everton
 */
public class Interface extends javax.swing.JFrame {

    private Erro erro;
    private String textoFonte;
    private StringBuilder textoMepa;
    private StringBuilder textoSaida;
    private ThreadExecucao threadexecucao;
    private ArvoreSintatica treePanel;

    /** Creates new form Interface */
    public Interface() {
        initComponents();
        this.setLocation(683 - (this.getWidth() / 2), (384 - ((this.getHeight() / 2) + 50)));
        this.jTAFonte.setText("program testeproc;\n   var x, y : integer;\nbegin\n   y := 10;\n   x := y + 2 * 10;\n   write(x);\nend. ");
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        
        treePanel = new ArvoreSintatica();
        jSPArvore = new javax.swing.JScrollPane();
        
        Compila = new javax.swing.JButton();
        Executa = new javax.swing.JButton();
        Termina = new javax.swing.JButton();
        jSPFonte = new javax.swing.JScrollPane();
        jTAFonte = new javax.swing.JTextArea();
        jSPMepa = new javax.swing.JScrollPane();
        jTAMepa = new javax.swing.JTextArea();
        jSPSaida = new javax.swing.JScrollPane();
        jTASaida = new javax.swing.JTextArea();
        jBAbrirArquivo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Compilador Pascal");
        setFont(new java.awt.Font("Arial", 0, 10));

        Compila.setText("Compila");
        Compila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CompilaActionPerformed(evt);
            }
        });

        Executa.setText("Executa");
        Executa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExecutaActionPerformed(evt);
            }
        });

        Termina.setText("Termina");
        Termina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TerminaActionPerformed(evt);
            }
        });

        jSPFonte.setViewportBorder(javax.swing.BorderFactory.createTitledBorder("Fonte"));
        
        jSPArvore.setViewportBorder(javax.swing.BorderFactory.createTitledBorder("Arvore"));
        treePanel.setPreferredSize(new Dimension(100, 200));
        populateTree(treePanel);
        add(treePanel, BorderLayout.CENTER);

        jSPArvore.setViewportView(treePanel);
        
        jTAFonte.setColumns(20);
        jTAFonte.setRows(5);
        jSPFonte.setViewportView(jTAFonte);

        jSPMepa.setViewportBorder(javax.swing.BorderFactory.createTitledBorder("Mepa"));

        jTAMepa.setColumns(20);
        jTAMepa.setRows(5);
        jSPMepa.setViewportView(jTAMepa);

        jSPSaida.setViewportBorder(javax.swing.BorderFactory.createTitledBorder("Saida"));

        jTASaida.setColumns(20);
        jTASaida.setEditable(false);
        jTASaida.setRows(5);
        jSPSaida.setViewportView(jTASaida);

        jBAbrirArquivo.setText("Abrir Arquivo");
        jBAbrirArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAbrirArquivoActionPerformed(evt);
            }
        });
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jSPFonte, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSPMepa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSPArvore, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSPSaida, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jBAbrirArquivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Compila, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Executa, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Termina, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBAbrirArquivo)
                    .addComponent(Compila)
                    .addComponent(Executa)
                    .addComponent(Termina))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSPMepa, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addComponent(jSPFonte, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSPArvore, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSPSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>                        

    private void CompilaActionPerformed(java.awt.event.ActionEvent evt) {                                        

        try {
            this.erro = new Erro(0, 0);
            this.jTAFonte.getText();
            this.textoFonte = this.jTAFonte.getText();
            this.textoMepa = new StringBuilder("");
            this.textoSaida = new StringBuilder("");
            sintatico p = new sintatico(new StringReader(textoFonte), textoMepa, textoSaida, erro);
            Object result = p.parse().value;
            this.jTAMepa.setText(textoMepa.toString());
            if (erro.colErro() == 0) {
                textoSaida.append("Programa correto!\n");
            } else {
                this.jTAFonte.setText(textoFonte);
                this.jTAFonte.select(erro.linErro() - 1, erro.linErro());
            }
            this.jTASaida.setText(textoSaida.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }                                       

    private void ExecutaActionPerformed(java.awt.event.ActionEvent evt) {                                        

        if (erro.colErro() == 0) {
            threadexecucao = new ThreadExecucao(textoMepa, jTASaida);
            threadexecucao.start();
        }

    }                                       

    private void TerminaActionPerformed(java.awt.event.ActionEvent evt) {                                        

        if (threadexecucao != null) {
            threadexecucao.para();
        }

    }                                       

    private void jBAbrirArquivoActionPerformed(java.awt.event.ActionEvent evt) {                                               
        JFileChooser fc = new JFileChooser("C:\\Users\\Everton\\Desktop\\SimuladorMepaV2");
        File file;
        String str, texto = "";
        int res;

        try {
            res = fc.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) {
                File arquivo = fc.getSelectedFile();
                jTAFonte.setText("");

                try {
                    BufferedReader in = new BufferedReader(new FileReader(arquivo));                   
                    while ((str = in.readLine()) != null) {
                        texto += str + "\n";
                    }
                    jTAFonte.setText(texto);
                    in.close();
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            }

        } catch (Exception ex) {
            System.out.println("Nenhum Arquivo foi aberto!");
        }
    }                                              

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Interface().setVisible(true);
            }
        });
    }
 
    public void populateTree(ArvoreSintatica treePanel) {
        String p1Name = new String("Parent 1");
        String p2Name = new String("Parent 2");
        String c1Name = new String("Child 1");
        String c2Name = new String("Child 2");

        DefaultMutableTreeNode p1, p2;

        p1 = treePanel.addObject(null, p1Name);
        p2 = treePanel.addObject(null, p2Name);

        treePanel.addObject(p1, c1Name);
        treePanel.addObject(p1, c2Name);

        treePanel.addObject(p2, c1Name);
        treePanel.addObject(p2, c2Name);
    }
    
    public javax.swing.JButton Compila;
    public javax.swing.JButton Executa;
    public javax.swing.JButton Termina;
    private javax.swing.JButton jBAbrirArquivo;
    public javax.swing.JScrollPane jSPFonte;
    public javax.swing.JScrollPane jSPArvore;
    public javax.swing.JScrollPane jSPMepa;
    public javax.swing.JScrollPane jSPSaida;
    public javax.swing.JTextArea jTAFonte;
    public javax.swing.JTextArea jTAMepa;
    public javax.swing.JTextArea jTASaida;

}
