package com.developer.edu.arco.util;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class UtilArco {

    public static boolean verificarPermissao(Context context, String ID_CRIADOR, String ID, String acesso) {

        boolean result = false;

        if (ID_CRIADOR != null && ID_CRIADOR.equalsIgnoreCase(ID) && verificarAcessoRestrito(context, acesso)) {
            result = true;
        } else {
            Toast.makeText(context, "Você não tem permissão para executar esta ação", Toast.LENGTH_SHORT).show();

        }

        return result;
    }

    public static boolean verificarAcessoRestrito(Context context, String acesso) {

        boolean result = false;

        if (acesso != null && acesso.equalsIgnoreCase("N")) {
            result = true;
        } else {
            Toast.makeText(context, "Você não tem permissão para executar esta ação", Toast.LENGTH_SHORT).show();
        }

        return result;
    }


    public static String toPathFileBase64(Context context, String path) throws Exception {

        final String[] resultr = {""};
        File file = new File(path);
        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        final byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        reader.close();

        String base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
        resultr[0] = base64.toString();

        Log.i("DEVEDU", resultr[0]);

        return resultr[0];

    }


}
