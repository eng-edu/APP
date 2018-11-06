package com.developer.edu.arco.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerLogin;

public class Login extends AppCompatActivity {

    ControllerLogin controllerLogin = new ControllerLogin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(String.valueOf(R.string.TOKENAPI), "");


        //verifica se o usuario já está logado
        if (result.length()>0) {
            Intent mudarParaMain = new Intent(getApplicationContext(), MenuPrincipal.class);
            startActivity(mudarParaMain);
            finish();
        }

        final EditText email = (EditText) findViewById(R.id.editText_login_email);
        final EditText senha = (EditText) findViewById(R.id.editText_login_senha);
        Button entar = (Button) findViewById(R.id.button_login_entrar);
        TextView cadastrese = (TextView) findViewById(R.id.textView_login_cadastrese);
        final RadioButton discente = (RadioButton) findViewById(R.id.radio_discente);
        final RadioButton docente = (RadioButton) findViewById(R.id.radio_docente);

        cadastrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mudarParaCadastro = new Intent(getApplicationContext(), Cadastro.class);
                startActivity(mudarParaCadastro);
            }
        });

        entar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerLogin.logar(v.getContext(), email.getText().toString(), senha.getText().toString(), discente.isChecked(), docente.isChecked());
            }
        });
    }

}
