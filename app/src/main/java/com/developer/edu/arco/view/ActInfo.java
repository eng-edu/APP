package com.developer.edu.arco.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.developer.edu.arco.R;

public class ActInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_info);


        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString(String.valueOf(R.string.tipo_login), "");

        TextView info = (TextView) findViewById(R.id.info);


        if (getIntent().getStringExtra("login_info") != null && getIntent().getStringExtra("login_info").equalsIgnoreCase("S")) {
            info.setText(R.string.texto_inicial  );
        } else {
            if (result.equals("docente")) {

                info.setText(R.string.texto_docente_menu);

            } else if (result.equals("discente")) {

                info.setText(R.string.texto_discente_menu);
            }
        }


    }
}
