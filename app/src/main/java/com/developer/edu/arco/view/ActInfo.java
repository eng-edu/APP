package com.developer.edu.arco.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.developer.edu.arco.R;
import com.github.barteksc.pdfviewer.PDFView;

public class ActInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_info);


        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.tipo_login), "");

        PDFView b1 = (PDFView) findViewById(R.id.pdfb1);



        if (getIntent().getStringExtra("login_info") != null && getIntent().getStringExtra("login_info").equalsIgnoreCase("S")) {
            b1.fromAsset("a.pdf").load();
        } else {
            if (result.equals("docente")) {

                b1.fromAsset("b.pdf").load();

            } else if (result.equals("discente")) {

                b1.fromAsset("c.pdf").load();

            }
        }


    }
}
