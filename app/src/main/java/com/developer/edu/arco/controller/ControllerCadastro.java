package com.developer.edu.arco.controller;

import android.content.Context;
import android.widget.Toast;
import com.developer.edu.arco.conectionAPI.ConfigRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerCadastro {

    Call<String> stringCall;

    public void cadastrar(final Context context, String nome, String form_inst, final String email, final String senha, final boolean discente, final boolean docente) {

        String result = "S3MS3NH4";


        if (nome.length() > 0
                && nome.length() > 0
                && form_inst.length() > 0
                && email.length() > 0
                && senha.length() > 0) {

            if (docente) {
                stringCall = ConfigRetrofit.getService().cadastarDocente(result, nome, form_inst, email, senha);
            }

            if (discente) {
                stringCall = ConfigRetrofit.getService().cadastarDiscente(result, nome, form_inst, email, senha);
            }

            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.code() == 201){
                        //chama o controller login
                        new ControllerLogin().logar(context, email,senha, discente, docente);

                    }else if (response.code() == 405) {
                        Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
        }

    }

}
