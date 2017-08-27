package SSC;

import java.awt.Color;
import java.awt.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 *
 * @author roger
 */
public class Servidor implements Runnable{

    public ServerSocket server;
    public Socket connection;
    private int counter = 1;
    private java.util.List<String> conexoesAdiquiridas;
    private int contadorConexoes;
    private java.util.List<Cliente> listaCliente;
    private String enderecoLocal;
    
    private List diretorioGrupoRaiz = new List();
    private TelaInicial telaInicial;
    
     public void setDiretorioGrupoRaiz(List diretorioGrupoRaiz){
        
        this.diretorioGrupoRaiz = diretorioGrupoRaiz;
        
    }
    
     public void setTelaInicial(TelaInicial ti){
         
         telaInicial = ti;
         
     }
     
     public void setListaClientes(java.util.List<Cliente> lc){
        
        listaCliente = lc;
        
    }
    
    private void waitForConnection() throws IOException{
        
        //System.out.println("waiting for connection\n");
        telaInicial.adicionaLog("Tentando se conectar...");
        connection = server.accept();
        enderecoLocal = connection.getLocalAddress().getHostAddress();
        telaInicial.jLabel3.setBackground(Color.decode("#AAFFAA"));
        telaInicial.jLabel3.setText("Online");
        telaInicial.adicionaLog("Conectado.");
        telaInicial.adicionaLog("Endereco local: " + connection.getLocalAddress().getHostAddress());
        if(!connection.getLocalAddress().getHostAddress().contentEquals(connection.getInetAddress().getHostAddress())){
            telaInicial.adicionaLog("Conectado ao IP: " + connection.getInetAddress().getHostAddress());
        }
        
        //System.out.println("Endereco local " + connection.getLocalAddress().getHostAddress());
//        System.err.println("Connection " + counter + " received from: "+
//                connection.getInetAddress().getHostName());
        
    }
       

    public void closeConnection(){
        
        //System.out.println("\nTerminating connection\n");
        
        try{
            
            conexoesAdiquiridas.remove(conexoesAdiquiridas.indexOf(connection.getInetAddress().getHostAddress()));
            
            telaInicial.adicionaLog("Terminou a conexão com o ip: " + connection.getInetAddress().getHostAddress());
            
            connection.close();
            
        }catch (IOException IO){
            IO.printStackTrace();
        }
        
    }

    
    public void criaNovoCliente(){
        
        try{
            
            Cliente cliente = new Cliente();
        
            cliente.setEnderecoServidor(connection.getInetAddress().getHostAddress());
            cliente.setListaClientes(listaCliente);
            cliente.setDiretorioGrupoRaiz(diretorioGrupoRaiz);
            cliente.setEnderecoLocal(enderecoLocal);
            cliente.setServidor(this);
            cliente.setTelaInicial(telaInicial);

            Thread threadcliente = new Thread(cliente);

            threadcliente.start();
                        
        }catch(Exception e){
            
            //.out.println("Cliente ja estava conectado");
            
        }
        
    }
    
    private void conectar(){
        
        if(!conexoesAdiquiridas.contains(connection.getInetAddress().getHostAddress())){
             
            contadorConexoes++;
//            System.err.println("Numero de conexões: " + contadorConexoes);
            
            conexoesAdiquiridas.add(connection.getInetAddress().getHostAddress());
        
            ProcessamentoConexaoServidor PCServidor = new ProcessamentoConexaoServidor();

            PCServidor.setConnection(connection);
            PCServidor.setDiretorioGrupoRaiz(diretorioGrupoRaiz);
            PCServidor.setTelaInicial(telaInicial);
            PCServidor.setConexoesAdiquiridas(conexoesAdiquiridas);
            PCServidor.setServidor(this);

            Thread threadPCServidor = new Thread(PCServidor);
            threadPCServidor.start();
            
            criaNovoCliente();
            
        }
        
    }

    @Override
    public void run() {
     
        conexoesAdiquiridas = new ArrayList<>();
        
        contadorConexoes = 0;
        
        enderecoLocal = "";

        try{
            
            server = new ServerSocket(7300, 100);
            
//            System.err.println("Numero de conexões: " + contadorConexoes);
            
            while(true){
                
                try{
                    
                    waitForConnection();
                    
                    conectar();
                                                                
                }catch (IOException eo){
                    
                    closeConnection();
                    
                    System.err.println("Erro na conexão");
                }finally{
//                    closeConnection();
                    ++counter;
                }
                
            }
            
        }catch (IOException e){
            
            e.printStackTrace();
            
        }
        
    }
    
}
