package com.developer.edu.arco.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerCadastro;

public class ActCadastro extends AppCompatActivity {
    public static final int IMAGEM_INTERNA = 12;

    ImageView fotoPerfil;
    String pathfoto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


         if (ActivityCompat.checkSelfPermission(ActCadastro.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //e caso ainda não tenha dado, ele solicita...
            ActivityCompat.requestPermissions(ActCadastro.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        ActionBar bar = getSupportActionBar();
        bar.setTitle("CADASTRO");

        final EditText nome = (EditText) findViewById(R.id.editText_cadastro_nome);
        final EditText inst_form = (EditText) findViewById(R.id.editText_cadastro_formacao_instituicao);
        final EditText email = (EditText) findViewById(R.id.editText_cadastro_email);
        final EditText senha = (EditText) findViewById(R.id.editText_cadastro_senha);

        Button cadastrar = (Button) findViewById(R.id.button_cadastro_cadastrar);
        final RadioButton discente = (RadioButton) findViewById(R.id.radio_cadastro_discente);
        final RadioButton docente = (RadioButton) findViewById(R.id.radio_cadastro_docente);

        RadioGroup group = (RadioGroup) findViewById(R.id.groupCadastro);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_cadastro_discente) {
                    inst_form.setHint(R.string.ed_hint_instituicao);
                } else if (checkedId == R.id.radio_cadastro_docente) {
                    inst_form.setHint(R.string.ed_hint_formacao);
                }
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ControllerCadastro().cadastrar(v.getContext(),
                        nome.getText().toString(),
                        inst_form.getText().toString(),
                        email.getText().toString(),
                        senha.getText().toString(),
                        discente.isChecked(),
                        docente.isChecked(),
                        pathfoto);

            }
        });


        fotoPerfil = (ImageView) findViewById(R.id.image_perfil);
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarImg(v);
            }
        });


    }

    public void pegarImg(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), IMAGEM_INTERNA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == IMAGEM_INTERNA && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.image_perfil);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            pathfoto = picturePath;



        }

    }


}
