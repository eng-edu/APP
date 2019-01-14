package com.developer.edu.arco.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerMsg;
import com.developer.edu.arco.model.Mensagem;
import com.developer.edu.arco.util.SocketStatic;
import com.developer.edu.arco.view.adapter.AdapterMsg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

        socket = SocketStatic.getSocket().connect();


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

                        List<Mensagem> mensagems = new ArrayList<>();

                        JSONArray array = null;
                        try {
                            array = new JSONArray(s);
                            int sizeArray = array.length();

                            for (int i = 0; i < sizeArray; i++) {

                                JSONObject object = array.getJSONObject(i);

                                Mensagem msg = new Mensagem();
                                msg.setTEXTO(object.getString("TEXTO"));
                                msg.setNOME_AUTOR(object.getString("EMAIL_AUTOR"));
                                msg.setID_AUTOR(object.getString("ID_AUTOR"));
                                msg.setDATA(object.getString("DATA"));
                                msg.setARCO_ID(object.getString("ARCO_ID"));
                                mensagems.add(msg);


                            }

                            arrayAdapter.clear();
                            arrayAdapter.addAll(mensagems);
                            arrayAdapter.notifyDataSetChanged();
                            listMSG.setAdapter(arrayAdapter);

                            listMSG.post(new Runnable() {
                                @Override
                                public void run() {
                                    listMSG.setSelection(listMSG.getCount() - 1);
                                }
                            });



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String ID_AUTOR = sharedPreferences.getString(String.valueOf(R.string.ID), "");
        final String EMAIL_AUTOR = sharedPreferences.getString(String.valueOf(R.string.EMAIL), "");
        sendMSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MSG = msg.getText().toString();
                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
                String DATA = sdf.format(date);
                ControllerMsg.sendmsg(ActMsg.this,MSG, ID_AUTOR,EMAIL_AUTOR, DATA, ARCO_ID, msg, socket);
            }
        });


    }


}
