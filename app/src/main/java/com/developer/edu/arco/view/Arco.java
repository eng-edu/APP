package com.developer.edu.arco.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerArco;


public class Arco extends AppCompatActivity {

    ControllerArco controllerArco = new ControllerArco();
    Button e1;
    Button e2;
    Button e3;
    Button e4;
    Button e5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arco);



        Intent intent = getIntent();
        String ARCO_ID = intent.getStringExtra("ARCO_ID");
        String NOME = intent.getStringExtra("NOME");
        String COMPARTILHADO = intent.getStringExtra("COMPARTILHADO");

        Switch s = (Switch) findViewById(R.id.switch1);
        if(COMPARTILHADO.equals("1")){
            s.setChecked(true);
        }else {
            s.setChecked(false);
        }

        controllerArco.alterarCompartilhado(Arco.this, ARCO_ID, s.isChecked());

        e1 = (Button) findViewById(R.id.btn_e1);
        e2 = (Button) findViewById(R.id.btn_e2);
        e3 = (Button) findViewById(R.id.btn_e3);
        e4 = (Button) findViewById(R.id.btn_e4);
        e5 = (Button) findViewById(R.id.btn_e5);

        e1.setEnabled(true);
        e2.setEnabled(true);
        e3.setEnabled(true);
        e4.setEnabled(true);
        e5.setEnabled(true);

        controllerArco.bucarEtapasArco(Arco.this, ARCO_ID, e1, e2, e3, e4, e5);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_arco, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = getIntent();
        String ARCO_ID = intent.getStringExtra("ARCO_ID");


        if (item.getItemId() == R.id.sinc) {
            controllerArco.bucarEtapasArco(Arco.this, ARCO_ID, e1, e2, e3, e4, e5);
        } else if (item.getItemId() == R.id.lixo) {
            controllerArco.excluirArco(Arco.this);
        } else if (item.getItemId() == R.id.msg) {

        }
        return super.onOptionsItemSelected(item);
    }

}
