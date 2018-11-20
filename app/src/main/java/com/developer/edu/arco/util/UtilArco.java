package com.developer.edu.arco.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.developer.edu.arco.model.Arquivo;
import com.developer.edu.arco.view.ActArquivo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import static com.developer.edu.arco.view.ActArquivo.getArquivo;


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


    public static String toPathFileBase64() throws Exception {


        final String[] resultr = {""};
        File file = new File(getArquivo().getCAMINHO());
        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        final byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        reader.close();


        String base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
        resultr[0] = base64.toString();

        getArquivo().setBASE64(resultr[0]);
        //aqui já faz a requisição e manda pro servirdor

        Log.i("DEVEDU", getArquivo().getBASE64());

        return resultr[0];

    }

}
