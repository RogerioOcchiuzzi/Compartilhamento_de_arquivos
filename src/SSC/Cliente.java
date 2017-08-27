package SSC;

import java.awt.Color;
import java.awt.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.GregorianCalendar;

/**
 *
 * @author roger
 */
public class Cliente implements Runnable{
    
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private String chatServer;
    private Socket client;
    //esse vai ser o endereco do rastreador
    private String enderecoServidor = "192.168.56.101";//"192.168.56.1"
    private java.util.List<Cliente> listaCliente;
    private String enderecoLocal;
    
    private List diretorioGrupoRaiz = new List();
    private LerArquivos lerArquivos;
    private boolean primeiroEnvio = true;
    private TelaInicial telaInicial;
    private String acaoTagServidor;
    private ArrayList<String> buffer;
    private String diretorioRequisitado = "";
    private boolean diretorioVazio ;
    private ArquivoConfig arquivoConfig;
    private Blockchain blockchain;
    private Servidor servidor;
    
    private String formatoData = "dd/MM/yyyy";
    private String formatoHora = "HH:mm";//"h:mm - a";
    private String dataAtual, horaAtual;
    
    private String arquivoQeuEstaEmNuvem = "";
    private InputStream in ;
    private OutputStream out;

    @Override
    public void run() {
        
        lerArquivos = new LerArquivos();
        buffer = new ArrayList<>();
        diretorioVazio = false;
        arquivoConfig = new ArquivoConfig();
                
        blockchain = new Blockchain();
        
        
    try{
            
            connectToServer();
            getStreams();
            processConnection();
                        
        }catch(IOException io){
            
            //io.printStackTrace();
            //System.out.println("Cliente: erro na conexão");
        }  
//        }finally{
//        
//            closeConnection();
//        }    
    
    }
    
    public void setListaClientes(java.util.List<Cliente> lc){
        
        listaCliente = lc;
        
    }
    public void setServidor(Servidor s){
        
        servidor = s;
        
    }
    
    public void setEnderecoServidor(String servidor){
        
        enderecoServidor = servidor;
        
    }
    
    public void setEnderecoLocal(String local){
        
        enderecoLocal = local;
        
    }
    
    public void setDiretorioGrupoRaiz(List diretorioGrupoRaiz){
        
        this.diretorioGrupoRaiz = diretorioGrupoRaiz;
        
    }
    
    public void setTelaInicial(TelaInicial ti){
        
        telaInicial = ti;
        
    }
    
    public void verificaSeEDiretorio(String localizacao){
         
        sendData("VERIFICACAO_DIRETORIO>>>" + localizacao);
        
    }
    
    public void requisitarDownload(String diretorio){
        
        arquivoQeuEstaEmNuvem = diretorio;
        
        sendData("REQUISICAO_DOWNLOAD>>>" + diretorio);
        
    }
    
    public void requisitaDiretorioServer(String diretorio){
        
        //System.out.println("Requisitou este diretorio " + diretorio);
        
        diretorioRequisitado = diretorio;
        
        sendData("REQUISICAO_DIRETORIO>>>" + diretorio);
    }
   
    private void connectToServer() throws IOException{
        
        //System.out.println("Attempting connection\n");
        
        //client = new Socket(InetAddress.getByName(chatServer), 12345);
        //client = new Socket(InetAddress.getByName("192.168.56.101"), 12345);
        
//        System.err.println("Tentando se conectar a: " + enderecoServidor);
        
        client = new Socket(InetAddress.getByName(enderecoServidor), 7300);
        
//        System.err.println("Connected to: " +
//                client.getInetAddress().getHostName());
                
    }
    
    private void getStreams() throws IOException{
        
        output = new ObjectOutputStream(client.getOutputStream());
        output.flush();
        
        input = new ObjectInputStream(client.getInputStream());
        
        //System.out.println("\nGot I/O streams\n");
        
        listaCliente.add(this);
        
    }
    
    private void processConnection() throws IOException{
                
        do{
            
            try{
                
                message = (String) input.readObject();
                //System.err.println("\n" + message);
                
                processaStringRecebida(message);
                
            }catch(ClassNotFoundException C){
                
                System.err.println("\nUnknow object type received");
                
            }
            
        }while(!message.equals("SERVER>>> TERMINATE"));
        
    }
    
