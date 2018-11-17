package com.developer.edu.arco.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerCadastro;

public class ActCadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("CADASTRO");

        final EditText nome = (EditText) findViewById(R.id.editText_cadastro_nome);
        final EditText inst_form = (EditText) findViewById(R.id.editText_cadastro_formacao_instituicao);
        final EditText email = (EditText) findViewById(R.id.editText_cadastro_email);
        final EditText senha = (EditText) findViewById(R.id.editText_cadastro_senha);

        Button cadastrar = (Button) findViewById(R.id.button_cadastro_cadastrar);
        final RadioButton discente = (RadioButton) findViewById(R.id.radio_cadastro_discente);
        final RadioButton docente = (RadioButton) findViewById(R.id.radio_cadastro_docente);

        RadioGroup group = (RadioGroup) findViewById(R.id.groupCadastro);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_cadastro_discente) {
                    inst_form.setHint(R.string.ed_hint_instituicao);
                } else if (checkedId == R.id.radio_cadastro_docente) {
                    inst_form.setHint(R.string.ed_hint_formacao);
                }
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ControllerCadastro().cadastrar(v.getContext(),
                        nome.getText().toString(),
                        inst_form.getText().toString(),
                        email.getText().toString(),
                        senha.getText().toString(),
                        discente.isChecked(),
                        docente.isChecked());
            }
        });

    }
}
