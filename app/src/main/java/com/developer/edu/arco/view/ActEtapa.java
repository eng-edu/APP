package com.developer.edu.arco.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerEtapa;
import com.developer.edu.arco.dao.EtapaDAO;
import com.developer.edu.arco.model.Etapa;

public class ActEtapa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actetapa);


        final ControllerEtapa controllerEtapa = new ControllerEtapa();


        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.tipo_login), "");
        final String ID = sharedPreferences.getString(String.valueOf(R.string.ID), "");


        Intent intent = getIntent();
        final String ARCO_ID = intent.getStringExtra("ARCO_ID");
        final String NOME_ETAPA = intent.getStringExtra("NOME_ETAPA");
        final String ID_CRIADOR = intent.getStringExtra("ID_CRIADOR");
        final String ACESSO_RESTRITO = intent.getStringExtra("ACESSO_RESTRITO");
        final String COMPARTILHADO = intent.getStringExtra("COMPARTILHADO");
        final String NOME = getIntent().getStringExtra("NOME");

        ActionBar bar = getSupportActionBar();
        bar.setTitle(NOME_ETAPA);

        EtapaDAO etapaDAO = new EtapaDAO();
        final Etapa modelEtapa = etapaDAO.buscarEtapa(getApplicationContext(), NOME_ETAPA);

        final TextView resumo = (TextView) findViewById(R.id.text_etapa_resumo);
        resumo.setText(modelEtapa.getRESUMO());

        Button btn_salvar_reprovar = (Button) findViewById(R.id.btn_salvar_reprovar);

        Button btn_submeter_aprovar = (Button) findViewById(R.id.btn_submeter_aprovar);


        if(ACESSO_RESTRITO.equals("S")){
            btn_salvar_reprovar.setVisibility(View.INVISIBLE);
                btn_submeter_aprovar.setVisibility(View.INVISIBLE);
        }


        if (result.equals("docente")) {

            btn_salvar_reprovar.setText("REPROVAR");
            btn_submeter_aprovar.setText("APROVAR");
            resumo.setEnabled(false);

        } else if (result.equals("discente")) {

            btn_salvar_reprovar.setText("SALVAR");
            btn_submeter_aprovar.setText("SUBMETER");
            resumo.setEnabled(true);
        } else if (ACESSO_RESTRITO.equals("N")) {
            resumo.setEnabled(false);
        }


        btn_submeter_aprovar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (result.equals("docente")) {

                    int prox = Integer.parseInt(modelEtapa.getID()) + 1;

            //        if (UtilArco.verificarAcessoRestrito(ActEtapa.this, ACESSO_RESTRITO))
                        controllerEtapa.aprovarEtapa(ActEtapa.this, modelEtapa.getID(), String.valueOf(prox), ARCO_ID, NOME, COMPARTILHADO, ID_CRIADOR);

                } else if (result.equals("discente")) {

              //      if (UtilArco.verificarPermissao(ActEtapa.this, ID_CRIADOR, ID, getIntent().getStringExtra("ACESSO_RESTRITO")))
                        controllerEtapa.submeterEtapa(ActEtapa.this, modelEtapa.getID(), resumo.getText().toString(), ARCO_ID, NOME, COMPARTILHADO, ID_CRIADOR);

                }

            }
        });

        btn_salvar_reprovar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (result.equals("docente")) {

              //      if (UtilArco.verificarAcessoRestrito(ActEtapa.this, ACESSO_RESTRITO))
                        controllerEtapa.reprovarEtapa(ActEtapa.this, modelEtapa.getID(), ARCO_ID, NOME, COMPARTILHADO, ID_CRIADOR);

                } else if (result.equals("discente")) {

               //     if (UtilArco.verificarPermissao(ActEtapa.this, ID_CRIADOR, ID, getIntent().getStringExtra("ACESSO_RESTRITO")))
                        controllerEtapa.salvarEtapa(ActEtapa.this, modelEtapa.getID(), resumo.getText().toString(), ARCO_ID, NOME, COMPARTILHADO, ID_CRIADOR);


                }


            }
        });




        ImageView folder = (ImageView)findViewById(R.id.iv_etapa_arquivos);
        folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Etapa etapa = new EtapaDAO().buscarEtapa(ActEtapa.this,NOME_ETAPA);

                Intent intent = new Intent(ActEtapa.this, ActArquivo.class);
                intent.putExtra("ARCO_ID", ARCO_ID);
                intent.putExtra("ETAPA_ID", etapa.getID());
                intent.putExtra("ID_CRIADOR",getIntent().getStringExtra("ID_CRIADOR"));
                intent.putExtra("ACESSO_RESTRITO", getIntent().getStringExtra("ACESSO_RESTRITO"));
                startActivity(intent);
                //TENHO NOME DA ETAPA PEGO O ID NO BANCO SQLITE

            }
        });




    }
}
