package com.developer.edu.arco.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.developer.edu.arco.R;
import com.developer.edu.arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.arco.dao.ArquivoDAO;
import com.developer.edu.arco.model.Arquivo;
import com.developer.edu.arco.util.UtilArco;
import com.developer.edu.arco.view.ActArquivo;
import com.developer.edu.arco.view.ActViewPDF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerArquivo {

    public void novoArquivo(final Context context, final String json) throws Exception {


        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        Call<String> stringCall = ConfigRetrofit.getService().novoArquivoEtapa(result, json);

        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(context, "Upload feito com sucesso!", Toast.LENGTH_LONG).show();
                }

                //atualiza a lista de arquivos
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

    public void up(final Context context, String filePath, final String ETAPA_ID, final String ARCO_ID, final ListView listView, final ArrayAdapter<Arquivo> adapter) throws Exception {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(false);
        dialog.show();

        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        File file = new File(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


        // finally, execute the request
        Call<ResponseBody> call = ConfigRetrofit.getService().upload(result, fileToUpload, filename, ETAPA_ID, ARCO_ID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");
                Toast.makeText(context, "success", Toast.LENGTH_LONG).show();
                dialog.dismiss();

                buscarArquivos(context, ETAPA_ID, listView, adapter);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                dialog.dismiss();
            }
        });

    }

    public void buscarArquivos(final Context context, String ETAPA_ID, final ListView listView, final ArrayAdapter<Arquivo> adapter) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        Call<String> stringCall = ConfigRetrofit.getService().buscarArquivosEtapa(result, ETAPA_ID);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){


                    ArquivoDAO arquivoDAO = new ArquivoDAO();

                    arquivoDAO.deletAll(context);

                    try {
                        JSONArray array = new JSONArray(response.body());

                        int sizeArray = array.length();

                        for (int i = 0; i < sizeArray; i++) {

                            JSONObject object = array.getJSONObject(i);

                            arquivoDAO.inserir(context,
                                    object.getString("ID"),
                                    object.getString("ETAPA_ID"),
                                    object.getString("ARCO_ID"),
                                    object.getString("NOME"),
                                    object.getString("CAMINHO")
                                    );

                        }

                        } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //atulizando a listview
                    adapter.clear();
                    adapter.addAll(arquivoDAO.buscarTodos(context));
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Arquivo arquivo = adapter.getItem(position);

                String arq = arquivo.getCAMINHO().replace("./uploads/", "");

                context.startActivity(new Intent(context, ActViewPDF.class).putExtra("PDF","https://docs.google.com/gview?embedded=true&url=http://191.252.193.192:8052/PDF/"+arq ));

            }
        });


    }



}
