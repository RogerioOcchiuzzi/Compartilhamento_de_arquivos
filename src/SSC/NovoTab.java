package SSC;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import sun.security.ssl.Debug;

/**
 *
 * @author roger
 */
public class NovoTab {
    
    private javax.swing.JPanel painelNovo;
    private JScrollPane rolagemPaineis;
    private javax.swing.JButton btInfoGrupo;
    private javax.swing.JButton btBlockchain;
    private javax.swing.JButton btAbrirPasta;
    private javax.swing.JButton btBaixar;
    private javax.swing.JButton btAtualizar;
    private javax.swing.JList<String> listaDiretrios;
    
    private javax.swing.JDialog jDialog5;
    private javax.swing.JDialog jDialog6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane jTextPane2;
    
    private List diretorioGrupo = new List();
    private List diretorioGrupoRaiz = new List();
    private boolean criarNovosGrupos = false;
    private static final String ARQUIVO_NUVEM = "<html><font size=\"4\" color=\"#6666CC\"><b>nuvem&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";//"nuvem---";
    private static final String ARQUIVO_LOCAL = "<html><font size=\"4\"  color=\"#555555\"><b>local&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";//"local------";
    private static final String STRING_STATUS = "<html><font size=\"4\"><b>Status&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Arquivos";//"Status----Arquivos";
    private static final String STRING_VOLTAR = "<html><font size=\"4\"  color=\"#663300\"><b>voltar/..";
    private String nomeNovoTab;
    private Cliente cliente;
    private boolean diretorioNuvem;
    private String localizaçãoNuvem;
    private boolean eDiretorio;
    private boolean servidorJaRespondeu;
    private java.util.List<Cliente> listaCliente;
    private String bufferDiretorioRequisitado;
    private java.util.List<String> bufferarquivosNuvem;
    private boolean semConexao;
    
    public void setCliente(Cliente cl) {
        cliente = cl;
    }
    public void setListaClientes(java.util.List<Cliente> lc){
        
        listaCliente = lc;
        
    }
    
    public void setSemConexao(boolean sc){
        
        semConexao = sc;
        
    }

    public void setDiretorioGrupo(List diretorioGrupo) {
        this.diretorioGrupo = diretorioGrupo;
    }
    
    public void setEDiretorio(boolean eDir) {
        eDiretorio = eDir;
        
    }
    
    public void setTextoBlockchain(javax.swing.JTextPane jTextPane2){
        
        this.jTextPane2 = jTextPane2;
        
    }
    
    public void setServidorJaRespondeu(boolean serv) {
        servidorJaRespondeu = serv;
        
    }
    
    public void setDiretorioGrupoRaiz(List diretorioGrupoRaiz) {
        this.diretorioGrupoRaiz = diretorioGrupoRaiz;
    }

    public void setCriarNovosGrupos(boolean criarNovosGrupos) {
        this.criarNovosGrupos = criarNovosGrupos;
    }
    
    public String getNomeNovoTab(){
        return nomeNovoTab;
    }
    
