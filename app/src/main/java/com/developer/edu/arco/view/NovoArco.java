package com.developer.edu.arco.view;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerArco;

import java.util.List;

public class NovoArco extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_arco);

        EditText titulo = (EditText) findViewById(R.id.ed_novoarco_titulo);
        EditText nomegrpo = (EditText) findViewById(R.id.ed_novoarco_grupo);
        EditText orientador = (EditText) findViewById(R.id.ed_novoarco_orientador);
        ListView listaDiscentes = (ListView) findViewById(R.id.list_novoarco_discentes);
        FloatingActionButton novoDiscente = (FloatingActionButton) findViewById(R.id.float_novoarco_novoDiscente);
        Button criar =(Button) findViewById(R.id.btn_novoarco_criar);
        Button cancelar = (Button) findViewById(R.id.btn_novoarco_cancelar);



        
        ControllerArco controllerArco = new ControllerArco();
        controllerArco.buscarTodosdocentes(this);
        controllerArco.buscarTodosDiscentes(this);


    }
}
