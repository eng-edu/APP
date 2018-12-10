package com.developer.edu.arco.model;

public class Mensagem {

    String ID;
    String TEXTO;
    String ID_AUTOR;
    String NOME_AUTOR;
    String DATA;
    String ARCO_ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTEXTO() {
        return TEXTO;
    }

    public void setTEXTO(String TEXTO) {
        this.TEXTO = TEXTO;
    }

    public String getID_AUTOR() {
        return ID_AUTOR;
    }

    public void setID_AUTOR(String ID_AUTOR) {
        this.ID_AUTOR = ID_AUTOR;
    }

    public String getNOME_AUTOR() {
        return NOME_AUTOR;
    }

    public void setNOME_AUTOR(String NOME_AUTOR) {
        this.NOME_AUTOR = NOME_AUTOR;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getARCO_ID() {
        return ARCO_ID;
    }

    public void setARCO_ID(String ARCO_ID) {
        this.ARCO_ID = ARCO_ID;
    }

    @Override
    public String toString() {
        return "Mensagem{" +
                "ID='" + ID + '\'' +
                ", TEXTO='" + TEXTO + '\'' +
                ", ID_AUTOR='" + ID_AUTOR + '\'' +
                ", NOME_AUTOR='" + NOME_AUTOR + '\'' +
                ", DATA='" + DATA + '\'' +
                ", ARCO_ID='" + ARCO_ID + '\'' +
                '}';
    }
}
