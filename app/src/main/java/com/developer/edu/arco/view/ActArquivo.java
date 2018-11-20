package com.developer.edu.arco.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Trace;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.util.UtilArco;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.chrono.ThaiBuddhistChronology;

public class ActArquivo extends AppCompatActivity {

    public static final int code = 12;

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

                try {
                    UtilArco.toPathFileBase64(filePath);
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

