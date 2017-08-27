/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SSC;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roger
 */
public class LerArquivos {
    
    private String diretorio = "C:\\";
    //private boolean temTcc = false;
    private ArrayList diretoriosTcc = new ArrayList();
    
    private List diretorioGrupo = new List();
    private List diretorioGrupoRaiz = new List();
    private boolean criarNovosGrupos = false;

    public ArrayList getDiretoriosTcc() {
        return diretoriosTcc;
    }

    public void setDiretorioGrupoRaiz(List diretorioGrupoRaiz) {
        this.diretorioGrupoRaiz = diretorioGrupoRaiz;
    }

    public void setCriarNovosGrupos(boolean criarNovosGrupos) {
        this.criarNovosGrupos = criarNovosGrupos;
    }
    
 
    public List getDiretorioGrupo() {
        return diretorioGrupo;
    }

    public void setDiretorioGrupo(List diretorioGrupo) {
        this.diretorioGrupo = diretorioGrupo;
    }

/**
 * 
 */
    public void LeOsArquivos() throws IOException {

	File file = new File(diretorio);
	File afile[] = file.listFiles();
	int i = 0;
	for (int j = afile.length; i < j; i++) {
		File arquivos = afile[i];
//		System.out.println(arquivos.getName() + " -- Diretorio: " + 
//                        arquivos.isDirectory() + " -- Arquivo: " + arquivos.isFile() +
//                        " -- oculto: " + arquivos.isHidden());
	}

    }
    
    public void LeOsArquivosPastaTcc() throws IOException {       
    
            File file = new File(Constantes.PASTA_RAIZ);
            
            File afile[] = file.listFiles();
            int i = 0;
            for (int j = afile.length; i < j; i++) {
                    File arquivos = afile[i];
//                    System.out.println(arquivos.getName() + " -- Diretorio: " + 
//                            arquivos.isDirectory() + " -- Arquivo: " + arquivos.isFile() +
//                            " -- oculto: " + arquivos.isHidden());
                    if(arquivos.isDirectory()){
                        
                        diretoriosTcc.add(arquivos.getName());
                        diretorioGrupo.add(Constantes.PASTA_RAIZ + Constantes.BARRA_INVERTIDA + arquivos.getName());
                        diretorioGrupoRaiz.add(Constantes.PASTA_RAIZ + Constantes.BARRA_INVERTIDA + arquivos.getName());
                        
                    }

                    
                                     
            }
            

    }
    
    /**
     *
     * @param diretorio
     * @return
     * @throws IOException
     */
    public String[] LeOsArquivosPastas(String diretorioAtual) throws IOException {         
            
            File file = new File(Constantes.PASTA_RAIZ + Constantes.BARRA_INVERTIDA + diretorioAtual);
                   
            return file.list();
    }
    
 public void criarDiretorioTcc() {     

        File file = new File(Constantes.PASTA_RAIZ);
        
        if (!file.exists()) {
            
            //System.out.println(Constantes.PASTA_RAIZ + " não existia");
           
                //let's try to create it
                file.mkdirs();
            
        }else{
            
            
            //System.out.println(Constantes.PASTA_RAIZ + " ja existe");
            
        }
        
    }
 
 /**
  * esse metodo cria os diretorios dos novos grupos
  */
 public void criaGrupo(String nomeGrupo){
     
            try {
            File diretorio = new File(Constantes.PASTA_RAIZ + Constantes.BARRA_INVERTIDA + nomeGrupo);
            diretorio.mkdirs();
            } catch (Exception ex) {
//                System.out.println(ex);
//                System.out.println("Não foi possivel criar o diretorio teste");
            }
            
//            System.out.println("não tem este grupo");
      
 }
 
 // Deleta todos os arquivos e subdiretorios
    // Retorna verdadeiro se todas as remoções aconteceram com sucesso.
    // Se houve falha, o método será interrompido, e retornará falso.
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) { 
               boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
//                    System.out.println("não deletou");
                    return false;
                }
            }
        }
//        System.out.println("deletou");
        // Agora o diretório está vazio, restando apenas deletá-lo.
        return dir.delete();
    }
 
 public void deletarGrupo(String nomeGrupo){
     
     File diretorio = new File(Constantes.PASTA_RAIZ + Constantes.BARRA_INVERTIDA + nomeGrupo); 
     
     deleteDir(diretorio);
     
 }

}
    

