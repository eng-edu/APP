package com.developer.edu.arco.model;

public class Arco {

    String ID;
    String STATUS;
    String NOME;
    String ID_CRIADOR;
    String DOCENTE_ID;
    String COMPARTILHADO;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public String getID_CRIADOR() {
        return ID_CRIADOR;
    }

    public void setID_CRIADOR(String ID_CRIADOR) {
        this.ID_CRIADOR = ID_CRIADOR;
    }

    public String getDOCENTE_ID() {
        return DOCENTE_ID;
    }

    public void setDOCENTE_ID(String DOCENTE_ID) {
        this.DOCENTE_ID = DOCENTE_ID;
    }

    public String getCOMPARTILHADO() {
        return COMPARTILHADO;
    }

    public void setCOMPARTILHADO(String COMPARTILHADO) {
        this.COMPARTILHADO = COMPARTILHADO;
    }

    @Override
    public String toString() {
        return "NOME: " + this.NOME + "  STATUS: " + this.STATUS;
    }
}
