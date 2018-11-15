package com.developer.edu.arco.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.edu.arco.model.Arco;
import com.developer.edu.arco.model.Etapa;

import java.util.ArrayList;
import java.util.List;

public class EtapaDAO {

    //*
    //     String ID;
    //    String NOME;
    //    String RESUMO;
    //    String STATUS;
    //    String ARCO_ID;*


    public boolean inserir(Context context, String id, String nome, String resumo, String status, String arco_id) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("NOME", nome);
        contentValues.put("RESUMO", resumo);
        contentValues.put("STATUS", status);
        contentValues.put("ARCO_ID", arco_id);

        boolean result = bd.insertWithOnConflict("ETAPA", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE) > 0;
        bd.close();
        return result;
    }


    public Etapa buscarEtapa(Context context, String nome) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();

        String colunas[] = new String[]{"ID", "NOME", "RESUMO", "STATUS", "ARCO_ID"};
        Cursor cursor = bd.query("ETAPA", colunas, "NOME = ?", new String[]{nome}, null, null, null);

        Etapa modelEtapa = new Etapa();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                modelEtapa.setID(cursor.getString(0));
                modelEtapa.setNOME(cursor.getString(1));
                modelEtapa.setRESUMO(cursor.getString(2));
                modelEtapa.setSTATUS(cursor.getString(3));
                modelEtapa.setARCO_ID(cursor.getString(4));

            } while (cursor.moveToNext());

        }

        return modelEtapa;

    }


    public void deletAll(Context context) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();
        bd.execSQL("DELETE FROM ETAPA");
        bd.close();

    }
}
