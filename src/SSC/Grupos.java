/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SSC;

import java.awt.List;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roger
 */
public class Grupos {
    
    
    private List diretorioGrupo = new List();
    private List diretorioGrupoRaiz = new List();
    private boolean criarNovosGrupos = false;

    public Grupos() {
        
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
        /**
         * aqui adiquire os diretorios dentro da pasta tcc
         * e exibe eles como tabs
         */
        if(!lerArquivos.getDiretoriosTcc().isEmpty()){
            telaInicial.tabsGrupoInicial(lerArquivos.getDiretoriosTcc());
        }
        
        telaInicial.setVisible(true);
        
    }

}