    public void criarTab(String nomeTab, String[] arquivosDentroPasta){
        
        
        diretorioNuvem = false;
        localizaçãoNuvem = "";
        eDiretorio = false;
        servidorJaRespondeu = false;
        bufferDiretorioRequisitado = "";
        bufferarquivosNuvem = new ArrayList<>();
        semConexao = true;
        
        painelNovo = new javax.swing.JPanel();
        rolagemPaineis = new javax.swing.JScrollPane();
        btBlockchain = new javax.swing.JButton();
        btInfoGrupo = new javax.swing.JButton();
        btAbrirPasta = new javax.swing.JButton();
        btBaixar = new javax.swing.JButton();
        btAtualizar = new javax.swing.JButton();
        btBlockchain.setText("Blockchain");
        btAtualizar.setText("Atualizar");
        btInfoGrupo.setText("Informações");
        btAbrirPasta.setText("Abrir");
        btBaixar.setText("Baixar");
        
        
        nomeNovoTab = nomeTab;
        String[] adicionaStatus = new String[arquivosDentroPasta.length + 1];
        adicionaStatus[0] = STRING_STATUS;
        for(int i = 0; i < arquivosDentroPasta.length; i++){
            adicionaStatus[i + 1] = ARQUIVO_LOCAL + arquivosDentroPasta[i];
        }
        
        /**
         * essa variavel cria uma lista de diretorios que estão dentro do grupo
         */
        listaDiretrios = new javax.swing.JList<>();
        listaDiretrios.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = adicionaStatus;
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        
        listaDiretrios.addMouseListener(new AcoesDaLista());
        
        rolagemPaineis.setViewportView(listaDiretrios);
        
        btBlockchain.addActionListener(new ListenerbtBlockchain());
        
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
                    .addComponent(rolagemPaineis, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(paineisLayout.createSequentialGroup()
                        .addComponent(btBlockchain)
                        .addGap(20, 35, 35)
                         .addComponent(btInfoGrupo)
                        .addGap(20, 35, 35)
                        .addComponent(btAtualizar)
                        .addGap(20, 35, 35)
                        .addComponent(btAbrirPasta)
                        .addGap(30, 35, 35)
                        .addComponent(btBaixar)))
                .addGap(41, 41, 41))
        );
        paineisLayout.setVerticalGroup(
            paineisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paineisLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(rolagemPaineis, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(paineisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btBlockchain)
                    .addComponent(btInfoGrupo)
                    .addComponent(btAtualizar)
                    .addComponent(btBaixar)
                    .addComponent(btAbrirPasta))
                .addGap(25, 25, 25))
        );       
        
        
        if(criarNovosGrupos){
            
            diretorioGrupo.add(Constantes.PASTA_RAIZ + Constantes.BARRA_INVERTIDA + nomeTab);
            diretorioGrupoRaiz.add(Constantes.PASTA_RAIZ + Constantes.BARRA_INVERTIDA + nomeTab);
//            System.out.println("-----------------------------------------" + Constantes.PASTA_RAIZ + Constantes.BARRA_INVERTIDA + nomeTab);
            
        }
        
        jTabbedPane1.addTab(nomeTab, painelNovo);
        
        
    }
    
    /**
     *
     */
    public void setJDialog5(javax.swing.JDialog jDialog5){
        
        this.jDialog5 = jDialog5;
        
    }
    
    public void setJDialog6(javax.swing.JDialog jDialog6){
        
        this.jDialog6 = jDialog6;
        
    }
    
    public void setJTabbedPane1(javax.swing.JTabbedPane jTabbedPane1){
        
        this.jTabbedPane1 = jTabbedPane1;
        
    }
    
    //este metodo adquire as pastas na primeira conexao
    //e atualiza elas
    public void listaArquivosNuvem(String arquivosNuvem){
        
        ListModel<String> listaDiretoriosAuxiliar = listaDiretrios.getModel();
        
        //aqui ve se tem o arquivo localmente
        boolean jaTemArquivo = false;
        
        for(int j = 0; j < listaDiretoriosAuxiliar.getSize(); j++){
        
            if(listaDiretoriosAuxiliar.getElementAt(j).contains(arquivosNuvem)){
                
                jaTemArquivo = true;
               
            }
            
        } 
        
        if(!jaTemArquivo){
            
            String[] stringAuxiliar = new String[listaDiretoriosAuxiliar.getSize() + 1];
            
            for(int i = 0;i < listaDiretoriosAuxiliar.getSize();i++){
            
                stringAuxiliar[i] = listaDiretoriosAuxiliar.getElementAt(i);
            
            } 

            stringAuxiliar[listaDiretoriosAuxiliar.getSize()] = ARQUIVO_NUVEM + arquivosNuvem;
            
            listaDiretrios.setModel(new javax.swing.AbstractListModel<String>() {
                String[] strings = stringAuxiliar;
                public int getSize() { return strings.length; }
                public String getElementAt(int i) { return strings[i]; }
            });
            
        }
               
    }
    
    //este metodo atualiza a lista de arquivos de acordo 
    //com o diretorio requisitado
    public void atualizaPasta(String[] arquivosMistos, String diretorioRequisitado){
        
                 
        diretorioGrupo.replaceItem(Constantes.PASTA_RAIZ + diretorioRequisitado, jTabbedPane1.getSelectedIndex() - 1);
                
        List arquivosAuxiliar = new List();
        
        if(semConexao){
            
            bufferDiretorioRequisitado = diretorioRequisitado;
            
            arquivosAuxiliar.add(STRING_STATUS);

            if(!diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).equalsIgnoreCase(diretorioGrupoRaiz.getItem(jTabbedPane1.getSelectedIndex() - 1))){

                    arquivosAuxiliar.add(STRING_VOLTAR); 
            }
            
            for(String arq: arquivosMistos){
                
                arquivosAuxiliar.add(ARQUIVO_LOCAL + arq);
                
            }
            
        }else{
            
            
            
            if(bufferDiretorioRequisitado.contentEquals(diretorioRequisitado)){
            
                for(String ar: arquivosMistos){

                    bufferarquivosNuvem.add(ar);

                }

            }else{


                bufferDiretorioRequisitado = diretorioRequisitado;

                bufferarquivosNuvem.clear();

                for(String ar: arquivosMistos){

                    bufferarquivosNuvem.add(ar);

                }
            
        }
                
        String[] arquivos = new String[bufferarquivosNuvem.size()];
        
        for(int i = 0; i < arquivos.length; i++){
            
            arquivos[i] = bufferarquivosNuvem.get(i);
            
            
        }          
            
            
            if(!diretorioNuvem){
            
                File file2 = new File(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1));
                                
                arquivosAuxiliar.add(STRING_STATUS);

                if(!diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).equalsIgnoreCase(diretorioGrupoRaiz.getItem(jTabbedPane1.getSelectedIndex() - 1))){

                        arquivosAuxiliar.add(STRING_VOLTAR); 
                }

                if(file2.exists()){

                    //adiciona voltar na lista de opcoes
                    String[] voltarAux = file2.list();

                    for(int i = 0;i < file2.list().length; i++){
                            arquivosAuxiliar.add(ARQUIVO_LOCAL + voltarAux[i]);
                    }


                }

                for(int i = 0; i < arquivos.length;i++ ){

                    boolean jaTemArquivo = false;

                    for(int j = 0; j < arquivosAuxiliar.getItemCount();j++){

                        if(arquivosAuxiliar.getItem(j).contains(arquivos[i].substring(diretorioRequisitado.length() + 1))){
                        
                            jaTemArquivo = true;

                        }

                    }

                    if(!jaTemArquivo){

                        arquivosAuxiliar.add(ARQUIVO_NUVEM + arquivos[i].substring(diretorioRequisitado.length() + 1));

                    }

                }
            
        }else{
            
                arquivosAuxiliar.add(STRING_STATUS);
                
                if(!localizaçãoNuvem.equalsIgnoreCase(diretorioGrupoRaiz.getItem(jTabbedPane1.getSelectedIndex() - 1).substring(Constantes.PASTA_RAIZ.length()))){

                        arquivosAuxiliar.add(STRING_VOLTAR); 
                }

               
                for(int i = 0; i < arquivos.length;i++ ){

                    arquivosAuxiliar.add(ARQUIVO_NUVEM + arquivos[i].substring(diretorioRequisitado.length() + 1));

                }
            
                diretorioNuvem = false;
            
        }
            
            
        }
        
              
            listaDiretrios.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = arquivosAuxiliar.getItems();
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
            });
            

        int contador = 100;
        
        while(contador > 0){
            atualizaLista();
            
            contador--;
        }
   
          
    }  
    
    
    
    
    
    
    
    
    public void atualizaLista() {
        
        List arquivosAuxiliar = new List();
                
        String[] arquivos = new String[bufferarquivosNuvem.size()];
        
        for(int i = 0; i < arquivos.length; i++){
            
            arquivos[i] = bufferarquivosNuvem.get(i);
            
            
        }          
            
            
            if(!diretorioNuvem){
            
                File file2 = new File(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1));
                                
                arquivosAuxiliar.add(STRING_STATUS);

                if(!diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).equalsIgnoreCase(diretorioGrupoRaiz.getItem(jTabbedPane1.getSelectedIndex() - 1))){

                        arquivosAuxiliar.add(STRING_VOLTAR); 
                }

                if(file2.exists()){

                    //adiciona voltar na lista de opcoes
                    String[] voltarAux = file2.list();

                    for(int i = 0;i < file2.list().length; i++){
                            arquivosAuxiliar.add(ARQUIVO_LOCAL + voltarAux[i]);
                    }


                }

                for(int i = 0; i < arquivos.length;i++ ){

                    boolean jaTemArquivo = false;

                    for(int j = 0; j < arquivosAuxiliar.getItemCount();j++){

                        if(arquivosAuxiliar.getItem(j).contains(arquivos[i].substring(bufferDiretorioRequisitado.length() + 1))){
                        
                            jaTemArquivo = true;

                        }

                    }

                    if(!jaTemArquivo){

                        arquivosAuxiliar.add(ARQUIVO_NUVEM + arquivos[i].substring(bufferDiretorioRequisitado.length() + 1));

                    }

                }
            
        }else{
            
                arquivosAuxiliar.add(STRING_STATUS);
                
                if(!localizaçãoNuvem.equalsIgnoreCase(diretorioGrupoRaiz.getItem(jTabbedPane1.getSelectedIndex() - 1).substring(Constantes.PASTA_RAIZ.length()))){

                        arquivosAuxiliar.add(STRING_VOLTAR); 
                }

               
                for(int i = 0; i < arquivos.length;i++ ){

                    arquivosAuxiliar.add(ARQUIVO_NUVEM + arquivos[i].substring(bufferDiretorioRequisitado.length() + 1));

                }
            
                diretorioNuvem = false;
            
        }
        
            if(arquivosAuxiliar.getItemCount() > 3){
                
                for(int i = 0; i < arquivosAuxiliar.getItemCount(); i++){
                    
                    if(arquivosAuxiliar.getItem(i).contains("(Nenhum arquivo em nuvem)")){
                        
                        arquivosAuxiliar.remove(i);
                        
                    }
                    
                }
                
            }
        
                listaDiretrios.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = arquivosAuxiliar.getItems();
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
            });
                
    }
    
    
    
    
    
    
    
    
    
    public class AcoesDaLista extends java.awt.event.MouseAdapter {
        
        
        public void mouseClicked(java.awt.event.MouseEvent evt) {
                
                if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();                    
                                  
                    if(listaDiretrios.getSelectedValue().contains(ARQUIVO_NUVEM)){
                        
                        String localizacaoAuxiliar = diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).substring(Constantes.PASTA_RAIZ.length()) + Constantes.BARRA_INVERTIDA +
                                    (listaDiretrios.getSelectedValue().substring(ARQUIVO_NUVEM.length()));
                        //teste de varias conexoes
                        //cliente.verificaSeEDiretorio(localizacaoAuxiliar);
                        if(!listaCliente.isEmpty()){
                            for(Cliente cl: listaCliente){

                                cl.verificaSeEDiretorio(localizacaoAuxiliar);

                            }
                        }
                        
                        //Se o processador for de um unico core use o thread.sleep
//                        try {
//                            Thread.sleep(100);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(NovoTab.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                        
                        //Se o processador for multi-core use o while no lugar do sleep
                        while(!servidorJaRespondeu){
                            //System.out.println("SSC.NovoTab.AcoesDaLista.mouseClicked()");
                        
                            try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(NovoTab.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        }
                        
                        if(eDiretorio && !listaDiretrios.getSelectedValue().contains("(Nenhum arquivo em nuvem)")){
                            
                            diretorioNuvem = true;
                        localizaçãoNuvem = diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).substring(Constantes.PASTA_RAIZ.length()) + Constantes.BARRA_INVERTIDA +
                                    (listaDiretrios.getSelectedValue().substring(ARQUIVO_NUVEM.length()));
                        //System.out.println("Localização nuvem: " + Constantes.PASTA_RAIZ  + localizaçãoNuvem);
                        //teste de varias conexoes
                        //cliente.requisitaDiretorioServer(localizaçãoNuvem);
                        
                        if(!listaCliente.isEmpty()){
                            for(Cliente cl: listaCliente){

                                cl.requisitaDiretorioServer(localizaçãoNuvem);

                            }
                        }
                            
                        }
                        
                        eDiretorio = false;
                        servidorJaRespondeu = false;
                        
                    }else if(listaDiretrios.getSelectedValue().equals(STRING_VOLTAR)){  
                         
                        String subirDiretorio = diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).substring(0, diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).lastIndexOf("\\"));
                        //System.out.println("Localização atual: " + subirDiretorio.toString());
                        diretorioGrupo.replaceItem(subirDiretorio, jTabbedPane1.getSelectedIndex() - 1);
                           
                        //teste de varias conexoes
                        //cliente.requisitaDiretorioServer(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).substring(Constantes.PASTA_RAIZ.length()));
                        
                        if(!listaCliente.isEmpty()){
                            for(Cliente cl: listaCliente){

                                cl.requisitaDiretorioServer(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).substring(Constantes.PASTA_RAIZ.length()));

                            }
                        }else{
                                
                                File file = new File(subirDiretorio);
                                    
                                String[] arquivosAux = null;
                                
                                if(file.list().length == 0){
                                    
                                    arquivosAux = new String[1];
                                    arquivosAux[0] = "(Nenhum arquivo local)";
                                    
                                }else{
                                    
                                    arquivosAux = file.list();
                                    
                                }
                                
                                String diretorioformatada = subirDiretorio.substring(Constantes.PASTA_RAIZ.length());
                                
                                atualizaPasta( arquivosAux , diretorioformatada );
                            }
               
                        listaDiretrios.addMouseListener(this);
                        
                        rolagemPaineis.setViewportView(listaDiretrios);
                                                    
                    }else if(!listaDiretrios.getSelectedValue().contains(STRING_STATUS) && !listaDiretrios.getSelectedValue().equals(STRING_VOLTAR) && !listaDiretrios.getSelectedValue().contains(ARQUIVO_NUVEM)){
                        
                        
                        File file = new File(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) + Constantes.BARRA_INVERTIDA + 
                                listaDiretrios.getSelectedValue().substring(ARQUIVO_LOCAL.length()));
                                          
                        if(file.isDirectory()){
                                                        
                            diretorioGrupo.replaceItem(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) + Constantes.BARRA_INVERTIDA + 
                                    listaDiretrios.getSelectedValue().substring(ARQUIVO_LOCAL.length())
                                    , jTabbedPane1.getSelectedIndex() - 1);
                            
                            
                            //teste varias conexoes
                            //cliente.requisitaDiretorioServer(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).substring(Constantes.PASTA_RAIZ.length()));
                            
                            if(!listaCliente.isEmpty()){
                                
                                for(Cliente cl: listaCliente){
                            
                                    cl.requisitaDiretorioServer(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).substring(Constantes.PASTA_RAIZ.length()));
                            
                                }
                                
                            }else{
                                
                                file = new File(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1));
                                    
                                String[] arquivosAux = null;
                                
                                if(file.list().length == 0){
                                    
                                    arquivosAux = new String[1];
                                    arquivosAux[0] = "(Nenhum arquivo local)";
                                    
                                }else{
                                    
                                    arquivosAux = file.list();
                                    
                                }
                                
                                String diretorioformatada = diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).substring(Constantes.PASTA_RAIZ.length());
                                
                                atualizaPasta( arquivosAux , diretorioformatada );
                            }    

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

                if(listaDiretrios.getSelectedValue() == null || listaDiretrios.getSelectedValue().contains(STRING_VOLTAR)
                    || listaDiretrios.getSelectedValue().contains(STRING_STATUS) ){

                try {
                    
                    File diretorioExiste = new File((diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) + Constantes.BARRA_INVERTIDA));
                    
                    if(diretorioExiste.exists()){
                        
                        Runtime.getRuntime().exec("explorer " + (diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) + Constantes.BARRA_INVERTIDA));
                        
                    }else{
                                
                        String[] arquivosAux = null;

                        if(diretorioExiste.list().length == 0){

                            arquivosAux = new String[1];
                            arquivosAux[0] = "(Nenhum arquivo local)";

                        }else{

                            arquivosAux = diretorioExiste.list();

                        }

                        String diretorioformatada = diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).substring(Constantes.PASTA_RAIZ.length());

                        atualizaPasta( arquivosAux , diretorioformatada );
                    } 
                    
                    
                    
                } catch (IOException ex) {
                    
                    Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
                    
                }

                }else{

                    try {
                        
                        if(!listaDiretrios.getSelectedValue().contains(ARQUIVO_NUVEM)){
                            
//                            System.out.println("SSC.NovoTab.ListnerbtAbrirPasta.actionPerformed()" +(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) + Constantes.BARRA_INVERTIDA +
//                            listaDiretrios.getSelectedValue().substring(ARQUIVO_LOCAL.length())) );
                        
                            File diretorioExiste = new File((diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) + Constantes.BARRA_INVERTIDA +
                                listaDiretrios.getSelectedValue().substring(ARQUIVO_LOCAL.length())));

                            if(diretorioExiste.exists()){

                                Runtime.getRuntime().exec("explorer " + (diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) + Constantes.BARRA_INVERTIDA +
                                    listaDiretrios.getSelectedValue().substring(ARQUIVO_LOCAL.length())));

                            }
                            
                        }else{
                            
                            String diretioNuvemNaoAcessivel = "Diretorio em nuvem:\n" + diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) + Constantes.BARRA_INVERTIDA +
                            listaDiretrios.getSelectedValue().substring(ARQUIVO_NUVEM.length()) + "\nnão acessivem localmente";
                            
                                JOptionPane.showMessageDialog(null, diretioNuvemNaoAcessivel);
                
                        }
                        
                    
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

                    voltar.add(STRING_STATUS);
                    
                    //teste varias conexoes
                    //cliente.requisitaDiretorioServer(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).substring(Constantes.PASTA_RAIZ.length()));
                    
                    if(!listaCliente.isEmpty()){                    
                        for(Cliente cl: listaCliente){

                            cl.requisitaDiretorioServer(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).substring(Constantes.PASTA_RAIZ.length()));

                        }
                    }
                    
