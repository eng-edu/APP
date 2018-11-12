package com.developer.edu.arco.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.MeasureUnit;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerArco;
import com.developer.edu.arco.controller.ControllerLogin;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

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
                        Intent intent = new Intent(MenuPrincipal.this, NovoArco.class);
                        startActivity(intent);
                    }else if(result.equals("docente")){
                        controllerArco.buscarSolicitações(MenuPrincipal.this, inflater);
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
                    Intent intent = new Intent(MenuPrincipal.this, Arco.class);
                    if (result.equals("discente")) {
                        controllerArco.buscarMeusArcosDiscentes(MenuPrincipal.this, inflater, intent);
                    }else if(result.equals("docente")){
                        controllerArco.buscarMeusArcosDocente(MenuPrincipal.this, inflater, intent);
                    }

                }


            }
        });

        arcos_compartilhados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, Arco.class);
                controllerArco.buscarArcosCompartilhados(MenuPrincipal.this, inflater, intent);
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

        return super.onOptionsItemSelected(item);
    }
}
