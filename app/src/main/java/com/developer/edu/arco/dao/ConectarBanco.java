package com.developer.edu.arco.dao;

import android.content.Context;

public class ConectarBanco {

    private static final String DATABASE_NAME = "arco4.db";
    private static final int DATABASE_VERSION = 4;

    static CriarBanco banco;

    public static CriarBanco getConnection(Context context){
        if(banco == null){
            banco = new CriarBanco(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        return banco;
    }

}
