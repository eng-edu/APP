package com.developer.edu.arco.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.arco.controller.ControllerArquivo;
import com.developer.edu.arco.controller.ControllerMsg;
import com.developer.edu.arco.model.Mensagem;
import com.developer.edu.arco.util.SocketStatic;
import com.developer.edu.arco.view.adapter.AdapterMsg;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ActMsg extends AppCompatActivity {


    public Socket socket;
    public ArrayAdapter arrayAdapter;
    public Button sendMSG;
    public EditText msg;
    public ListView listMSG;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_msg);

        socket = SocketStatic.getSocket();

        arrayAdapter = new AdapterMsg(getApplicationContext(), new ArrayList<Mensagem>());

        sendMSG = (Button) findViewById(R.id.send_msg);
        msg = (EditText) findViewById(R.id.msg);
        listMSG = (ListView) findViewById(R.id.mensagens);

        final String ARCO_ID = getIntent().getStringExtra("ARCO_ID");

        socket.emit("LIST_MSG", ARCO_ID);

        //ler mensagem do server
        socket.on("msg".concat(ARCO_ID), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ActMsg.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String s = args[0].toString();

                    }
                });
            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String ID_AUTOR = sharedPreferences.getString(String.valueOf(R.string.ID), "");
        final String NOME_AUTOR = sharedPreferences.getString(String.valueOf(R.string.NOME), "");

        sendMSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String MSG = msg.getText().toString();

                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
                String DATA = sdf.format(date);

                ControllerMsg.sendmsg(ActMsg.this,MSG, ID_AUTOR, NOME_AUTOR, DATA, ARCO_ID, msg, socket);

            }
        });


    }


}
