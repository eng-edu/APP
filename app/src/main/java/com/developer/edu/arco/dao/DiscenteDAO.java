package com.developer.edu.arco.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.edu.arco.model.Discente;
import com.developer.edu.arco.model.Docente;

import java.util.ArrayList;
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


    public List<Discente> buscarTodos(Context context) {
        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();

        List<Discente> discentes = new ArrayList<>();
        String colunas[] = new String[]{"ID", "NOME", "INSTITUICAO", "EMAIL", "SENHA"};
        Cursor cursor = bd.query("DISCENTE", colunas, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                Discente discente = new Discente();
                discente.setID(cursor.getString(0));
                discente.setNOME(cursor.getString(1));
                discente.setINSTITUICAO(cursor.getString(2));
                discente.setEMAIL(cursor.getString(3));
                discente.setSENHA(cursor.getString(4));

                discentes.add(discente);

            } while (cursor.moveToNext());

        }
        return discentes;

    }

    public void deletAll(Context context) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();
        bd.execSQL("DELETE FROM DISCENTE");
        bd.close();

    }


}
