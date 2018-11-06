package com.developer.edu.arco.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerLogin;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Button novo_solic = (Button) findViewById(R.id.btn_principal_novoarco_solicitacoes);
        Button meus_arcos = (Button) findViewById(R.id.btn_principal_meusarcos);
        Button arcos_compartilhados = (Button) findViewById(R.id.btn_principal_arcoscompartilhados);

        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_key), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.tipo_login), "");


        //pensa na logica de como chamr o novo arco... //pegar por shraedpreferences...

        novo_solic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result.length() > 0) {
                    if (result.equals(1)) {

                        Intent mudarParaNovoArco = new Intent(getApplicationContext(), NovoArco.class);
                        startActivity(mudarParaNovoArco);
                        finish();

                    } else if (result.equals(2)) {

                    }
                }
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
