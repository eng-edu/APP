package com.developer.edu.arco.view;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerArquivo;
import com.developer.edu.arco.model.Arquivo;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ActArquivo extends AppCompatActivity {

    public static List<Arquivo> arquivos = new ArrayList<>();
    ListView listView;
    ArrayAdapter<Arquivo> adapter;
    public static Arquivo arquivo;
    ControllerArquivo controllerArquivo = new ControllerArquivo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_arquivos);


        listView = (ListView) findViewById(R.id.lista_arquivos);
        adapter = new ArrayAdapter<Arquivo>(ActArquivo.this, R.layout.support_simple_spinner_dropdown_item);

        controllerArquivo.buscarArquivos(ActArquivo.this, getIntent().getStringExtra("ARCO_ID"), listView, adapter);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

            new AlertDialog.Builder(ActArquivo.this)
                    .setTitle("ARQUIVO")
                    .setMessage("TEM CERTEZA?")
                    .setPositiveButton("ABRIR ARQUIVO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Arquivo arquivo = adapter.getItem(position);
                            String arq = arquivo.getCAMINHO().replace("./uploads/", "");
                            startActivity(new Intent(ActArquivo.this, ActViewPDF.class).putExtra("PDF", "https://docs.google.com/gview?embedded=true&url=http://191.252.193.192:8052/PDF/" + arq));

                        }
                    })

                    .setNegativeButton("APAGAR ARQUIVO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            controllerArquivo.apagarArquivo(ActArquivo.this, getIntent().getStringExtra("ARCO_ID"), listView, adapter, position);



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

