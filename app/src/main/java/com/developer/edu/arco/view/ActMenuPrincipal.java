package com.developer.edu.arco.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerArco;
import com.developer.edu.arco.controller.ControllerLogin;

public class ActMenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("MENU PRINCIPAL");

        final ControllerArco controllerArco = new ControllerArco();
        final LayoutInflater inflater = getLayoutInflater();

        Button novo_solic = (Button) findViewById(R.id.btn_principal_novoarco_solicitacoes);
        Button meus_arcos = (Button) findViewById(R.id.btn_principal_meusarcos);
        Button arcos_compartilhados = (Button) findViewById(R.id.btn_principal_arcoscompartilhados);

        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.tipo_login), "");

        if (result.equals("docente")) {
            novo_solic.setText("Solicitações de orientação");
        }

        //pensa na logica de como chamr o novo arco... //pegar por shraedpreferences...

        novo_solic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //verifica se foi dsicente
                if (result.length() > 0) {

                    if (result.equals("discente")) {
                        Intent intent = new Intent(ActMenuPrincipal.this, ActNovoArco.class);
                        startActivity(intent);
                    } else if (result.equals("docente")) {
                        controllerArco.buscarSolicitações(ActMenuPrincipal.this, inflater);
                    }

                }
            }
        });

        //meus arcos
        meus_arcos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //verifica se foi dsicente
                if (result.length() > 0) {
                    Intent intent = new Intent(ActMenuPrincipal.this, ActArco.class);
                    if (result.equals("discente")) {
                        intent.putExtra("ACESSO_RESTRITO", "N");
                        controllerArco.buscarMeusArcosDiscentes(ActMenuPrincipal.this, inflater, intent);
                    } else if (result.equals("docente")) {
                        intent.putExtra("ACESSO_RESTRITO", "N");
                        controllerArco.buscarMeusArcosDocente(ActMenuPrincipal.this, inflater, intent);
                    }

                }


            }
        });

        arcos_compartilhados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActMenuPrincipal.this, ActArco.class);
                intent.putExtra("ACESSO_RESTRITO", "S");
                controllerArco.buscarArcosCompartilhados(ActMenuPrincipal.this, inflater, intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            new ControllerLogin().logout(this);
        }


        if (item.getItemId() == R.id.info) {

            startActivity(new Intent(ActMenuPrincipal.this, ActInfo.class));

        }

        return super.onOptionsItemSelected(item);
    }
}
