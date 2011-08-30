/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

/**
 *
 * @author luizedu
 */
public enum Codigo {
        INPP("INPP"), AMEM("AMEM"), CRVL("CRVL"), CRCT("CRCT"), CMIG("CMIG"), 
        CMDG("CMDG"), CMMA("CMMA"), CMME("CMME"), CMAG("CMAG"), CMEG("CMEG"), 
        SOMA("SOMA"), SUBT("SUBT"), MULT("MULT"), DIVI("DIVI"), INVR("INVR"), 
        DISJ("DISJ"), CONJ("CONJ"), NEGA("NEGA"), DSVS("DSVS"), DSVF("DSVF"), 
        NADA("NADA"), FIM("FIM"),  PARA("PARA"), ARMZ("ARMZ"), IMPR("IMPR"), 
        LEIT("LEIT"), CREN("CREN"), CRVI("CRVI"), ARMI("ARMI"), ENPR("ENPR"), 
        RTPR("RTPR"), CHPR("CHPR"), DMEM("DMEM");
        
        private String codigo;
        
        Codigo (String codigo) {
            this.codigo = codigo;
        }
}