package com.developer.edu.arco.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class DiscenteDAO {


    public boolean inserir(Context context, String id, String nome, String formacao, String email, String senha) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("NOME", nome);
        contentValues.put("INSTITUICAO", formacao);
        contentValues.put("EMAIL", email);
        contentValues.put("SENHA", senha);


        boolean result = bd.insertWithOnConflict("DISCENTE", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE) > 0;
        bd.close();
        return result;
    }

    public void deletAll(Context context){

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();
        bd.execSQL("DELETE FROM DISCENTE");
        bd.close();

    }


}