//                    if(!diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1).equalsIgnoreCase(diretorioGrupoRaiz.getItem(jTabbedPane1.getSelectedIndex() - 1))){
//
//                            voltar.add(STRING_VOLTAR); 
//                    }
//
//                    for(int i = 0;i < file2.list().length; i++){
//                        voltar.add(ARQUIVO_LOCAL + voltarAux[i]);
//                    }
//
//                    listaDiretrios.setModel(new javax.swing.AbstractListModel<String>() {
//                        String[] strings = voltar.getItems();
//                        public int getSize() { return strings.length; }
//                        public String getElementAt(int i) { return strings[i]; }
//                    });
                    
                                        
//                    System.out.println("Atualizar: " + diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1));
                
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
                
                if(listaDiretrios.getSelectedValue() ==null ||
                        listaDiretrios.getSelectedValue() == STRING_VOLTAR || 
                   listaDiretrios.getSelectedValue() == STRING_STATUS ||
                        listaDiretrios.getSelectedValue().contentEquals(ARQUIVO_LOCAL)){
                    
//                    System.out.println("Comando para baixar invalido!!!");
                    
                }else{
                    
//                    System.out.println("Requisitoui baixar :" + diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) +
//                            Constantes.BARRA_INVERTIDA + listaDiretrios.getSelectedValue().substring(ARQUIVO_NUVEM.length()));
                    
                    //teste varias cponexoes
//                    cliente.requisitarDownload(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) +
//                            Constantes.BARRA_INVERTIDA + listaDiretrios.getSelectedValue().substring(ARQUIVO_NUVEM.length()));
                    if(!listaCliente.isEmpty()){
                        for(Cliente cl: listaCliente){

                            cl.requisitarDownload(diretorioGrupo.getItem(jTabbedPane1.getSelectedIndex() - 1) +
                                Constantes.BARRA_INVERTIDA + listaDiretrios.getSelectedValue().substring(ARQUIVO_NUVEM.length()));

                        }
                    }
                    
                }
                
                
                
            }
        
    }
    
    public class ListenerbtBlockchain implements java.awt.event.ActionListener{
        
        @Override
            public void actionPerformed(ActionEvent e) {
                
                java.util.List<String> leituraBlockchain = new ArrayList<>();
                
               leituraBlockchain = new Blockchain().lerArquivo(diretorioGrupoRaiz.getItem(jTabbedPane1.getSelectedIndex() - 1));
                
               jDialog6.setVisible(true);
               
               String conteudoBlockchain = "";
               
               for(int i = 0; i < leituraBlockchain.size(); i++){
                   
                   conteudoBlockchain = conteudoBlockchain + leituraBlockchain.get(i) + "\n";
                   
               }
               
               jTextPane2.setEditable(false);
               jTextPane2.setText(conteudoBlockchain);
                
            }
        
    }
    
}
