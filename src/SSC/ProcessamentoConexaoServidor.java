package SSC;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roger
 */
public class ProcessamentoConexaoServidor implements Runnable{

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;
    private int counter = 1;
    
    private List diretorioGrupoRaiz = new List();
    private LerArquivos lerArquivos;
    private InputStream in ;
    private OutputStream out;
    private String enderecoArquivoParaBlockchain;
    private ArquivoConfig arquivoConfig;
    private Blockchain blockchain;
    private TelaInicial telaInicial;
    private java.util.List<String> conexoesAdiquiridas;
    private Servidor servidor;
    
    public void setServidor(Servidor s){
        
        servidor = s;
        
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }
    
    public void setConexoesAdiquiridas(java.util.List<String> ca) {
        conexoesAdiquiridas = ca;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setDiretorioGrupoRaiz(List diretorioGrupoRaiz) {
        this.diretorioGrupoRaiz = diretorioGrupoRaiz;
    }

    public void setLerArquivos(LerArquivos lerArquivos) {
        this.lerArquivos = lerArquivos;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

    public void setEnderecoArquivoParaBlockchain(String enderecoArquivoParaBlockchain) {
        this.enderecoArquivoParaBlockchain = enderecoArquivoParaBlockchain;
    }

    public void setArquivoConfig(ArquivoConfig arquivoConfig) {
        this.arquivoConfig = arquivoConfig;
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public void setTelaInicial(TelaInicial telaInicial) {
        this.telaInicial = telaInicial;
    }
    

     
    
    @Override
    public void run() {
        
        lerArquivos = new LerArquivos();
        
        enderecoArquivoParaBlockchain = "";
        
        arquivoConfig = new ArquivoConfig();
        
        blockchain = new Blockchain();
        
        try {
            getStreams();
        } catch (IOException ex) {
            Logger.getLogger(ProcessamentoConexaoServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String message = "Connection sucessful";
        sendData(message);
        
        do{
            
            try{
                
                
                message = ( String ) input.readObject();
                //System.err.println("\n" + message);
                
                processaStringRecebida(message);
                
            } catch (ClassNotFoundException ex) {
                
                servidor.closeConnection();
                
                telaInicial.adicionaLog("Erro na conexão!");
                
                break;
                
                
            } catch (IOException ex) {
                
                servidor.closeConnection();
                
                telaInicial.adicionaLog("Erro na conexão!");
                
                break;
                
                //Logger.getLogger(ProcessamentoConexaoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } while(!message.equals("CLIENT>>> TERMINATE"));
        
                    try{

                output.close();
                input.close();

            }catch (IOException IO){
                IO.printStackTrace();
            }
        
    }
    
    
    private void getStreams() throws IOException{
        
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        
        input = new ObjectInputStream(connection.getInputStream());
        
        //System.out.println("\nGot I/O streams\n");
    }
    
    private void processaStringRecebida(String recebida){
        
        if(recebida.contains("PRIMEIRA_CONEXAO")){
            
            primeiraConexao(recebida);
            
        }else if(recebida.contains("REQUISICAO_DIRETORIO>>>")){
            
            leDiretorio(recebida.substring(new String("CLIENT>>> REQUISICAO_DIRETORIO>>>").length() ));
            
        }else if(recebida.contains("REQUISICAO_DOWNLOAD>>>")){
            
            enviaArquivo(recebida.substring("CLIENT>>> REQUISICAO_DOWNLOAD>>>".length()));
            
        }else if(recebida.contains("VERIFICACAO_DIRETORIO>>>")){
            
            verificaSeEArquivoServidor(recebida.substring("CLIENT>>> VERIFICACAO_DIRETORIO>>>".length()));
            
        }else if(recebida.contains("CLIENT>>> REQUISICAO_BLOCKCHAIN>>>")){
            
            enviaInformacoesBlockchain(recebida.substring("CLIENT>>> REQUISICAO_BLOCKCHAIN>>>INFORMACOES_BLOCKCHAIN>>>".length()));
            
        }
                
    }
    
    private void enviaInformacoesBlockchain(String informacoesCliente){
        
        java.util.List<String> leitura = new ArrayList<String>();
        
        leitura = arquivoConfig.lerArquivo();
        
        String informacoes = "";
        
        for(int i = 0; i < leitura.size(); i++){
            
            if(leitura.get(i).contains("Nome:")){
                
                informacoes = informacoes + leitura.get(i) + ";";
                
            }else if(leitura.get(i).contains("CPF:")){
                
                informacoes = informacoes + leitura.get(i) + ";";
                
            }
            
        }
        
        informacoes = informacoes + enderecoArquivoParaBlockchain + ";" + informacoesCliente;
        
        blockchain.adicionaBloco(informacoes);
        
        informacoes = "INFORMACOES_BLOCKCHAIN>>>" + informacoes;
        
        sendData(informacoes);
        
    }
    
    private void verificaSeEArquivoServidor(String localizacao){
        
        File file = new File(Constantes.PASTA_RAIZ + Constantes.BARRA_INVERTIDA + localizacao);
                
        if(file.isFile()){
            
            sendData("VERIFICACAO_DIRETORIO>>>arquivo:" + localizacao);
            
        }else {
            
            sendData("VERIFICACAO_DIRETORIO>>>diretorio:" + localizacao);
            
        }
        
    }
    
    private void enviaArquivo(String diretorio){
        
        enderecoArquivoParaBlockchain = diretorio;

        File file = new File(diretorio);
        
        if(file.exists()){
            
            if(file.isFile()){
                
                
                
                sendData("INICIAR_ENVIO>>>" + (int) file.length());
            
                byte [] b = new byte[(int) file.length()];

                try {
                        in = new FileInputStream(file);
                } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                } 

                try {
                    out = connection.getOutputStream();
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                        int count ;
                        while ((count  = in.read(b))>0) {
                                out.write(b, 0, count);
                        }
                } catch (IOException e1) {
                        // TODO Auto-generated catch block 
                        e1.printStackTrace();
                }

                //deixa aqui para nao dar problema
                sendData("ARQUIVO_ENVIADO");
                telaInicial.adicionaLog("Um arquivo foi enviado!");

                }
           
        }
    
    }
    
    private void leDiretorio(String diretorio){
               
        String[] arquivos = null;
        try {
            arquivos = lerArquivos.LeOsArquivosPastas(diretorio);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(arquivos == null || arquivos.length < 1){
             
            sendData("INICIAR_TRANSFERENCIA_INFORMACOES_DIRETORIO>>>");
            
            sendData("DIRETORIO_SEM_ARQUIVOS>>>");
            
            sendData("FINALIZAR_TRANSFERENCIA_INFORMACOES_DIRETORIO>>>");
            
        }else{
            
            sendData("INICIAR_TRANSFERENCIA_INFORMACOES_DIRETORIO>>>");
            
            for(int i = 0; i < arquivos.length; i++){
                
                sendData(arquivos[i]);
                
            }
            
            sendData("FINALIZAR_TRANSFERENCIA_INFORMACOES_DIRETORIO>>>");            
        }
       
    }
    
    private void primeiraConexao(String recebida){
        
        //manda as conecxoes que ja estao ativas
        String conexoesAd = "CONEXOES>>>";
        
        for(String cad: conexoesAdiquiridas){
            
            conexoesAd = conexoesAd + cad + ";";
            
        }
        
        sendData(conexoesAd);
//        System.err.println(conexoesAd);
        
        //aqui verifica se este usuário esta nos mesmos grupos 
        //do usuário que está tentando se conectar
                
        for(int i = 0; i < diretorioGrupoRaiz.getItemCount(); i++){

                if(recebida.endsWith(diretorioGrupoRaiz.getItem(i).substring(7))){
                    
                        String[] ArquivosPrimeiroDiretorio = null;

                        try {
                            ArquivosPrimeiroDiretorio = lerArquivos.LeOsArquivosPastas(diretorioGrupoRaiz.getItem(i).substring(7));
                            sendData("ESTA_NO_GRUPO>>>" + diretorioGrupoRaiz.getItem(i));
                        } catch (IOException ex) {
                            System.err.println("\n Erro ao ler Arquivos em " + diretorioGrupoRaiz.getItem(i));
                        }


                        for(String arquivos : ArquivosPrimeiroDiretorio){

                            sendData("ARQUIVOS_DIRETORIO_REQUISITADO>>>" + diretorioGrupoRaiz.getItem(i) + "\\" + arquivos);

                        }

                }
                 
        }
        
    }
    
    public void sendData(String message){
        
        try{
            
            output.writeObject("SERVER>>> " + message);
            output.flush();
            //System.err.println("\nprint do servidor\nSERVER>>> " + message);
            
        } catch (IOException ex) {
            System.err.println("\nError writing object");
        }
        
    }
    
}
