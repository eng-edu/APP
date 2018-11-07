package com.developer.edu.arco.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.arco.dao.DiscenteDAO;
import com.developer.edu.arco.dao.DocenteDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.developer.edu.arco.model.*;
import com.developer.edu.arco.view.NovoArco;
import com.developer.edu.arco.view.adapter.Adapterdiscente;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ControllerArco {

    public void selecionarDocente(final Context context, LayoutInflater inflater, final EditText orientador) {

        final View view = inflater.inflate(R.layout.lista_docentes_discente_dialog, null);

        final ListView listView = (ListView) view.findViewById(R.id.list_alert_list_docentes_discentes);
        final ArrayAdapter<Docente> arrayAdapter = new ArrayAdapter<Docente>(view.getContext(), R.layout.support_simple_spinner_dropdown_item);

        final ProgressDialog dialog = new ProgressDialog(view.getContext());
        dialog.setTitle("Aguarde...");
        dialog.show();

        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        final DocenteDAO docenteDAO = new DocenteDAO();

        Call<String> stringCall = ConfigRetrofit.getService().buscarTodosDocentes(result);
        stringCall.enqueue(new Callback<String>() {


            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.isSuccessful()) {

                    try {

                        JSONArray array = new JSONArray(response.body());

                        int sizeArray = array.length();

                        for (int i = 0; i < sizeArray; i++) {
                            JSONObject object = array.getJSONObject(i);
                            docenteDAO.inserir(context,
                                    object.getString("ID"),
                                    object.getString("NOME"),
                                    object.getString("FORMACAO"),
                                    object.getString("EMAIL"),
                                    object.getString("SENHA"));

                        }

                        arrayAdapter.clear();
                        arrayAdapter.addAll(docenteDAO.buscarTodos(view.getContext()));
                        listView.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();

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


        final AlertDialog alert;
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("SELECIONAR DOCENTE");
        builder.setView(view);
        alert = builder.create();
        alert.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orientador.setText(arrayAdapter.getItem(position).toString());
                NovoArco.setDocente(arrayAdapter.getItem(position));
                alert.dismiss();
            }
        });


    }

    public void showListDiscentes(final Context context, LayoutInflater inflater, final ListView discentes, final ArrayAdapter<Discente> adapter, View view) {


        final AlertDialog alert;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("SELECIONAR DISCENTE");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.clear();
                adapter.addAll(NovoArco.getDiscentes());
                discentes.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setView(view);
        builder.setCancelable(false);
        alert = builder.create();
        alert.show();


    }


    public void selecionarDiscente(final Context context, final LayoutInflater inflater, final ListView discentes, final ArrayAdapter<Discente> adapter) {

        final View view = inflater.inflate(R.layout.lista_docentes_discente_dialog, null);

        final ListView listView = (ListView) view.findViewById(R.id.list_alert_list_docentes_discentes);
        final ArrayAdapter<Discente> arrayAdapter = new Adapterdiscente(context, new ArrayList<Discente>());


        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.show();

        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        final DiscenteDAO discenteDAO = new DiscenteDAO();

        Call<String> stringCall = ConfigRetrofit.getService().buscarTodosDiscentes(result);
        stringCall.enqueue(new Callback<String>() {


            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.isSuccessful()) {

                    try {

                        JSONArray array = new JSONArray(response.body());

                        int sizeArray = array.length();

                        for (int i = 0; i < sizeArray; i++) {
                            JSONObject object = array.getJSONObject(i);
                            discenteDAO.inserir(context,
                                    object.getString("ID"),
                                    object.getString("NOME"),
                                    object.getString("INSTITUICAO"),
                                    object.getString("EMAIL"),
                                    object.getString("SENHA"));

                        }

                        arrayAdapter.clear();
                        arrayAdapter.addAll(discenteDAO.buscarTodos(context));
                        listView.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();
                        dialog.dismiss();

                        //e(final Context context, LayoutInflater inflater, final ListView discentes, final ArrayAdapter<Discente> adapter) {

                        showListDiscentes(context, inflater, discentes, adapter, view);

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


    public void criarArco(String titulo, String tituloGrupo, Docente docente, List<Discente> discentes) {

    }


}
