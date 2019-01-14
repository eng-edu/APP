package com.developer.edu.arco.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.conectionAPI.ConfigRetrofit;

import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerMsg {

    public static void sendmsg(final Context context, String texto, String ID_AUTOR, String EMAIL_AUTOR, String DATA, final String ARCO_ID, final EditText msg, final Socket socket) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Aguarde...");
        dialog.setCancelable(false);
        dialog.show();


        Call<String> stringCall = ConfigRetrofit.getService().send(result, texto, ID_AUTOR, EMAIL_AUTOR, DATA, ARCO_ID);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {

                    socket.emit("LIST_MSG", ARCO_ID);
                    msg.setText("");

                } else {
                    Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });


    };

}
