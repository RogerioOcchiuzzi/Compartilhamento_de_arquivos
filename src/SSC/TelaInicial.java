package SSC;

import java.awt.Desktop;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static sun.security.jgss.GSSUtil.login;


/**
 *
 * @author roger
 */
public class TelaInicial extends javax.swing.JFrame {
    
    /**
     * Creates new form TelaInicial
     */
    
    private LerArquivos lerArquivos;
    private List diretorioGrupo = new List();
    private List diretorioGrupoRaiz = new List();
    private boolean criarNovosGrupos = false;
    public ArrayList<NovoTab> listaTabs ;
    private Cliente cliente;
    private ArquivoConfig arquivoConfig;
    private Blockchain blockchain;
    private java.util.List<Cliente> listaCliente;
    private String formatoHora = "HH:mm";
            
    public TelaInicial() {
        initComponents();
        
        listaTabs = new ArrayList();
        
        lerArquivos = new LerArquivos();
        
        arquivoConfig = new ArquivoConfig();
        arquivoConfig.setTelaInicial(this);
        arquivoConfig.iniciarPrograma();
        
        blockchain = new Blockchain();
        blockchain.setTelaInicial(this);
        
        manipulaArquivoConfig();
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setCliente(Cliente cl) {
        cliente = cl;
    }
    
    public void setListaClientes(java.util.List<Cliente> lc){
        
        listaCliente = lc;
        
    }

    public void setDiretorioGrupo(List diretorioGrupo) {
        this.diretorioGrupo = diretorioGrupo;
    }
    
    public void setDiretorioGrupoRaiz(List diretorioGrupoRaiz) {
        this.diretorioGrupoRaiz = diretorioGrupoRaiz;
    }

    public void setCriarNovosGrupos(boolean criarNovosGrupos) {
        this.criarNovosGrupos = criarNovosGrupos;
    }
    
    public void manipulaArquivoConfig(){
        
        if(!verificaCadastro()){
            
            forcaCadastro();
            
        }
        
    }
    
    public void adicionaLog(String texto){
        
        String textoLog = jTextPane3.getText();
        
      // if(!jTextPane3.getText().contains(texto) || texto.contains("!")){
            
            java.util.Date agora = new java.util.Date();
            SimpleDateFormat formata = new SimpleDateFormat();
            formata = new SimpleDateFormat(formatoHora);
            String horaAtual = formata.format(agora);
            //System.out.println(dataAtual+" " + horaAtual);
            
            jTextPane3.setText(textoLog + "\n" + horaAtual + " - " + texto);
            
        //}
        
    }
    
    public boolean verificaCadastro(){
        
        java.util.List<String> textoAuxiliar  = new ArrayList<String>();
        
        textoAuxiliar = arquivoConfig.lerArquivo();
        
        boolean temCadastro = true;
        
        for(int i = 0; i < textoAuxiliar.size(); i++){
            
            if((textoAuxiliar.get(i).contains("Nome:") && textoAuxiliar.get(i).length() == "Nome:".length()) 
                    || (textoAuxiliar.get(i).contains("CPF:") && textoAuxiliar.get(i).length() == "CPF:".length()) ){
                
                temCadastro = false;
                
            }
            
        }
        
        return temCadastro;
    }
    
    public void forcaCadastro(){
        
        //System.out.println("SSC.TelaInicial.forcaCadastro() nao tem cadastro");
        
        jDialog4.setVisible(true);
        jDialog4.setAlwaysOnTop(true);
        
    }
    
    public void adicionaCadastroArquivoConfig(String nome, String cpf){
        
        arquivoConfig.adicionaOuMudaCadastro(nome, cpf);
        
    }
    
    /**
     * esse metodo adiquire os diretorios na pasta raiz tcc
     * e cria tabs com eles
     *
     * @param diretoriosTcc */
    public void tabsGrupoInicial(ArrayList diretoriosTcc){
        
        java.util.List<String> nomesTabs = new ArrayList<String>();
        
        int percorreLista = 0;
        
        for (Object diretoriosTcc1 : diretoriosTcc) {
                         
            try {
                novoTab(diretoriosTcc.get(percorreLista).toString(), lerArquivos.LeOsArquivosPastas(diretoriosTcc.get(percorreLista).toString()));
            } catch (IOException ex) {
                Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            nomesTabs.add(listaTabs.get(percorreLista).getNomeNovoTab());
              
            percorreLista++; 
            
        }
        
        //aqui verifica se existemc os blockchains
        blockchain.verificaECria(nomesTabs);
        
    }
    
    /**
     * Metodo que criara os tabs que serão os grupos
     * @param nomeTab
     */
    public void novoTab(String nomeTab, String[] arquivosDentroPasta){
        
        NovoTab novoT = new NovoTab();
        
        novoT.setJDialog5(jDialog5);
        novoT.setJDialog6(jDialog6);
        novoT.setTextoBlockchain(jTextPane2);
        novoT.setJTabbedPane1(jTabbedPane1);
        novoT.setDiretorioGrupo(diretorioGrupo);
        novoT.setDiretorioGrupoRaiz(diretorioGrupoRaiz);
        novoT.setCriarNovosGrupos(criarNovosGrupos);
        novoT.setListaClientes(listaCliente);
        novoT.criarTab(nomeTab, arquivosDentroPasta);
        
        listaTabs.add(novoT);
            
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jDialog2 = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jDialog3 = new javax.swing.JDialog();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jDialog4 = new javax.swing.JDialog();
        jLabel7 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jDialog5 = new javax.swing.JDialog();
        jLabel10 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jDialog6 = new javax.swing.JDialog();
        jButton9 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        jDialog1.setTitle("Criar novo grupo");
        jDialog1.setLocation(new java.awt.Point(250, 150));
        jDialog1.setMinimumSize(new java.awt.Dimension(300, 200));
        jDialog1.setResizable(false);

        jLabel4.setText("Digite o nome do novo grupo");

        jTextField1.setText("Digite nome");
        jTextField1.setToolTipText("");
        jTextField1.setVerifyInputWhenFocusTarget(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Criar grupo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        jDialog2.setTitle("Entar em um grupo");
        jDialog2.setLocation(new java.awt.Point(250, 150));
        jDialog2.setMinimumSize(new java.awt.Dimension(300, 200));
        jDialog2.setResizable(false);

        jLabel5.setText("Digite o nome do grupo que deseja entrar");

        jTextField2.setToolTipText("");
        jTextField2.setVerifyInputWhenFocusTarget(false);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton2.setText("Entrar no grupo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField2)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        jLabel5.getAccessibleContext().setAccessibleName("Digite o nome do grupo que dajaja entrar");

        jDialog3.setTitle("Deletar grupo");
        jDialog3.setLocation(new java.awt.Point(250, 150));
        jDialog3.setMinimumSize(new java.awt.Dimension(320, 200));
        jDialog3.setResizable(false);

        jLabel6.setText("Tem certeza que deseja deletar esta grupo?");

        jTextField3.setEditable(false);
        jTextField3.setText("dfgdfg");
        jTextField3.setToolTipText("");
        jTextField3.setVerifyInputWhenFocusTarget(false);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jButton3.setText("Deletar grupo");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField3)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        jDialog4.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jDialog4.setTitle("Fazer cadastro");
        jDialog4.setLocation(new java.awt.Point(250, 150));
        jDialog4.setMinimumSize(new java.awt.Dimension(320, 300));
        jDialog4.setResizable(false);

        jLabel7.setText("Fazer cadastro:");

        jButton5.setText("Concluir cadastro");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel8.setText("Digite o nome completo:");

        jLabel9.setText("Digite o CPF");

        javax.swing.GroupLayout jDialog4Layout = new javax.swing.GroupLayout(jDialog4.getContentPane());
        jDialog4.getContentPane().setLayout(jDialog4Layout);
        jDialog4Layout.setHorizontalGroup(
            jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jButton5)
                    .addComponent(jLabel7)
                    .addComponent(jTextField5)
                    .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jDialog4Layout.setVerticalGroup(
            jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jDialog5.setTitle("Informações do grupo");
        jDialog5.setLocation(new java.awt.Point(250, 150));
        jDialog5.setMinimumSize(new java.awt.Dimension(450, 300));

        jLabel10.setText("Você É/Não  é o adiministrador do grupo");

        jButton8.setText("Fechar");
        jButton8.setFocusPainted(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel11.setText("Chave publica:(é a que deve ser passada para entrar os grupos)");

        jLabel12.setText("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        jLabel13.setText("Chave privada:(se for o adm do grupo)");

        jLabel14.setText("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        javax.swing.GroupLayout jDialog5Layout = new javax.swing.GroupLayout(jDialog5.getContentPane());
        jDialog5.getContentPane().setLayout(jDialog5Layout);
        jDialog5Layout.setHorizontalGroup(
            jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog5Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jButton8)
                    .addComponent(jLabel10))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jDialog5Layout.setVerticalGroup(
            jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog5Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addGap(37, 37, 37)
                .addComponent(jButton8)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jDialog6.setTitle("Blockchain");
        jDialog6.setLocation(new java.awt.Point(250, 150));
        jDialog6.setMinimumSize(new java.awt.Dimension(506, 565));
        jDialog6.setResizable(false);

        jButton9.setText("Fechar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(jTextPane2);

        javax.swing.GroupLayout jDialog6Layout = new javax.swing.GroupLayout(jDialog6.getContentPane());
        jDialog6.getContentPane().setLayout(jDialog6Layout);
        jDialog6Layout.setHorizontalGroup(
            jDialog6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog6Layout.createSequentialGroup()
                .addContainerGap(415, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addGap(26, 26, 26))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jDialog6Layout.setVerticalGroup(
            jDialog6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Secure Shared Cloud");
        setBackground(new java.awt.Color(255, 255, 255));
        setLocation(new java.awt.Point(200, 100));
        setMaximumSize(new java.awt.Dimension(2352, 4394));
        setMinimumSize(new java.awt.Dimension(577, 740));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("grupos ativos");

        jButton4.setText("Criar novo grupo");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton4.setFocusPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setOpaque(true);

        jTextPane3.setEditable(false);
        jTextPane3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextPane3.setText("Secure Shared Cloud log:\n");
        jScrollPane3.setViewportView(jTextPane3);

        jTabbedPane1.addTab("Log", jScrollPane3);

        jButton6.setText("Deletar grupo");
        jButton6.setFocusPainted(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Entrar em um grupo");
        jButton7.setFocusPainted(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 153));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Offline");
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel3.setOpaque(true);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));

        jMenu1.setText("arquivo");

        jMenuItem4.setText("Abrir diretorio raiz");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem1.setText("Fazer cadastro");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("ajuda");

        jMenuItem5.setText("pagina official do projeto");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4))
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton7)
                            .addComponent(jButton6)
                            .addComponent(jButton4))
                        .addGap(5, 5, 5)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("pag");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        
        jDialog1.setVisible(true);
        
        jDialog1.setLocation(getX() + 50, getY() + 50);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        //aqui permite que o programa crtie novos grupos
        criarNovosGrupos = true;
        
        lerArquivos.criaGrupo(jTextField1.getText());
        String[] preencher = {};
        novoTab(jTextField1.getText(), preencher);
        
        blockchain.criaNovoParaGrupo(jTextField1.getText());
        
        jDialog1.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        //aqui permite que o programa crtie novos grupos
        criarNovosGrupos = true;
        
        lerArquivos.criaGrupo(jTextField2.getText());
        String[] preencher = {};
        novoTab(jTextField2.getText(), preencher);
        
        blockchain.criaNovoParaGrupo(jTextField2.getText());
        
        jDialog2.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
                
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        
        jDialog2.setVisible(true);
        
        jDialog2.setLocation(getX() + 50, getY() + 50);
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        if (jTabbedPane1.getSelectedIndex() > 0){
            
            System.out.println("Deletou grupo " + jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
            lerArquivos.deletarGrupo(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));        
            jTabbedPane1.remove(jTabbedPane1.getSelectedIndex());
            diretorioGrupoRaiz.remove(jTabbedPane1.getSelectedIndex() - 1);
            diretorioGrupo.remove(jTabbedPane1.getSelectedIndex() - 1);
            
        }            
        
        jDialog3.setVisible(false);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if (jTabbedPane1.getSelectedIndex() > 0){
            
           jTextField3.setText("Grupo: " + jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
        
            jDialog3.setVisible(true); 
            jDialog3.setLocation(getX() + 50, getY() + 50);
        }        
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        try {
            // TODO add your handling code here:

            Runtime.getRuntime().exec("explorer C:\\TCC");
        } catch (IOException ex) {
            Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        
        jDialog4.setVisible(true);
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       
        String textoNome = jTextField5.getText();
        String textoCPF = jTextField6.getText();
        
        if(!textoNome.isEmpty() && !textoCPF.isEmpty()){
            
            adicionaCadastroArquivoConfig(textoNome, textoCPF);
        
            jDialog4.setVisible(false);
            
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        
        jDialog5.setVisible(false);
        
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

                
        jDialog6.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed

                if(Desktop.isDesktopSupported())
        {
                    try {
                        Desktop.getDesktop().browse(new URI("https://github.com/RogerioOcchiuzzi/SSC"));
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(Grupos.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Grupos.class.getName()).log(Level.SEVERE, null, ex);
                    }
        }
                
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JDialog jDialog4;
    private javax.swing.JDialog jDialog5;
    private javax.swing.JDialog jDialog6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    // End of variables declaration//GEN-END:variables

}
