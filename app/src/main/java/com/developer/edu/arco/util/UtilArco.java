package com.developer.edu.arco.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class UtilArco {

    public static boolean verificarPermissao( Context context, String ID_CRIADOR, String ID, String acesso) {

        boolean result = false;

        if (ID_CRIADOR!= null && ID_CRIADOR.equalsIgnoreCase(ID) && verificarAcessoRestrito(context, acesso)) {
            result = true;
        } else {
            Toast.makeText(context, "Você não tem permissão para executar esta ação", Toast.LENGTH_SHORT).show();

        }

        return result;
    }

    public static boolean verificarAcessoRestrito( Context context, String acesso) {

        boolean result = false;

        if (acesso!= null && acesso.equalsIgnoreCase("N")) {
            result = true;
        } else {
            Toast.makeText(context, "Você não tem permissão para executar esta ação", Toast.LENGTH_SHORT).show();
        }

        return result;
    }


}
