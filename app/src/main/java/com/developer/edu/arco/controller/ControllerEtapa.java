package com.developer.edu.arco.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.arco.dao.DocenteDAO;
import com.developer.edu.arco.model.Docente;
import com.developer.edu.arco.view.ActArco;
import com.developer.edu.arco.view.ActNovoArco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerEtapa {

    public void submeterEtapa(final Context context, String id, String resumo) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(false);
        dialog.show();


        Call<String> stringCall = ConfigRetrofit.getService().submeter(result, id, resumo);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == 200) {

                    //   Intent intent = new Intent(context, ActArco.class);
                    Toast.makeText(context, "Submetido com sucesso!", Toast.LENGTH_LONG).show();
                    ((Activity) context).finish();
                    dialog.dismiss();


                } else if (response.code() == 405) {
                    Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

    }

    public void aprovarEtapa(final Context context, String id, String s, String arco_id) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(false);
        dialog.show();


        Call<String> stringCall = ConfigRetrofit.getService().aprovar(result, id, s, arco_id);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(context, "Aprovado com sucesso!", Toast.LENGTH_LONG).show();

                    //  Intent mudarParaMain = new Intent(context, ActArco.class);
                    //  context.startActivity(mudarParaMain);
                    ((Activity) context).finish();
                    dialog.dismiss();


                } else if (response.code() == 405) {
                    Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

    }
}
