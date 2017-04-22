/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SSC;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;

/**
 *
 * @author roger
 */
public class NovoTab {
    
    private javax.swing.JPanel painelNovo;
    private JScrollPane rolagemPaineis;
    private javax.swing.JButton btInfoGrupo;
    private javax.swing.JButton btAbrirPasta;
    private javax.swing.JButton btBaixar;
    private javax.swing.JButton btAtualizar;
    private javax.swing.JList<String> listaDiretrios;
    
    private javax.swing.JDialog jDialog5;
    private javax.swing.JTabbedPane jTabbedPane1;
    
    private List diretorioGrupo = new List();
    private List diretorioGrupoRaiz = new List();
    private boolean criarNovosGrupos = false;

    public void setDiretorioGrupo(List diretorioGrupo) {
        this.diretorioGrupo = diretorioGrupo;
    }
    
    public void setDiretorioGrupoRaiz(List diretorioGrupoRaiz) {
        this.diretorioGrupoRaiz = diretorioGrupoRaiz;
    }

    public void setCriarNovosGrupos(boolean criarNovosGrupos) {
        this.criarNovosGrupos = criarNovosGrupos;
    }
    
    public void criarTab(String nomeTab, String[] arquivosDentroPasta){
        
        
        painelNovo = new javax.swing.JPanel();
        rolagemPaineis = new javax.swing.JScrollPane();
        btInfoGrupo = new javax.swing.JButton();
        btAbrirPasta = new javax.swing.JButton();
        btBaixar = new javax.swing.JButton();
        btAtualizar = new javax.swing.JButton();
        btAtualizar.setText("Atualizar");
        btInfoGrupo.setText("Informações");
        btAbrirPasta.setText("Abrir");
        btBaixar.setText("Baixar");
        
        /**
         * essa variavel cria uma lista de diretorios que estão dentro do grupo
         */
        listaDiretrios = new javax.swing.JList<>();
        listaDiretrios.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = arquivosDentroPasta;
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        
        listaDiretrios.addMouseListener(new AcoesDaLista());
        
        rolagemPaineis.setViewportView(listaDiretrios);
        
        btInfoGrupo.addActionListener(new ListinerbtInfoGrupo());
        
        btAbrirPasta.addActionListener(new ListnerbtAbrirPasta());
        
        btBaixar.addActionListener(new ListinerbtBaixar());
        
        btAtualizar.addActionListener(new listinerbtAtualizar());
        
        
        javax.swing.GroupLayout paineisLayout = new javax.swing.GroupLayout(painelNovo);
        painelNovo.setLayout(paineisLayout);
        paineisLayout.setHorizontalGroup(
            paineisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paineisLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(paineisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rolagemPaineis, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(paineisLayout.createSequentialGroup()
                        .addComponent(btInfoGrupo)
                        .addGap(25, 35, 35)
                        .addComponent(btAtualizar)
                        .addGap(25, 35, 35)
                        .addComponent(btAbrirPasta)
                        .addGap(35, 35, 35)
                        .addComponent(btBaixar)))
                .addGap(41, 41, 41))
        );
        paineisLayout.setVerticalGroup(
            paineisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paineisLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(rolagemPaineis, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(paineisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btInfoGrupo)
                    .addComponent(btAtualizar)
                    .addComponent(btBaixar)
                    .addComponent(btAbrirPasta))
                .addGap(25, 25, 25))
        );       
        
        
        if(criarNovosGrupos){
            
            diretorioGrupo.add("C:\\TCC\\" + nomeTab);
            diretorioGrupoRaiz.add("C:\\TCC\\" + nomeTab);
            System.out.println("-----------------------------------------C:\\TCC\\" + nomeTab);
            
        }
        
        jTabbedPane1.addTab(nomeTab, painelNovo);
        
        
    }
    
    /**
     *
     */
    public void setJDialog5(javax.swing.JDialog jDialog5){
        
        this.jDialog5 = jDialog5;
        
    }
    
    public void setJTabbedPane1(javax.swing.JTabbedPane jTabbedPane1){
        
        this.jTabbedPane1 = jTabbedPane1;
        
    }
    
    
    public class AcoesDaLista extends java.awt.event.MouseAdapter {
        
        
        public void mouseClicked(java.awt.event.MouseEvent evt) {
                
                if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();                    
                    System.out.println("\nPasta localizada em " + diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1));
                    System.out.println("clicou em " + listaDiretrios.getSelectedValue());
                    
                    
                    if(listaDiretrios.getSelectedValue().equals("voltar/..")){  
                       
                                             
                        String subirDiretorio = diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).substring(0, diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).lastIndexOf("\\"));
                       
                        diretorioGrupo.replaceItem(subirDiretorio, jTabbedPane1.getSelectedIndex() - 1);
                        
                        //aqui limpa a variavel
                        subirDiretorio = "";
                                             
                        File file2 = new File(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1));
                        
                        //adiciona voltar na lista de opcoes
                        String[] voltarAux = file2.list();
                        List voltar = new List();
                        
                        if(!diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).equalsIgnoreCase(diretorioGrupoRaiz.getItem(jTabbedPane1.getSelectedIndex() - 1))){
     
                                voltar.add("voltar/.."); 
                        }
                        
                        for(int i = 0;i < file2.list().length; i++){
                                voltar.add(voltarAux[i]);
                            }
                        
                        listaDiretrios.setModel(new javax.swing.AbstractListModel<String>() {
                            String[] strings = voltar.getItems();
                            public int getSize() { return strings.length; }
                            public String getElementAt(int i) { return strings[i]; }
                        });
                            
                        System.out.println("----------------------------------------------------------- ");
                        System.out.println(file2.getPath().intern());
                        System.out.println("É pasta " + diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1));                      
                        
                        listaDiretrios.addMouseListener(this);
                        
                        rolagemPaineis.setViewportView(listaDiretrios);
                                                    
                    }else{
                        
                        
                        File file = new File(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) + "\\" + listaDiretrios.getSelectedValue());
                         
                        if(file.isDirectory()){
                            diretorioGrupo.replaceItem(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) + "\\" + listaDiretrios.getSelectedValue(), jTabbedPane1.getSelectedIndex() - 1);
                            System.out.println("É pasta " + diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1));

                            File file2 = new File(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1));

                            //adiciona voltar na lista de opcoes
                            String[] voltarAux = file2.list();
                            List voltar = new List();

                            if(!diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).equalsIgnoreCase(diretorioGrupoRaiz.getItem(jTabbedPane1.getSelectedIndex() - 1))){

                                    voltar.add("voltar/.."); 
                            }

                            for(int i = 0;i < file2.list().length; i++){
                                    voltar.add(voltarAux[i]);
                                }

                            listaDiretrios.setModel(new javax.swing.AbstractListModel<String>() {
                                String[] strings = voltar.getItems();
                                public int getSize() { return strings.length; }
                                public String getElementAt(int i) { return strings[i]; }
                            });

                            System.out.println("----------------------------------------------------------- ");
                            System.out.println(file2.getPath().intern());
                            System.out.println("É pasta " + diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1));

                            listaDiretrios.addMouseListener(this);

                            rolagemPaineis.setViewportView(listaDiretrios);
                            
                        }
                        
                        
                    }
                    
                    
                }
                                
            }
        
    }
    
    public class ListnerbtAbrirPasta implements java.awt.event.ActionListener{
        
        @Override
            public void actionPerformed(ActionEvent e) {
                
                System.out.println(".actionPerformed()");
                System.out.println(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) + "\\" + listaDiretrios.getSelectedValue());
                
                if(listaDiretrios.getSelectedValue() == null || listaDiretrios.getSelectedValue().contains("voltar")){
                    
                    try {
                    Runtime.getRuntime().exec("explorer " + (diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) + "\\"));
                } catch (IOException ex) {
                    Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                }else{
                    
                    try {
                    Runtime.getRuntime().exec("explorer " + (diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) + "\\" + listaDiretrios.getSelectedValue()));
                } catch (IOException ex) {
                    Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                    
                }
                
            }
        
    }
    
    public class listinerbtAtualizar implements java.awt.event.ActionListener{
        
        @Override
            public void actionPerformed(ActionEvent e) {
                
                File file2 = new File(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1));

                            //adiciona voltar na lista de opcoes
                            String[] voltarAux = file2.list();
                            List voltar = new List();

                            if(!diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).equalsIgnoreCase(diretorioGrupoRaiz.getItem(jTabbedPane1.getSelectedIndex() - 1))){

                                    voltar.add("voltar/.."); 
                            }

                            for(int i = 0;i < file2.list().length; i++){
                                    voltar.add(voltarAux[i]);
                                }

                            listaDiretrios.setModel(new javax.swing.AbstractListModel<String>() {
                                String[] strings = voltar.getItems();
                                public int getSize() { return strings.length; }
                                public String getElementAt(int i) { return strings[i]; }
                            });

                            System.out.println("Atualizar: " + diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1));
                
            }
        
    }
    
    public class ListinerbtInfoGrupo implements java.awt.event.ActionListener{
        
        @Override
            public void actionPerformed(ActionEvent e) {
                
               jDialog5.setVisible(true);
                
            }
        
    }
    
    public class ListinerbtBaixar implements java.awt.event.ActionListener{
        
        @Override
            public void actionPerformed(ActionEvent e) {
                
                System.out.println("btBaixar");
                
            }
        
    }
    
    
}
