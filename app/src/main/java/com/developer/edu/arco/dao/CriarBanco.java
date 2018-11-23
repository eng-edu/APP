package com.developer.edu.arco.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriarBanco extends SQLiteOpenHelper {




    private final String CREATE_TABLE_DOCENTE = "CREATE TABLE DOCENTE (ID TEXT PRIMARY KEY, NOME TEXT NOT NULL, FORMACAO TEXT NOT NULL, EMAIL TEXT NOT NULL, SENHA TEXT NOT NULL, FOTO NOT NULL);";
    private final String CREATE_TABLE_DISCENTE = "CREATE TABLE DISCENTE (ID TEXT PRIMARY KEY, NOME TEXT NOT NULL, INSTITUICAO TEXT NOT NULL, EMAIL TEXT NOT NULL, SENHA TEXT NOT NULL, FOTO NOT NULL);";
    private final String CREATE_TABLE_ARCO = "CREATE TABLE ARCO (ID TEXT PRIMARY KEY, NOME TEXT NOT NULL, STATUS TEXT NOT NULL, ID_CRIADOR TEXT NOT NULL, DOCENTE_ID TEXT NOT NULL, COMPARTILHADO TEXT NOT NULL);";
    private final String CREATE_TABLE_ETAPA = "CREATE TABLE ETAPA (ID TEXT PRIMARY KEY, NOME TEXT NOT NULL, RESUMO TEXT NOT NULL, STATUS TEXT NOT NULL, ARCO_ID TEXT NOT NULL);";
    private final String CREATE_TABLE_SOLICITACAO = "CREATE TABLE SOLICITACAO (ID TEXT PRIMARY KEY, ARCO_ID TEXT NOT NULL, DOCENTE_ID TEXT NOT NULL, NOME_ARCO TEXT NOT NULL);";
    private final String CREATE_TABLE_ARQUIVO = "CREATE TABLE ARQUIVO (ID TEXT PRIMARY KEY, ETAPA_ID TEXT NOT NULL, ARCO_ID TEXT NOT NULL, NOME TEXT NOT NULL, CAMINHO TEXT NOT NULL);";


    private final String DROP_TABLE_DOCENTE = "DROP TABLE DOCENTE";
    private final String DROP_TABLE_DISCENTE = "DROP TABLE DISCENTE";
    private final String DROP_TABLE_ARCO = "DROP TABLE ARCO";
    private final String DROP_TABLE_ETAPA = "DROP TABLE ETAPA";
    private final String DROP_TABLE_SOLICITACAO = "DROP TABLE SOLICITACAO";
    private final String DROP_TABLE_ARQUIVO = "DROP TABLE ARQUIVO";



    public CriarBanco(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DOCENTE);
        db.execSQL(CREATE_TABLE_DISCENTE);
        db.execSQL(CREATE_TABLE_ARCO);
        db.execSQL(CREATE_TABLE_ETAPA);
        db.execSQL(CREATE_TABLE_SOLICITACAO);
        db.execSQL(CREATE_TABLE_ARQUIVO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_DOCENTE);
        db.execSQL(DROP_TABLE_DISCENTE);
        db.execSQL(DROP_TABLE_ARCO);
        db.execSQL(DROP_TABLE_ETAPA);
        db.execSQL(DROP_TABLE_SOLICITACAO);
        db.execSQL(DROP_TABLE_ARQUIVO);
    }
}
