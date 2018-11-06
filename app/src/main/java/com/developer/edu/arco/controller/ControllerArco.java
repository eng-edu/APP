package com.developer.edu.arco.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.arco.dao.DiscenteDAO;
import com.developer.edu.arco.dao.DocenteDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerArco {

    public void buscarTodosdocentes(final Context context) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.show();


        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        Call<String> stringCall = ConfigRetrofit.getService().buscarTodosDocentes(result);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.isSuccessful()) {

                    try {

                        JSONArray array = new JSONArray(response.body());

                        int sizeArray = array.length();

                        for (int i = 0; i > sizeArray; i++) {
                            JSONObject object = array.getJSONObject(i);
                            new DocenteDAO().inserir(context,
                                    object.getString("ID"),
                                    object.getString("NOME"),
                                    object.getString("FORMACAO"),
                                    object.getString("EMAIL"),
                                    object.getString("SENHA"));

                        }

                        dialog.dismiss();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

    public void buscarTodosDiscentes(final Context context) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.show();


        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        Call<String> stringCall = ConfigRetrofit.getService().buscarTodosDiscentes(result);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.isSuccessful()) {

                    try {

                        JSONArray array = new JSONArray(response.body());

                        int sizeArray = array.length();

                        for (int i = 0; i > sizeArray; i++) {
                            JSONObject object = array.getJSONObject(i);
                            new DiscenteDAO().inserir(context,
                                    object.getString("ID"),
                                    object.getString("NOME"),
                                    object.getString("INSTITUICAO"),
                                    object.getString("EMAIL"),
                                    object.getString("SENHA"));
                        }

                        dialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();

                        dialog.dismiss();

                    }
                } else {
                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();

                    dialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

    public void criarNovoarvo(String titulo, String nomeGrupo, Docente docente, List<Discente> discentes) {

    }
}
