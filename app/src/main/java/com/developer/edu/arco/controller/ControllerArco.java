package com.developer.edu.arco.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.arco.dao.ArcoDAO;
import com.developer.edu.arco.dao.DiscenteDAO;
import com.developer.edu.arco.dao.DocenteDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.developer.edu.arco.dao.EtapaDAO;
import com.developer.edu.arco.model.*;
import com.developer.edu.arco.view.MenuPrincipal;
import com.developer.edu.arco.view.NovoArco;
import com.developer.edu.arco.view.adapter.Adapterdiscente;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ControllerArco {

    public void selecionarDocente(final Context context, LayoutInflater inflater, final EditText orientador) {

        final AlertDialog[] alert = new AlertDialog[1];

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

                docenteDAO.deletAll(context);

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


                        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("SELECIONAR DOCENTE");
                        builder.setView(view);
                        alert[0] = builder.create();
                        alert[0].show();


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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orientador.setText(arrayAdapter.getItem(position).toString());
                NovoArco.setDocente(arrayAdapter.getItem(position));
                alert[0].dismiss();
            }
        });


    }

    public void selecionarDiscente(final Context context, final String id_criador, final LayoutInflater inflater, final ListView discentes, final ArrayAdapter<Discente> adapter) {

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

                    discenteDAO.deletAll(context);

                    try {

                        JSONArray array = new JSONArray(response.body());

                        int sizeArray = array.length();

                        for (int i = 0; i < sizeArray; i++) {
                            JSONObject object = array.getJSONObject(i);

                            if (!object.getString("ID").equals(id_criador))
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

    public void criarArco(final Context context, String titulo, String tituloGrupo, String id_criador, final Docente docente, List<Discente> discentes) throws JSONException {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(false);
        dialog.show();

        JSONArray discentes_id = new JSONArray();

        for (Discente d : discentes) {
            JSONObject discente_id = new JSONObject();
            discente_id.put("DISCENTE_ID", d.getID());
            discentes_id.put(discente_id);
        }

        JSONObject arco = new JSONObject();
        arco.put("NOMEARCO", titulo);
        arco.put("NOMEGRUPO", tituloGrupo);
        arco.put("ID_CRIADOR", id_criador);
        arco.put("DISCENTES_ID", discentes_id);
        arco.put("DOCENTE_ID", docente.getID());

        arco.toString();


        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        Call<String> stringCall = ConfigRetrofit.getService().novoArco(result, arco.toString());
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 201) {

                    Toast.makeText(context, "Arco criado com sucesso!", Toast.LENGTH_LONG).show();

                    Intent mudarParaMain = new Intent(context, MenuPrincipal.class);
                    context.startActivity(mudarParaMain);
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

    public void buscarMeusArcosDiscentes(final Context context, final LayoutInflater inflater, final Intent intent) {

        final AlertDialog[] alert = new AlertDialog[1];

        final View view = inflater.inflate(R.layout.lista_docentes_discente_dialog, null);

        final ListView listView = (ListView) view.findViewById(R.id.list_alert_list_docentes_discentes);
        final ArrayAdapter<Arco> arrayAdapter = new ArrayAdapter<Arco>(view.getContext(), R.layout.support_simple_spinner_dropdown_item);

        final ProgressDialog dialog = new ProgressDialog(view.getContext());
        dialog.setTitle("Aguarde...");
        dialog.show();

        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");
        final String id = sharedPreferences.getString(String.valueOf(R.string.ID), "");

        final ArcoDAO arcoDAO = new ArcoDAO();

        Call<String> stringCall = ConfigRetrofit.getService().buscarMeusArcosDiscente(result, id);
        stringCall.enqueue(new Callback<String>() {


            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                arcoDAO.deletAll(context);

                if (response.isSuccessful()) {

                    try {

                        JSONArray array = new JSONArray(response.body());

                        int sizeArray = array.length();

                        for (int i = 0; i < sizeArray; i++) {
                            JSONObject object = array.getJSONObject(i);
                            arcoDAO.inserir(context,
                                    object.getString("ID"),
                                    object.getString("NOME"),
                                    object.getString("STATUS"),
                                    object.getString("ID_CRIADOR"),
                                    object.getString("DOCENTE_ID"),
                                    object.getString("COMPARTILHADO"));

                        }

                        arrayAdapter.clear();
                        arrayAdapter.addAll(arcoDAO.buscarTodos(view.getContext()));
                        listView.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();

                        dialog.dismiss();


                        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("SELECIONAR O ARCO");
                        builder.setView(view);
                        alert[0] = builder.create();
                        alert[0].show();


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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Arco arco = arrayAdapter.getItem(position);

                arco.toString();

                //abrir a activity que tem as etapas dos arcos

                intent.putExtra("ARCO_ID", arco.getID());
                intent.putExtra("NOME", arco.getNOME());
                intent.putExtra("STATUS", arco.getSTATUS());
                intent.putExtra("COMPARTILHADO", arco.getCOMPARTILHADO());
                context.startActivity(intent);
                alert[0].dismiss();
            }
        });


    }

    public void bucarEtapasArco(final Context context, final String arco_id, final Button e1, final Button e2, final Button e3, final Button e4, final Button e5) {


        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(false);
        dialog.show();


        final EtapaDAO etapaDAO = new EtapaDAO();


        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        Call<String> stringCall = ConfigRetrofit.getService().buscarEtapasArco(result, arco_id);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                try {
                    etapaDAO.deletAll(context);

                    JSONArray array = new JSONArray(response.body());

                    int sizeArray = array.length();

                    for (int i = 0; i < sizeArray; i++) {
                        JSONObject object = array.getJSONObject(i);
                        etapaDAO.inserir(context,
                                object.getString("ID"),
                                object.getString("NOME"),
                                object.getString("RESUMO"),
                                object.getString("STATUS"),
                                object.getString("ARCO_ID"));


                        if (object.getString("NOME").equals("OBSERVAÇÃO DA REALIDADE")) {
                            definirIconeEcliclavel(object, context, e1);
                        } else if (object.getString("NOME").equals("PONTOS CHAVES")) {
                            definirIconeEcliclavel(object, context, e2);
                        } else if (object.getString("NOME").equals("TEORIZAÇÃO")) {
                            definirIconeEcliclavel(object, context, e3);
                        } else if (object.getString("NOME").equals("HIPÓTESES DE SOLUÇÃO")) {
                            definirIconeEcliclavel(object, context, e4);
                        } else if (object.getString("NOME").equals("APLICAÇÃO A REALIDADE")) {
                            definirIconeEcliclavel(object, context, e5);
                        }

                    }

                    dialog.dismiss();

                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();

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

    public void definirIconeEcliclavel(JSONObject object, Context context, Button ed) throws JSONException {
        Drawable ic = null;

        //verifico o status e chamo o icone e se o botão é clicavel
        if (object.getString("STATUS").equals("1")) {
            ic = context.getResources().getDrawable(R.mipmap.ic_aprovado);
            ed.setEnabled(true);
        } else if (object.getString("STATUS").equals("2")) {
            ic = context.getResources().getDrawable(R.mipmap.ic_aguardando);
            ed.setEnabled(false);
        } else if (object.getString("STATUS").equals("3")) {
            ic = context.getResources().getDrawable(R.mipmap.ic_reprovado);
            ed.setEnabled(true);
        } else if (object.getString("STATUS").equals("4")) {
            ic = context.getResources().getDrawable(R.mipmap.ic_aprovado_edicao);
            ed.setEnabled(true);
        } else if (object.getString("STATUS").equals("5")) {
            ic = context.getResources().getDrawable(R.mipmap.ic_bloqueado);
            ed.setEnabled(false);
        }

        ic.setBounds(0, 0, ic.getMinimumWidth(), ic.getMinimumHeight());

        ed.setCompoundDrawables(null, null, ic, null);

    }

    public void buscarArcosCompartilhados(final Context context, final LayoutInflater inflater, final Intent intent) {

        final AlertDialog[] alert = new AlertDialog[1];

        final View view = inflater.inflate(R.layout.lista_docentes_discente_dialog, null);

        final ListView listView = (ListView) view.findViewById(R.id.list_alert_list_docentes_discentes);
        final ArrayAdapter<Arco> arrayAdapter = new ArrayAdapter<Arco>(view.getContext(), R.layout.support_simple_spinner_dropdown_item);

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.show();

        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        final ArcoDAO arcoDAO = new ArcoDAO();

        Call<String> stringCall = ConfigRetrofit.getService().buscarArcosCompartilhados(result);
        stringCall.enqueue(new Callback<String>() {


            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                arcoDAO.deletAll(context);

                if (response.isSuccessful()) {

                    try {

                        JSONArray array = new JSONArray(response.body());

                        int sizeArray = array.length();

                        for (int i = 0; i < sizeArray; i++) {
                            JSONObject object = array.getJSONObject(i);
                            arcoDAO.inserir(context,
                                    object.getString("ID"),
                                    object.getString("NOME"),
                                    object.getString("STATUS"),
                                    object.getString("ID_CRIADOR"),
                                    object.getString("DOCENTE_ID"),
                                    object.getString("COMPARTILHADO"));

                        }

                        arrayAdapter.clear();
                        arrayAdapter.addAll(arcoDAO.buscarTodos(view.getContext()));
                        listView.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();

                        dialog.dismiss();


                        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("SELECIONAR O ARCO");
                        builder.setView(view);
                        alert[0] = builder.create();
                        alert[0].show();


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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Arco arco = arrayAdapter.getItem(position);

                arco.toString();

                //abrir a activity que tem as etapas dos arcos

                intent.putExtra("ARCO_ID", arco.getID());
                intent.putExtra("NOME", arco.getNOME());
                intent.putExtra("STATUS", arco.getSTATUS());
                intent.putExtra("COMPARTILHADO", arco.getCOMPARTILHADO());
                context.startActivity(intent);
                alert[0].dismiss();
            }
        });


    }

    public void alterarCompartilhado(final Context context, String ARCO_ID, final Boolean switchState, final Switch s) {


        String comp = "";

        if (switchState) {
            comp = "1";
        } else {
            comp = "2";
        }


        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.show();

        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        Call<String> stringCall = ConfigRetrofit.getService().atulizarCompartilhamento(result, ARCO_ID, comp);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    if (switchState) {
                        s.setText("Compartilhado");

                    } else {
                        s.setText("Não compartilhado");

                    }

                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialog.dismiss();
            }
        });


    }

    public void excluirArco(final Context context) {
    }
}
