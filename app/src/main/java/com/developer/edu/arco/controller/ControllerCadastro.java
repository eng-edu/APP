package com.developer.edu.arco.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.Toast;

import com.developer.edu.arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.arco.view.ActCadastro;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerCadastro {

    Call<String> stringCall;

    public void cadastrar(final Context context, String nome, String form_inst, final String email, final String senha, final boolean discente, final boolean docente, String pathfoto) {

        String result = "S3MS3NH4";

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(false);
        dialog.show();

        if (nome.length() > 0
                && nome.length() > 0
                && form_inst.length() > 0
                && email.length() > 0
                && senha.length() > 0
                && pathfoto.length() > 0) {


            File file = new File(pathfoto);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


            if (docente) {
                stringCall = ConfigRetrofit.getService().cadastarDocente(result, nome, form_inst, email, senha, fileToUpload, filename);
            }

            if (discente) {
                stringCall = ConfigRetrofit.getService().cadastarDiscente(result, nome, form_inst, email, senha, fileToUpload, filename);
            }

            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 201) {
                        //chama o controller login
                        new ControllerLogin().logar(context, email, senha, discente, docente);
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

        } else {
            Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }

    }

}
