package com.developer.edu.arco.model;

public class Arquivo {
    String ID;
    String ETAPA_ID;
    String ARCO_ID;
    String NOME;
    String CAMINHO;
    String BASE64;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getETAPA_ID() {
        return ETAPA_ID;
    }

    public void setETAPA_ID(String ETAPA_ID) {
        this.ETAPA_ID = ETAPA_ID;
    }

    public String getARCO_ID() {
        return ARCO_ID;
    }

    public void setARCO_ID(String ARCO_ID) {
        this.ARCO_ID = ARCO_ID;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public String getCAMINHO() {
        return CAMINHO;
    }

    public void setCAMINHO(String CAMINHO) {
        this.CAMINHO = CAMINHO;
    }

    public String getBASE64() {
        return BASE64;
    }

    public void setBASE64(String BASE64) {
        this.BASE64 = BASE64;
    }

    @Override
    public String toString() {
        return NOME;
    }
}
