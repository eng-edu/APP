package com.developer.edu.arco.view;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerArco;
import com.developer.edu.arco.model.Discente;
import com.developer.edu.arco.model.Docente;

import java.util.ArrayList;
import java.util.List;

public class NovoArco extends AppCompatActivity {

    public static Docente docente;
    public static List<Discente> discentes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_arco);

        final EditText titulo = (EditText) findViewById(R.id.ed_novoarco_titulo);
        final EditText nomegrpo = (EditText) findViewById(R.id.ed_novoarco_grupo);
        final EditText orientador = (EditText) findViewById(R.id.ed_novoarco_orientador);
        final ListView listaDiscentes = (ListView) findViewById(R.id.list_novoarco_discentes);
        FloatingActionButton novoDiscente = (FloatingActionButton) findViewById(R.id.float_novoarco_novoDiscente);
        Button criar = (Button) findViewById(R.id.btn_novoarco_criar);
        Button cancelar = (Button) findViewById(R.id.btn_novoarco_cancelar);
        final ArrayAdapter<Discente> arrayAdapter = new ArrayAdapter<Discente>(NovoArco.this, R.layout.support_simple_spinner_dropdown_item);

        arrayAdapter.clear();
        arrayAdapter.addAll(getDiscentes());
        listaDiscentes.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();


        final ControllerArco controllerArco = new ControllerArco();
        final LayoutInflater inflater = getLayoutInflater();
        orientador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                controllerArco.selecionarDocente(NovoArco.this, inflater, orientador);
            }
        });

        novoDiscente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discentes.clear();
                controllerArco.selecionarDiscente(NovoArco.this, inflater, listaDiscentes, arrayAdapter);
            }
        });

        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                controllerArco.criarArco(titulo.getText().toString(), nomegrpo.getText().toString(), getDocente(), getDiscentes());

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docente = null;
                discentes.clear();
            }
        });

    }


    public static List<Discente> getDiscentes() {
        return discentes;
    }


    public static int verificarSize(Context context) {

        if(discentes.size()>=10){
            Toast.makeText(context, "limite máximo atingido!", Toast.LENGTH_SHORT).show();
        }

        return discentes.size();
    }

    public static void addDiscente(Discente discente) {

        if (!discentes.contains(discente)) {
            NovoArco.discentes.add(discente);
        }

    }

    public static void rmDiscente(Discente discente) {
        NovoArco.discentes.remove(discente);
    }

    public static Docente getDocente() {
        return docente;
    }

    public static void setDocente(Docente docente) {
        NovoArco.docente = docente;
    }
}
