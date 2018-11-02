package com.developer.edu.arco.controller;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.developer.edu.arco.conectionAPI.ConfigRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerLogin {

    Call<String> stringCall;

    public void logar(final View view, String email, String senha, boolean discente, boolean docente) {

        if (email.length() > 0 && senha.length() > 0) {

            if (docente) {
               stringCall = ConfigRetrofit.getService().logarDocente(email, senha);
            }

            if (discente) {
                stringCall = ConfigRetrofit.getService().logarDiscente(email, senha);
            }


            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.code() == 200){
                        Toast.makeText(view.getContext(), "Login realizado com sucesso!", Toast.LENGTH_LONG).show();
                    }else if(response.code() == 203){
                        Toast.makeText(view.getContext(), response.body(), Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


        }else {
            Toast.makeText(view.getContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();
        }


    }
}