    private void processaStringRecebida(String recebida){
        
        //Aqui garante que o primeiro envio vá apenas
        //uma vez
        if(primeiroEnvio){
            
            primeiroEnvioParaServidor();                    
            primeiroEnvio = false;
        }else if(recebida.contains("ARQUIVOS_DIRETORIO_REQUISITADO>>>")){
            
            String diretorioArquivos = recebida.substring(
            new String("SERVER>>> ARQUIVOS_DIRETORIO_REQUISITADO>>>C:\\TCC").length());
                    
            ArquivosNuvem(diretorioArquivos);
            //System.out.println(diretorioArquivos);
            
        }else if(recebida.contains("INICIAR_TRANSFERENCIA_INFORMACOES_DIRETORIO>>>")){
            
            acaoTagServidor = "INICIAR_TRANSFERENCIA_INFORMACOES_DIRETORIO>>>";
            
        }else if(recebida.contains("FINALIZAR_TRANSFERENCIA_INFORMACOES_DIRETORIO>>>")){
            
            acaoTagServidor = "FINALIZAR_TRANSFERENCIA_INFORMACOES_DIRETORIO>>>";
            
        }else if(recebida.contains("DIRETORIO_SEM_ARQUIVOS>>>")){
            
            diretorioVazio = true;
            
        }else if(recebida.contains("VERIFICACAO_DIRETORIO>>>")){
            
            //System.out.println("SSC.Cliente.processaStringRecebida()" + recebida.substring("SERVER>>> VERIFICACAO_DIRETORIO>>>".length()));
            
            if(recebida.contains("diretorio:")){
                liberaDiretorio(true, recebida.substring("SERVER>>> VERIFICACAO_DIRETORIO>>>diretorio:".length()));
            }else{
                liberaDiretorio(false , recebida.substring("SERVER>>> VERIFICACAO_DIRETORIO>>>arquivo:".length()));
            }
            
        }else if(recebida.contains("SERVER>>> INFORMACOES_BLOCKCHAIN>>>")){
            
            adicionaBloco(recebida);
            
        }else if(recebida.contains("SERVER>>> CONEXOES>>>")){
            
            seConectaAOutros(recebida.substring("SERVER>>> CONEXOES>>>".length()));
            
        }
        
        if(recebida.contains("INICIAR_ENVIO>>>")){
            
            try {
                receberArquivo(arquivoQeuEstaEmNuvem, recebida);
            } catch (IOException ex) {
                
                ex.printStackTrace();
                System.out.println("Erro no download");
            }
            
        }else{
            
            
                if(acaoTagServidor == "INICIAR_TRANSFERENCIA_INFORMACOES_DIRETORIO>>>"){

                    if(!recebida.contains("INICIAR_TRANSFERENCIA_INFORMACOES_DIRETORIO>>>")){
                         buffer.add(recebida);
                    }           

                }else if(acaoTagServidor =="FINALIZAR_TRANSFERENCIA_INFORMACOES_DIRETORIO>>>"){

                    if(!diretorioVazio){

                        String[] bufferList = new String[buffer.size()];

                        for(int i = 0; i < buffer.size(); i++){

                           bufferList[i] = diretorioRequisitado + "\\" + buffer.get(i).substring(new String("SERVER>>> ").length());
                           
                            //System.out.println("SSC.Cliente.processaStringRecebida()" + bufferList[i]);

                        }
                        
                        if(bufferList.length > 0){
                            
                            //System.out.println("tttttttttttttttttttttt" + bufferList.length);

                            exibeArquivosNuvem(bufferList);
                            
                        }

                    }else{

                        String[] bufferList = new String[1];

                        bufferList[0] = diretorioRequisitado + "\\ (Nenhum arquivo em nuvem)";
                        exibeArquivosNuvem(bufferList);

                        diretorioVazio = false;
                    }            

                    //aqui esvavia o buffer
                    buffer.clear();

                }
            
            
        }   
        
        
    }
    
