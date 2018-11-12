package com.developer.edu.arco.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.arco.dao.DiscenteDAO;
import com.developer.edu.arco.dao.DocenteDAO;
import com.developer.edu.arco.view.Login;
import com.developer.edu.arco.view.MenuPrincipal;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.EUICC_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class ControllerLogin {

    Call<String> stringCall;


    public void logar(final Context context, String email, String senha, final boolean discente, final boolean docente) {



        if (email.length() > 0 && senha.length() > 0) {

            if (docente) {
                stringCall = ConfigRetrofit.getService().logarDocente(email, senha);
                final SharedPreferences.Editor editor = context.getSharedPreferences(String.valueOf(R.string.preference_config), MODE_PRIVATE).edit();
                editor.putString(String.valueOf(R.string.tipo_login), "docente");
                editor.apply();

            }

            if (discente) {
                stringCall = ConfigRetrofit.getService().logarDiscente(email, senha);
                final SharedPreferences.Editor editor = context.getSharedPreferences(String.valueOf(R.string.preference_config), MODE_PRIVATE).edit();
                editor.putString(String.valueOf(R.string.tipo_login), "discente");
                editor.apply();
            }

            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setTitle("Aguarde...");
            dialog.show();


            stringCall.enqueue(new Callback<String>() {


                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {

                        try {

                            JSONObject object = new JSONObject(response.body());

                            if (docente) {

                                final SharedPreferences.Editor editor = context.getSharedPreferences(String.valueOf(R.string.preference_config), MODE_PRIVATE).edit();
                                editor.putString(String.valueOf(R.string.TOKENAPI), object.getString("TOKENAPI"));
                                editor.putString(String.valueOf(R.string.ID), object.getString("ID"));
                                editor.putString(String.valueOf(R.string.NOME), object.getString("NOME"));
                                editor.putString(String.valueOf(R.string.FORMACAO), object.getString("FORMACAO"));
                                editor.putString(String.valueOf(R.string.EMAIL), object.getString("EMAIL"));
                                editor.putString(String.valueOf(R.string.SENHA), object.getString("TOKENAPI"));
                                editor.apply();

                            } else if (discente) {

                                final SharedPreferences.Editor editor = context.getSharedPreferences(String.valueOf(R.string.preference_config), MODE_PRIVATE).edit();
                                editor.putString(String.valueOf(R.string.TOKENAPI), object.getString("TOKENAPI"));
                                editor.putString(String.valueOf(R.string.ID), object.getString("ID"));
                                editor.putString(String.valueOf(R.string.NOME), object.getString("NOME"));
                                editor.putString(String.valueOf(R.string.INSTITUICAO), object.getString("INSTITUICAO"));
                                editor.putString(String.valueOf(R.string.EMAIL), object.getString("EMAIL"));
                                editor.putString(String.valueOf(R.string.SENHA), object.getString("TOKENAPI"));
                                editor.apply();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                        Toast.makeText(context, "Login realizado com sucesso!", Toast.LENGTH_LONG).show();

                        Intent mudarParaMain = new Intent(context, MenuPrincipal.class);
                        context.startActivity(mudarParaMain);
                        ((Activity) context).finish();


                    } else if (response.code() == 203) {
                        dialog.dismiss();
                        Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


        } else {
            Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
        }


    }

    public void logout(Context context) {
        Toast.makeText(context, "Saindo...", Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = context.getSharedPreferences(String.valueOf(R.string.preference_config), MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();

        Intent mudarParaMain = new Intent(context, Login.class);
        context.startActivity(mudarParaMain);
        ((Activity) context).finish();

    }
}