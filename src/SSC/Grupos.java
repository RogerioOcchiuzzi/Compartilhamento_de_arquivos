/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SSC;

import java.awt.List;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author roger
 */
public class Grupos implements Runnable{
    
    
    private List diretorioGrupo = new List();
    private List diretorioGrupoRaiz = new List();
    private boolean criarNovosGrupos = false;
    private java.util.List<Cliente> listaCliente  = new ArrayList<>(); 
        
    @Override
    public void run() {
        
        LerArquivos lerArquivos = new LerArquivos();
        lerArquivos.setDiretorioGrupo(diretorioGrupo);
        lerArquivos.setDiretorioGrupoRaiz(diretorioGrupoRaiz);
        lerArquivos.setCriarNovosGrupos(criarNovosGrupos);
        
        lerArquivos.criarDiretorioTcc();        
        
        try {
            lerArquivos.LeOsArquivosPastaTcc();
        } catch (IOException ex) {
            Logger.getLogger(Tcc.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        TelaInicial telaInicial = new TelaInicial();
        telaInicial.setDiretorioGrupo(diretorioGrupo);
        telaInicial.setDiretorioGrupoRaiz(diretorioGrupoRaiz);
        telaInicial.setCriarNovosGrupos(criarNovosGrupos);
        telaInicial.setListaClientes(listaCliente);
        /**
         * aqui adiquire os diretorios dentro da pasta tcc
         * e exibe eles como tabs
         */
        if(!lerArquivos.getDiretoriosTcc().isEmpty()){
            telaInicial.tabsGrupoInicial(lerArquivos.getDiretoriosTcc());
        }
               
        
        
        Servidor servidor = new Servidor();
        servidor.setListaClientes(listaCliente);
        servidor.setDiretorioGrupoRaiz(diretorioGrupoRaiz);
        servidor.setTelaInicial(telaInicial);
        
        Cliente cliente = new Cliente();
        cliente.setListaClientes(listaCliente);
        cliente.setDiretorioGrupoRaiz(diretorioGrupoRaiz);
        cliente.setTelaInicial(telaInicial);
        
        telaInicial.setVisible(true);

       Thread threadServidor = new Thread(servidor);
       Thread threadcliente = new Thread(cliente);
       
       threadServidor.start();
       threadcliente.start();
               
    }

}