    private void seConectaAOutros(String conexoes){
        
        //System.err.println("Tentar essas outras conexoes: " + conexoes);
        java.util.List<String> enderecos = new ArrayList<>();
        java.util.List<String> podeConectar = new ArrayList<>();
        
        while(conexoes.length() > 0){
            
            enderecos.add(conexoes.substring(0, conexoes.indexOf(";")));
            podeConectar.add("sim");
            conexoes = conexoes.substring(conexoes.indexOf(";") + 1);
            
            //System.err.println("qqqqqqqqqqqqq "+ enderecos.get(0));
            
        }
        
        if(enderecoLocal == null){
            enderecoLocal = "";
        }
        
        for(int i = 0; i < listaCliente.size(); i++){
            
            for(int j = 0; j < enderecos.size(); j++){
                
//                System.out.println("getHostAddress" + listaCliente.get(i).client.getInetAddress().getHostAddress());
//                System.out.println("enderecos" + enderecos.get(j));
//                System.out.println("enderecoLocal" + enderecoLocal);
                
                if(listaCliente.get(i).client.getInetAddress().getHostAddress().contentEquals(enderecos.get(j)) ||
                        enderecoLocal.contentEquals(enderecos.get(j))){
                    
                     podeConectar.set(j, "não");
                     
                }
                
            }
            
        }
        
        for(int k = 0; k < enderecos.size(); k++){
            
            if(podeConectar.get(k).contentEquals("sim")){
                
                if(!enderecoLocal.contentEquals(enderecos.get(k))){
                    
                    criaNovoCliente(enderecos.get(k));
                    
                }
                
                //System.err.println("tttttttttttttttttttttttttt " + enderecos.get(k));
                
            }
            
        }
        
        
    }
    
    public void criaNovoCliente(String endereco){
        
        try{
            
            Cliente cliente = new Cliente();
        
            cliente.setEnderecoServidor(endereco);
            cliente.setListaClientes(listaCliente);
            cliente.setEnderecoLocal(enderecoLocal);
            cliente.setDiretorioGrupoRaiz(diretorioGrupoRaiz);
            cliente.setTelaInicial(telaInicial);

            Thread threadcliente = new Thread(cliente);

            threadcliente.start();
                        
        }catch(Exception e){
            
            //System.out.println("Cliente ja estava conectado");
            
        }
        
    }
    
    private void adicionaBloco(String informacoes){
        
        blockchain.adicionaBloco(informacoes.substring("SERVER>>> INFORMACOES_BLOCKCHAIN>>>".length()));
        
        telaInicial.adicionaLog("Adicionou um novo bloco!");
        
    }
    
    private void liberaDiretorio(boolean liberaDiretorio, String localizacaoRequerida){
        
        for(int i = 0; i < telaInicial.listaTabs.size(); i++){
            
            if(localizacaoRequerida.substring(1, (localizacaoRequerida.substring(1).indexOf('\\') + 1)).contentEquals(telaInicial.listaTabs.get(i).getNomeNovoTab())){
                
                telaInicial.listaTabs.get(i).setEDiretorio(liberaDiretorio);
                
            }
            
           
        }
        
        for(int i = 0; i < telaInicial.listaTabs.size(); i++){
            
            if(localizacaoRequerida.substring(1, (localizacaoRequerida.substring(1).indexOf('\\') + 1)).contentEquals(telaInicial.listaTabs.get(i).getNomeNovoTab())){
                
                telaInicial.listaTabs.get(i).setServidorJaRespondeu(true);
            }
            
           
        }
        
    }
    
    private void receberArquivo(String arquivo, String tamanhoArquivo) throws IOException{
        
        
        in = client.getInputStream();
        
        File fileTeste = new File(arquivo.substring(0, arquivo.lastIndexOf("\\")));
        
        if (!fileTeste.exists()) {
            //let's try to create it
            try {
                fileTeste.mkdirs();
            } catch (SecurityException secEx) {
                //handle the exception
                secEx.printStackTrace(System.out);
                fileTeste = null;
            }
        }
        
        //out = new FileOutputStream("C:\\TCC\\xx\\x.txt");
        out = new FileOutputStream(arquivo);
        
        //byte [] b = new byte[16*1024];
        
        int tamanhoArquivoReal = new Integer(tamanhoArquivo.substring("SERVER>>> INICIAR_ENVIO>>>".length()));

        byte [] b = new byte[tamanhoArquivoReal];
        
        if(tamanhoArquivoReal < 30){
            
            int count = in.read(b);
            out.write(b, 0, count);
            
            
        }else{
            
            int count, verificarTamanhoArquivo = 0;
            while ((count = in.read(b)) >0 && verificarTamanhoArquivo < tamanhoArquivoReal) {
                 //System.out.println("SSC.Cliente.receberArquivo()" + count);
                     out.write(b, 0, count);

                     verificarTamanhoArquivo = verificarTamanhoArquivo + count;
             }
            
        }
        
        //System.out.println("new file recieved !");
        
        telaInicial.adicionaLog("Arquivo baixado com sucesso!");
        
        pedirBlockchain();
        
        out.flush();
        out.close();

    }
    
