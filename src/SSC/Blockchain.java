package SSC;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roger
 */
public class Blockchain {
    
    private final Charset ENCODING = StandardCharsets.UTF_8;
    private TelaInicial telaInicial;
    
    public void setTelaInicial(TelaInicial ti){
        
        telaInicial = ti;
        
    }
    
    public void verificaECria(List<String> listaBlockChain){
        
        for(int i = 0; i < listaBlockChain.size(); i++){
            
            String enderecoBlockchain = Constantes.PASTA_RAIZ + Constantes.BARRA_INVERTIDA + listaBlockChain.get(i) + ".blockchain";
            
            File arquivoConfig = new File(enderecoBlockchain);
        
            if (!arquivoConfig.exists()) {

                //System.out.println("SSC.Blockchain.verificaECria() nao existia " + enderecoBlockchain);

                criarArquivo(enderecoBlockchain);

            }else{

                //System.out.println("SSC.Blockchain.verificaECria() ja existia " + enderecoBlockchain);

            }
            
            
        }
        
    }
    
    public void criaNovoParaGrupo(String nomegrupo){
        
            String enderecoBlockchain = Constantes.PASTA_RAIZ + Constantes.BARRA_INVERTIDA + nomegrupo + ".blockchain";
            
            File arquivoConfig = new File(enderecoBlockchain);
        
            if (!arquivoConfig.exists()) {

                //System.out.println("SSC.Blockchain.verificaECria() nao existia " + enderecoBlockchain);

                criarArquivo(enderecoBlockchain);

            }else{

                //System.out.println("SSC.Blockchain.verificaECria() ja existia " + enderecoBlockchain);

            }
            
            
        
        
    }
    
    public void criarArquivo(String endereco){
        
        File arquivoConfig = new File(endereco);
        
        try {
            //let's try to create it
            arquivoConfig.createNewFile();
            telaInicial.adicionaLog("Criou novo BlockChain: " + endereco);
        } catch (IOException ex) {
            Logger.getLogger(ArquivoConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void adicionaBloco(String informacoes){
            
        String nomeProprietario = informacoes.substring(0, informacoes.indexOf(";"));
        
        informacoes = informacoes.substring(informacoes.indexOf(";") + 1);
        
        String cpfProprietario = informacoes.substring(0, informacoes.indexOf(";"));
        
        informacoes = informacoes.substring(informacoes.indexOf(";") + 1);
        
        String enderecoArquivo = informacoes.substring(0, informacoes.indexOf(";"));
        
        informacoes = informacoes.substring(informacoes.indexOf(";") + 1);
        
        String nomeRequisitante = informacoes.substring(0, informacoes.indexOf(";"));
        
        informacoes = informacoes.substring(informacoes.indexOf(";") + 1);
        
        String cpfRequisitante = informacoes.substring(0, informacoes.indexOf(";"));
          
        informacoes = informacoes.substring(informacoes.indexOf(";") + 1);
        
        String data = informacoes.substring(0, informacoes.indexOf(";"));
          
        informacoes = informacoes.substring(informacoes.indexOf(";") + 1);
        
        String hora = informacoes.substring(0, informacoes.indexOf(";"));
        
        String enderecoAuxiliar = enderecoArquivo.substring(Constantes.PASTA_RAIZ.length() + Constantes.BARRA_INVERTIDA.length());
        
        enderecoAuxiliar = enderecoAuxiliar.substring(0, enderecoAuxiliar.indexOf(Constantes.BARRA_INVERTIDA));
        
        String enderecoBlockchain = Constantes.PASTA_RAIZ + Constantes.BARRA_INVERTIDA + enderecoAuxiliar + ".blockchain";

            Path path = Paths.get(enderecoBlockchain);

            List<String> textoArquivo = new ArrayList<String>();

            try (Scanner scanner =  new Scanner(path, ENCODING.name())){

                while (scanner.hasNextLine()){

                    textoArquivo.add(scanner.nextLine());

                } 

            }catch (IOException ex) {

                    Logger.getLogger(ArquivoConfig.class.getName()).log(Level.SEVERE, null, ex);

            }
            
           textoArquivo.add("Nome; CPF (Hospedeiro do arquivo): " + nomeProprietario.substring("Nome:".length()) + "; " + cpfProprietario.substring("CPF:".length()));
           textoArquivo.add("Arquivo baixado: " + enderecoArquivo.substring(Constantes.PASTA_RAIZ.length()));
           textoArquivo.add("Nome; CPF (Requisitante do arquivo): " + nomeRequisitante.substring("Nome:".length()) + "; " + cpfRequisitante.substring("CPF:".length()));
           textoArquivo.add("Data; hora(Download): " + data + "; " + hora);
           textoArquivo.add("--------------------------------------------------------------------------------");
           
            
        try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)){
            
          for(String line : textoArquivo){
              
            writer.write(line);
            writer.newLine();
            
          }
          
        }catch (IOException ex) {
            Logger.getLogger(ArquivoConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public List<String> lerArquivo(String nomeTab){
        
            Path path = Paths.get(nomeTab + ".blockchain");

            List<String> leitura = new ArrayList<String>();

            String auxiliar;

            try (Scanner scanner =  new Scanner(path, ENCODING.name())){

                while (scanner.hasNextLine()){

                    auxiliar = scanner.nextLine();

//                    //process each line in some way
//                    System.err.println(auxiliar);

                    leitura.add(auxiliar);

                } 

            }catch (IOException ex) {

                    Logger.getLogger(ArquivoConfig.class.getName()).log(Level.SEVERE, null, ex);

            }
        
            return leitura;

        }
    
    
}
