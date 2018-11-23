package com.developer.edu.arco.model;

public  class Docente {
    String ID;
    String NOME;
    String FORMACAO;
    String EMAIL;
    String SENHA;
    String FOTO;
    public String getFOTO() {
        return FOTO;
    }

    public void setFOTO(String FOTO) {
        this.FOTO = FOTO;
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

    public String getFORMACAO() {
        return FORMACAO;
    }

    public void setFORMACAO(String FORMACAO) {
        this.FORMACAO = FORMACAO;
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
        return "Nome: "+this.NOME +"/ Formação: " + this.FORMACAO;
    }
}
