package com.developer.edu.arco.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.arco.util.SocketStatic;
import com.developer.edu.arco.view.ActArco;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerEtapa {

    public void submeterEtapa(final Context context, String id, String resumo, final String arco_id, final String nome, final String compartilhado, final String id_criador) {

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

                    dialog.dismiss();
                    SocketStatic.getSocket().emit("ATUALIZOU_ARCO", arco_id);
                    ((Activity) context).finish();

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

    public void aprovarEtapa(final Context context, String id, String s, final String arco_id, final String nome, final String compartilhado, final String id_criador) {

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

                    dialog.dismiss();
                    SocketStatic.getSocket().emit("ATUALIZOU_ARCO", arco_id);
                    ((Activity) context).finish();


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

    public void reprovarEtapa(final Context context, String id, final String arco_id, final String nome, final String compartilhado, final String id_criador) {
        //muda o status da etapa
        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(false);
        dialog.show();

        Call<String> stringCall = ConfigRetrofit.getService().reprovar(result, id);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    dialog.dismiss();
                    SocketStatic.getSocket().emit("ATUALIZOU_ARCO", arco_id);
                    ((Activity) context).finish();

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

    public void salvarEtapa(final Context context, String id, String resumo, final String arco_id, final String nome, final String compartilhado, final String id_criador) {
        //só da um update sem fazer nada com status

        //muda o status da etapa
        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(false);
        dialog.show();

        Call<String> stringCall = ConfigRetrofit.getService().salvar(result, id, resumo);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {


                    dialog.dismiss();
                    SocketStatic.getSocket().emit("ATUALIZOU_ARCO", arco_id);
                    ((Activity) context).finish();

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
