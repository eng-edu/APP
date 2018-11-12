package com.developer.edu.arco.model;

public class Solicitacao {

    String ID;
    String ARCO_ID;
    String DOCENTE_ID;
    String NOME_ARCO;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getARCO_ID() {
        return ARCO_ID;
    }

    public void setARCO_ID(String ARCO_ID) {
        this.ARCO_ID = ARCO_ID;
    }

    public String getDOCENTE_ID() {
        return DOCENTE_ID;
    }

    public void setDOCENTE_ID(String DOCENTE_ID) {
        this.DOCENTE_ID = DOCENTE_ID;
    }

    public String getNOME_ARCO() {
        return NOME_ARCO;
    }

    public void setNOME_ARCO(String NOME_ARCO) {
        this.NOME_ARCO = NOME_ARCO;
    }

    @Override
    public String toString() {
        return "NOME ARCO: " + NOME_ARCO;
    }
}
