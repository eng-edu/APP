package com.developer.edu.arco.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.edu.arco.model.Docente;

import java.util.ArrayList;
import java.util.List;

public class DocenteDAO {


    public boolean inserir(Context context, String id, String nome, String formacao, String email, String senha) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("NOME", nome);
        contentValues.put("FORMACAO", formacao);
        contentValues.put("EMAIL", email);
        contentValues.put("SENHA", senha);

        boolean result = bd.insertWithOnConflict("DOCENTE", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE) > 0;
        bd.close();
        return result;
    }


    public List<Docente> buscarTodos(Context context) {
        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();

        List<Docente> docentes = new ArrayList<>();
        String colunas[] = new String[]{"ID", "NOME", "FORMACAO", "EMAIL", "SENHA"};
        Cursor cursor = bd.query("DOCENTE", colunas, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                Docente docente = new Docente();
                docente.setID(cursor.getString(0));
                docente.setNOME(cursor.getString(1));
                docente.setFORMACAO(cursor.getString(2));
                docente.setEMAIL(cursor.getString(3));
                docente.setSENHA(cursor.getString(4));

                docentes.add(docente);

            } while (cursor.moveToNext());

        }
        return docentes;

    }




    public void deletAll(Context context) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();
        bd.execSQL("DELETE FROM DOCENTE");
        bd.close();

    }


}
