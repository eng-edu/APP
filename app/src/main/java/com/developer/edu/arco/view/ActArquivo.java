package com.developer.edu.arco.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerArquivo;
import com.developer.edu.arco.model.Arquivo;
import com.developer.edu.arco.util.UtilArco;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ActArquivo extends AppCompatActivity {

    public static final int code = 12;

    public static Arquivo arquivo = new Arquivo();

    public static Arquivo getArquivo() {

        if(arquivo == null){
            arquivo = new Arquivo();
        }

        return arquivo;
    }

    public static void setArquivo(Arquivo arquivo) {
        ActArquivo.arquivo = arquivo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_arquivos);


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
                selecionarArquivo(v);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {

                ContentResolver cr = getContentResolver();
                Uri uri = MediaStore.Files.getContentUri("external");

                final String column = "_data";
                final String[] projection = {column};

                String sortOrder = null; //

                String selectionMimeType = MediaStore.Files.FileColumns.MIME_TYPE + "=?";
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf");
                String[] selectionArgsPdf = new String[]{mimeType};
                Cursor allPdfFiles = cr.query(uri, projection, selectionMimeType, selectionArgsPdf, sortOrder);


                allPdfFiles.moveToFirst();

                int indexColuna = allPdfFiles.getColumnIndex(projection[0]);
                String pathImg = allPdfFiles.getString(indexColuna);
                allPdfFiles.close();

                File originalFile = new File(pathImg);

                String fileName = originalFile.getName();
                String filePath = originalFile.getPath();;


                getArquivo().setNOME(fileName);
                getArquivo().setCAMINHO(filePath);

                try {
                    UtilArco.toPathFileBase64();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    // Botão que abre a galeria de mídia para
    // selecionar um arquivo
    public void selecionarArquivo(View v) {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 0);
    }

}

