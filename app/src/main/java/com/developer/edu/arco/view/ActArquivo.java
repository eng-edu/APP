package com.developer.edu.arco.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerArquivo;
import com.developer.edu.arco.controller.ControllerEtapa;
import com.developer.edu.arco.model.Arquivo;
import com.developer.edu.arco.util.UtilArco;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
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

        FloatingActionButton novoarquivo = (FloatingActionButton) findViewById(R.id.btn_novo_arquivo);

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
                if(arquivo!= null){
                   ControllerArquivo controllerArquivo = new ControllerArquivo();
                    try {
                        controllerArquivo.novoArquivo(ActArquivo.this, arquivo);
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


            File file = new File(filePath);

            //falta o id DA ETAPA E DO ARCO

            arquivo = null;

            arquivo = new Arquivo();
            arquivo.setARCO_ID(getIntent().getStringExtra("ARCO_ID"));
            arquivo.setETAPA_ID(getIntent().getStringExtra("ETAPA_ARCO_ID"));
            arquivo.setNOME(file.getName());
            arquivo.setCAMINHO(file.getPath());
            try {
                arquivo.setBASE64(UtilArco.toPathFileBase64(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }


            selecioando.setText(arquivo.toString());

        }
    }

}

