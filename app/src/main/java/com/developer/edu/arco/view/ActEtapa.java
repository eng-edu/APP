package com.developer.edu.arco.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.dao.EtapaDAO;
import com.developer.edu.arco.model.Etapa;

public class ActEtapa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actetapa);

        Intent intent = getIntent();
        final String ARCO_ID = intent.getStringExtra("ARCO_ID");
        final String NOME_ETAPA = intent.getStringExtra("NOME_ETAPA");

        ActionBar bar = getSupportActionBar();
        bar.setTitle(NOME_ETAPA);

        EtapaDAO etapaDAO = new EtapaDAO();
        Etapa modelEtapa = etapaDAO.buscarEtapa(getApplicationContext(), NOME_ETAPA);

        TextView resumo = (TextView) findViewById(R.id.text_etapa_resumo);
        resumo.setText(modelEtapa.getRESUMO());

    }
}