    public void pedirBlockchain(){
        
                java.util.List<String> leitura = new ArrayList<String>();
        
        leitura = arquivoConfig.lerArquivo();
        
        String informacoes = "INFORMACOES_BLOCKCHAIN>>>";
        
        for(int i = 0; i < leitura.size(); i++){
            
            if(leitura.get(i).contains("Nome:")){
                
                informacoes = informacoes + leitura.get(i) + ";";
                
            }else if(leitura.get(i).contains("CPF:")){
                
                informacoes = informacoes + leitura.get(i) + ";";
                
            }
            
        }
        
        
        java.util.Date agora = new java.util.Date();
        SimpleDateFormat formata = new SimpleDateFormat(formatoData);
        dataAtual = formata.format(agora);
        formata = new SimpleDateFormat(formatoHora);
        horaAtual = formata.format(agora);
        //System.out.println(dataAtual+" " + horaAtual);
        
        informacoes = informacoes + dataAtual + ";" + horaAtual + ";";
                
        sendData("REQUISICAO_BLOCKCHAIN>>>" + informacoes);
        
    }
    
    //este metodo recebe os arquivos da pasta que foi requisitada
    //e ativa o metodo de atualizar a lista na classe novoTab
    private void exibeArquivosNuvem(String[] arquivos){
                
        for(int i = 0; i < telaInicial.listaTabs.size(); i++){
            
            //if(arquivos[0].contentEquals("\\" + telaInicial.listaTabs.get(i).getNomeNovoTab())){
            if(arquivos[0].substring(1, (arquivos[0].substring(1).indexOf('\\') + 1)).contentEquals(telaInicial.listaTabs.get(i).getNomeNovoTab())){
            
                telaInicial.listaTabs.get(i).setSemConexao(false);
                
                telaInicial.listaTabs.get(i).atualizaPasta(arquivos, diretorioRequisitado);
                
                
            }
            
           
        }
        
    }
    
    private void ArquivosNuvem(String arquivosExternos){
        
        for(int i = 0; i < telaInicial.listaTabs.size(); i++){
            
            if(arquivosExternos.substring(1, (arquivosExternos.substring(1).indexOf('\\') + 1)).contentEquals(telaInicial.listaTabs.get(i).getNomeNovoTab())){
                
                telaInicial.listaTabs.get(i).listaArquivosNuvem(arquivosExternos.substring(telaInicial.listaTabs.get(i).getNomeNovoTab().length() + 2));
                
            }
            
           
        }
        
    }
    
    private void primeiroEnvioParaServidor(){
        
        //aqui manda os grupos para o servidor
        for(int i = 0; i < diretorioGrupoRaiz.getItemCount(); i++){

            sendData("PRIMEIRA_CONEXAO>>>" + diretorioGrupoRaiz.getItem(i));
            
            //aqui seta esta classe/instancia para ser acessada  
            //pelos tabs graficos que foram instanciados
            telaInicial.listaTabs.get(i).setCliente(this);

        }
    }
    
    private void closeConnection(){
        
        System.out.println("\nClosing connection");
        
        try{
            
            output.close();
            input.close();
            client.close();
            
        }catch (IOException io){
            
            //io.printStackTrace();
            
            System.out.println("Cliente não conseguiu conectar ao servidor");
            
        }
        
    }
    
    public void sendData(String message){
        
        try{
            
            output.writeObject("CLIENT>>> " + message);
            output.flush();
            //System.err.println("\nPrint do client\nCLIENT>>> " + message);
            
        }catch(IOException io){
            
            System.err.println("\nError writing object");
            
        }
        
    }
    
}
