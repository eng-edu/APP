package com.developer.edu.arco.view;

import android.Manifest;
import android.app.ProgressDialog;
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
    Button upload;
    TextView selecioando;
    public static Arquivo arquivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_arquivos);

        upload = (Button) findViewById(R.id.upload);
        selecioando = (TextView) findViewById(R.id.selecionado);

        listView = (ListView) findViewById(R.id.lista_arquivos);
        adapter = new ArrayAdapter<Arquivo>(ActArquivo.this, R.layout.support_simple_spinner_dropdown_item);

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


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arquivo != null) {
                  ControllerArquivo controllerArquivo =  new ControllerArquivo();

                    try {

                        JSONObject OBJarquivo = new JSONObject();

                        OBJarquivo.put("NOME", arquivo.getNOME());
                        OBJarquivo.put("ETAPA_ID", arquivo.getETAPA_ID());
                        OBJarquivo.put("ETAPA_ARCO_ID", arquivo.getARCO_ID());
                        OBJarquivo.put("BASE64", arquivo.getBASE64());


                        controllerArquivo.novoArquivo(ActArquivo.this, OBJarquivo.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            Log.i("DEVEDU", filePath);


            ControllerArquivo controllerArquivo = new ControllerArquivo();
            try {

                controllerArquivo.up(ActArquivo.this, filePath, getIntent().getStringExtra("ARCO_ID"), getIntent().getStringExtra("ETAPA_ARCO_ID"));
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}

