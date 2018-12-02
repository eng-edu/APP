package com.developer.edu.arco.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.edu.arco.model.Arquivo;

import java.util.ArrayList;
import java.util.List;

public class ArquivoDAO {

    public boolean inserir(Context context, String ID, String ETAPA_ID, String ARCO_ID, String NOME, String CAMINHO) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", ID);
        contentValues.put("ETAPA_ID", ETAPA_ID);
        contentValues.put("ARCO_ID", ARCO_ID);
        contentValues.put("NOME", NOME);
        contentValues.put("CAMINHO", CAMINHO);

        boolean result = bd.insertWithOnConflict("ARQUIVO", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE) > 0;
        bd.close();
        return result;
    }

    public List<Arquivo> buscarTodos(Context context) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();

        String colunas[] = new String[]{"ID", "ETAPA_ID", "ARCO_ID", "NOME", "CAMINHO"};
        Cursor cursor = bd.query("ARQUIVO", colunas, null, null, null, null, null);

        List<Arquivo> arquivos = new ArrayList<>();


        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                Arquivo arquivo = new Arquivo();
                arquivo.setID(cursor.getString(0));
                arquivo.setETAPA_ID(cursor.getString(1));
                arquivo.setARCO_ID(cursor.getString(2));
                arquivo.setNOME(cursor.getString(3));
                arquivo.setCAMINHO(cursor.getString(4));

                arquivos.add(arquivo);

            } while (cursor.moveToNext());

        }

        return arquivos;

    }

    public void deletAll(Context context) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();
        bd.execSQL("DELETE FROM ARQUIVO");
        bd.close();

    }
}
