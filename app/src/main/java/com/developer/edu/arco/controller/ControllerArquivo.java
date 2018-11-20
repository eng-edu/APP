package com.developer.edu.arco.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.arco.model.Arquivo;
import com.developer.edu.arco.model.Discente;
import com.developer.edu.arco.model.Docente;
import com.developer.edu.arco.util.UtilArco;
import com.developer.edu.arco.view.ActNovoArco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerArquivo {

    public void novoArquivo(final Context context, Arquivo arquivo) throws Exception {


        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(false);
        dialog.show();

        JSONObject OBJarquivo = new JSONObject();
        OBJarquivo.put("NOME", arquivo.getNOME());
        OBJarquivo.put("ETAPA_ID", arquivo.getETAPA_ID());
        OBJarquivo.put("ETAPA_ARCO_ID", arquivo.getARCO_ID());
        OBJarquivo.put("BASE64", arquivo.getBASE64());


        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        Call<String> stringCall = ConfigRetrofit.getService().novoArquivoEtapa(result, OBJarquivo.toString());
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                    Toast.makeText(context, "Upload feito com sucesso!", Toast.LENGTH_LONG).show();
                }

                dialog.dismiss();

                //atualiza a lista de arquivos
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });


    }

}
