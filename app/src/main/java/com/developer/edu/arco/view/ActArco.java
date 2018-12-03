package com.developer.edu.arco.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerArco;
import com.developer.edu.arco.util.UtilArco;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ActArco extends AppCompatActivity {

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

        final String ARCO_ID = getIntent().getStringExtra("ARCO_ID");
        String NOME = getIntent().getStringExtra("NOME");
        String COMPARTILHADO = getIntent().getStringExtra("COMPARTILHADO");

        ActionBar bar = getSupportActionBar();
        bar.setTitle(NOME);


        final Switch s = (Switch) findViewById(R.id.switch1);
        if (COMPARTILHADO.equals("1")) {
            s.setChecked(true);
            s.setText("Compartilhado");
        } else {
            s.setChecked(false);
            s.setText("Não compartilhado");
        }

        final String ID_CRIADOR = getIntent().getStringExtra("ID_CRIADOR");
        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String ID = sharedPreferences.getString(String.valueOf(R.string.ID), "");

        if (!UtilArco.verificarPermissao(ActArco.this, ID_CRIADOR, ID, getIntent().getStringExtra("ACESSO_RESTRITO"))) {
            s.setVisibility(View.INVISIBLE);
        }

            s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    controllerArco.alterarCompartilhado(ActArco.this, ARCO_ID, isChecked, s);
            }
        });


        e1 = (Button) findViewById(R.id.btn_e1);
        e2 = (Button) findViewById(R.id.btn_e2);
        e3 = (Button) findViewById(R.id.btn_e3);
        e4 = (Button) findViewById(R.id.btn_e4);
        e5 = (Button) findViewById(R.id.btn_e5);

        e1.setEnabled(false);
        e2.setEnabled(false);
        e3.setEnabled(false);
        e4.setEnabled(false);
        e5.setEnabled(false);

        controllerArco.bucarEtapasArco(ActArco.this, ARCO_ID, e1, e2, e3, e4, e5, getIntent().getStringExtra("ACESSO_RESTRITO"));


        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActArco.this, ActEtapa.class);
                intent.putExtra("ARCO_ID", ARCO_ID);
                intent.putExtra("ID_CRIADOR", ID_CRIADOR);
                intent.putExtra("NOME_ETAPA", "OBSERVAÇÃO DA REALIDADE");
                intent.putExtra("ACESSO_RESTRITO", getIntent().getStringExtra("ACESSO_RESTRITO"));
                startActivity(intent);
                finish();

            }
        });

        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActArco.this, ActEtapa.class);

                intent.putExtra("ARCO_ID", ARCO_ID);
                intent.putExtra("ID_CRIADOR", ID_CRIADOR);
                intent.putExtra("NOME_ETAPA", "PONTOS CHAVES");
                intent.putExtra("ACESSO_RESTRITO", getIntent().getStringExtra("ACESSO_RESTRITO"));
                startActivity(intent);
                finish();
            }
        });

        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActArco.this, ActEtapa.class);

                intent.putExtra("ARCO_ID", ARCO_ID);
                intent.putExtra("ID_CRIADOR", ID_CRIADOR);
                intent.putExtra("NOME_ETAPA", "TEORIZAÇÃO");
                intent.putExtra("ACESSO_RESTRITO", getIntent().getStringExtra("ACESSO_RESTRITO"));
                startActivity(intent);
                finish();
            }
        });

        e4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActArco.this, ActEtapa.class);

                intent.putExtra("ARCO_ID", ARCO_ID);
                intent.putExtra("ID_CRIADOR", ID_CRIADOR);
                intent.putExtra("NOME_ETAPA", "HIPÓTESES DE SOLUÇÃO");
                intent.putExtra("ACESSO_RESTRITO", getIntent().getStringExtra("ACESSO_RESTRITO"));
                startActivity(intent);
                finish();
            }
        });

        e5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActArco.this, ActEtapa.class);

                intent.putExtra("ARCO_ID", ARCO_ID);
                intent.putExtra("ID_CRIADOR", ID_CRIADOR);
                intent.putExtra("NOME_ETAPA", "APLICAÇÃO A REALIDADE");
                intent.putExtra("ACESSO_RESTRITO", getIntent().getStringExtra("ACESSO_RESTRITO"));
                startActivity(intent);
                finish();
            }
        });


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
        String ID_CRIADOR = intent.getStringExtra("ID_CRIADOR");
        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String ID = sharedPreferences.getString(String.valueOf(R.string.ID), "");

        if (item.getItemId() == R.id.sinc) {
            controllerArco.bucarEtapasArco(ActArco.this, ARCO_ID, e1, e2, e3, e4, e5, getIntent().getStringExtra("ACESSO_RESTRITO"));
        } else if (item.getItemId() == R.id.lixo) {

            if (UtilArco.verificarPermissao(ActArco.this, ID_CRIADOR, ID, getIntent().getStringExtra("ACESSO_RESTRITO"))) {
                controllerArco.excluirArco(ActArco.this, ARCO_ID);
            }

        } else if (item.getItemId() == R.id.msg) {

        }
        return super.onOptionsItemSelected(item);
    }

}
