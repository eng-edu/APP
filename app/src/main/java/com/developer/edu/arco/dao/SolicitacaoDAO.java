package com.developer.edu.arco.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.developer.edu.arco.model.Solicitacao;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoDAO {

    public boolean inserir(Context context, String id, String arco_id, String docente_id, String nome_arco) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("ARCO_ID", arco_id);
        contentValues.put("DOCENTE_ID", docente_id);
        contentValues.put("NOME_ARCO", nome_arco);


        boolean result = bd.insertWithOnConflict("SOLICITACAO", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE) > 0;
        bd.close();
        return result;
    }


    public List<Solicitacao> buscarTodos(Context context) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();

        List<Solicitacao> solicitacoes = new ArrayList<>();
        String colunas[] = new String[]{"ID", "ARCO_ID", "DOCENTE_ID", "NOME_ARCO"};
        Cursor cursor = bd.query("SOLICITACAO", colunas, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                Solicitacao solicitacao = new Solicitacao();

                solicitacao.setID(cursor.getString(0));
                solicitacao.setARCO_ID(cursor.getString(1));
                solicitacao.setDOCENTE_ID(cursor.getString(2));
                solicitacao.setNOME_ARCO(cursor.getString(3));

                solicitacoes.add(solicitacao);

            } while (cursor.moveToNext());

        }
        return solicitacoes;

    }


    public void deletAll(Context context) {

        SQLiteDatabase bd = ConectarBanco.getConnection(context).getWritableDatabase();
        bd.execSQL("DELETE FROM SOLICITACAO");
        bd.close();

    }
}
