package com.developer.edu.arco.model;

public  class Discente {

    String ID;
    String NOME;
    String INSTITUICAO;
    String EMAIL;
    String SENHA;
    boolean CHEKED;

    public boolean isCHEKED() {
        return CHEKED;
    }

    public void setCHEKED(boolean CHEKED) {
        this.CHEKED = CHEKED;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public String getINSTITUICAO() {
        return INSTITUICAO;
    }

    public void setINSTITUICAO(String INSTITUICAO) {
        this.INSTITUICAO = INSTITUICAO;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getSENHA() {
        return SENHA;
    }

    public void setSENHA(String SENHA) {
        this.SENHA = SENHA;
    }


    @Override
    public String toString() {
        return "Nome: "+this.NOME +"/ Instituição: " + this.INSTITUICAO;
    }
}
