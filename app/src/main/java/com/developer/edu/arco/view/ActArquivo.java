package com.developer.edu.arco.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerArquivo;
import com.developer.edu.arco.model.Arquivo;
import com.developer.edu.arco.util.UtilArco;
import com.developer.edu.arco.view.adapter.Adapterarquivo;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ActArquivo extends AppCompatActivity {

    public static List<Arquivo> arquivos = new ArrayList<>();
    ListView listView;
    ArrayAdapter<Arquivo> adapter;
    public static Arquivo arquivo;
    ControllerArquivo controllerArquivo = new ControllerArquivo();
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_arquivos);


        listView = (ListView) findViewById(R.id.lista_arquivos);
        adapter = new Adapterarquivo(ActArquivo.this, arquivos);

        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String tipo_login = sharedPreferences.getString(String.valueOf(R.string.tipo_login), "");


        controllerArquivo.buscarArquivos(ActArquivo.this, getIntent().getStringExtra("ETAPA_ID"), listView, adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

           alertDialog =  new AlertDialog.Builder(ActArquivo.this)
                    .setTitle("APAGANDO ARQUIVO")
                    .setMessage("TEM CERTEZA?")
                    .setCancelable(false)
                    .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final String ID_CRIADOR = getIntent().getStringExtra("ID_CRIADOR");
                            SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
                            final String ID = sharedPreferences.getString(String.valueOf(R.string.ID), "");

                            if (UtilArco.verificarPermissao(ActArquivo.this, ID_CRIADOR, ID, getIntent().getStringExtra("ACESSO_RESTRITO")))
                                controllerArquivo.apagarArquivo(ActArquivo.this, getIntent().getStringExtra("ETAPA_ID"), listView, adapter, position);

                        }
                    })

                    .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();



                return false;
            }
        });


        if (ActivityCompat.checkSelfPermission(ActArquivo.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //e caso ainda não tenha dado, ele solicita...
            ActivityCompat.requestPermissions(ActArquivo.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        final FloatingActionButton novoarquivo = (FloatingActionButton) findViewById(R.id.btn_novo_arquivo);

        if(tipo_login.equals("docente") || getIntent().getStringExtra("ACESSO_RESTRITO").equals("S")){
            novoarquivo.hide();
        }

        novoarquivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(ActArquivo.this)
                        .withRequestCode(1)
                        .withFilter(Pattern.compile(".*\\.pdf$")) // Filtering files and directories by file name using regexp
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            Log.i("DEVEDU", filePath);


            try {

                controllerArquivo.up(ActArquivo.this, filePath, getIntent().getStringExtra("ETAPA_ID"), getIntent().getStringExtra("ARCO_ID"), listView, adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}

