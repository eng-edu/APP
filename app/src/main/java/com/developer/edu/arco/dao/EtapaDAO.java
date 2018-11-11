package com.developer.edu.arco.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.edu.arco.model.Arco;

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


    public List<Arco> buscarTodos(Context context) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();

        List<Arco> arcos = new ArrayList<>();
        String colunas[] = new String[]{"ID", "NOME", "RESUMO", "STATUS", "ARCO_ID"};
        Cursor cursor = bd.query("ETAPA", colunas, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Arco arco = new Arco();
                arco.setID(cursor.getString(0));
                arco.setNOME(cursor.getString(1));
                arco.setSTATUS(cursor.getString(2));
                arco.setID_CRIADOR(cursor.getString(3));
                arco.setDOCENTE_ID(cursor.getString(4));
                arco.setCOMPARTILHADO(cursor.getString(5));

                arcos.add(arco);

            } while (cursor.moveToNext());

        }
        return arcos;

    }

    public void deletAll(Context context) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();
        bd.execSQL("DELETE FROM ETAPA");
        bd.close();

    }
}
