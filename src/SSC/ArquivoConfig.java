package SSC;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roger
 */
public class ArquivoConfig {

    private final String LOCALIZACAO_ARQUIVO = Constantes.PASTA_RAIZ + Constantes.BARRA_INVERTIDA + "TCC.config";
    private List<String> texto;
    private final Charset ENCODING = StandardCharsets.UTF_8;
    private TelaInicial telaInicial;
    
    public void setTelaInicial(TelaInicial ti){
        
        telaInicial = ti;
        
    }
       
    public void iniciarPrograma(){
        
        File arquivoConfig = new File(LOCALIZACAO_ARQUIVO);
        
        texto = new ArrayList<String>();
        
        if (!arquivoConfig.exists()) {
            
            telaInicial.adicionaLog("Criou arquivo de configuração.");
            
            try {
                //let's try to create it
                arquivoConfig.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ArquivoConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            criarArquivo();
            
        }else{
            
            //System.out.println("SSC.ArquivoConfig.<init>() arquivo config ja existe");
            
        }
        
    }
    
    
    public List<String> lerArquivo(){
        
            Path path = Paths.get(LOCALIZACAO_ARQUIVO);

            List<String> leitura = new ArrayList<String>();

            String auxiliar;

            try (Scanner scanner =  new Scanner(path, ENCODING.name())){

                while (scanner.hasNextLine()){

                    auxiliar = scanner.nextLine();

                    //process each line in some way
                    //System.err.println(auxiliar);

                    leitura.add(auxiliar);

                } 

            }catch (IOException ex) {

                    Logger.getLogger(ArquivoConfig.class.getName()).log(Level.SEVERE, null, ex);

            }
        
            return leitura;

        }
    
    public void adicionaOuMudaCadastro(String nome, String cpf){
        
        
        Path path = Paths.get(LOCALIZACAO_ARQUIVO);

            List<String> textoArquivo = new ArrayList<String>();

            String auxiliar;

            try (Scanner scanner =  new Scanner(path, ENCODING.name())){

                while (scanner.hasNextLine()){

                    auxiliar = scanner.nextLine();

                    //process each line in some way
                    //System.err.println(auxiliar);

                    textoArquivo.add(auxiliar);

                } 

            }catch (IOException ex) {

                    Logger.getLogger(ArquivoConfig.class.getName()).log(Level.SEVERE, null, ex);

            }
            
            for(int i = 0; i < textoArquivo.size(); i++){
                
                if(textoArquivo.get(i).contains("Nome:")){
                    
                    textoArquivo.set(i, "Nome:" + nome);
                    
                }else if(textoArquivo.get(i).contains("CPF:")){
                    
                    textoArquivo.set(i, "CPF:" + cpf);
                    
                }
                
            }
            
        try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)){
            
          for(String line : textoArquivo){
              
            writer.write(line);
            writer.newLine();
            
          }
          
        }catch (IOException ex) {
            Logger.getLogger(ArquivoConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void criarArquivo(){
        
        List<String> textoAuxiliar = new ArrayList<String>();
        
        textoAuxiliar.add("Nome:");
        textoAuxiliar.add("CPF:");
        
        Path path = Paths.get(LOCALIZACAO_ARQUIVO);
        
        try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)){
            
          for(String line : textoAuxiliar){
              
            writer.write(line);
            writer.newLine();
            
          }
          
        }catch (IOException ex) {
            Logger.getLogger(ArquivoConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
