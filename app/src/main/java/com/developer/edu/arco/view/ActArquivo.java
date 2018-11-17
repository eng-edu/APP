package com.developer.edu.arco.view;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.developer.edu.arco.R;

public class ActArquivo extends AppCompatActivity {

    public static final int code = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_arquivos);


        FloatingActionButton novoarquivo = (FloatingActionButton) findViewById(R.id.btn_novo_arquivo);

        novoarquivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }






}
