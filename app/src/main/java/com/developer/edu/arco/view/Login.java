package com.developer.edu.arco.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerLogin;

public class Login extends AppCompatActivity {

    ControllerLogin controllerLogin = new ControllerLogin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        final EditText email = (EditText) findViewById(R.id.editText_login_email);
        final EditText senha = (EditText) findViewById(R.id.editText_login_senha);
        Button entar =(Button) findViewById(R.id.button_login_entrar);
        TextView cadastrese = (TextView) findViewById(R.id.textView_login_cadastrese);
        final RadioButton discente = (RadioButton) findViewById(R.id.radio_discente);
        final RadioButton docente = (RadioButton) findViewById(R.id.radio_docente);

        entar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerLogin.logar(v ,email.getText().toString() , senha.getText().toString(), discente.isChecked(), docente.isChecked());
            }
        });
    }

}
