package com.developer.edu.arco.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerArco;
import com.developer.edu.arco.util.SocketStatic;
import com.developer.edu.arco.util.UtilArco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ActArco extends AppCompatActivity {

    ControllerArco controllerArco = new ControllerArco();
    Button e1;
    Button e2;
    Button e3;
    Button e4;
    Button e5;

    ImageView i1;
    ImageView i2;
    ImageView i3;
    ImageView i4;
    ImageView i5;

    TextView status;


    public Socket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arco);

        socket = SocketStatic.getSocket();
        socket.connect();

        final String ARCO_ID = getIntent().getStringExtra("ARCO_ID");
        final String NOME = getIntent().getStringExtra("NOME");
        final String COMPARTILHADO = getIntent().getStringExtra("COMPARTILHADO");
        final String ID_CRIADOR = getIntent().getStringExtra("ID_CRIADOR");
        final String STATUS = getIntent().getStringExtra("STATUS");

        status = (TextView) findViewById(R.id.status_arco);
        status.setText(STATUS);

        ActionBar bar = getSupportActionBar();
        bar.setTitle(NOME);


        final Switch s = (Switch) findViewById(R.id.switch1);
        if (COMPARTILHADO.equals("1")) {
            s.setChecked(true);
            s.setText("Compartilhado");
        } else {
            s.setChecked(false);
            s.setText("Não compartilhado");
        }

        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String ID = sharedPreferences.getString(String.valueOf(R.string.ID), "");

        if (!UtilArco.verificarPermissao(ActArco.this, ID_CRIADOR, ID, getIntent().getStringExtra("ACESSO_RESTRITO"))) {
            s.setVisibility(View.INVISIBLE);
        }
            s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    controllerArco.alterarCompartilhado(ActArco.this, ARCO_ID, isChecked, s);
            }
        });


        e1 = (Button) findViewById(R.id.btn_e1);
        e2 = (Button) findViewById(R.id.btn_e2);
        e3 = (Button) findViewById(R.id.btn_e3);
        e4 = (Button) findViewById(R.id.btn_e4);
        e5 = (Button) findViewById(R.id.btn_e5);

        e1.setEnabled(false);
        e2.setEnabled(false);
        e3.setEnabled(false);
        e4.setEnabled(false);
        e5.setEnabled(false);


        i1 = (ImageView) findViewById(R.id.i1);
        i2 = (ImageView) findViewById(R.id.i2);
        i3 = (ImageView) findViewById(R.id.i3);
        i4 = (ImageView) findViewById(R.id.i4);
        i5 = (ImageView) findViewById(R.id.i5);

        controllerArco.bucarEtapasArco(ActArco.this, ARCO_ID, e1, e2, e3, e4, e5, i1,i2,i3,i4,i5, getIntent().getStringExtra("ACESSO_RESTRITO"));

        //quando ouver uam alteração o server vai atulizar
        socket.on("ARCO".concat(ARCO_ID), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ActArco.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = args[0].toString(); //aqui recebo o json do arco

                        try {

                            JSONArray array = new JSONArray(result);
                            JSONObject object = array.getJSONObject(0);
                            status.setText(object.getString("STATUS"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        controllerArco.bucarEtapasArco(ActArco.this, ARCO_ID, e1, e2, e3, e4, e5, i1, i2, i3, i4, i5, getIntent().getStringExtra("ACESSO_RESTRITO"));

                    }
                });
            }
        });


        final Intent intent = new Intent(ActArco.this, ActEtapa.class);
        intent.putExtra("ARCO_ID", ARCO_ID);
        intent.putExtra("ID_CRIADOR", ID_CRIADOR);
        intent.putExtra("NOME_ETAPA", "OBSERVAÇÃO DA REALIDADE");
        intent.putExtra("ACESSO_RESTRITO", getIntent().getStringExtra("ACESSO_RESTRITO"));
        intent.putExtra("COMPARTILHADO", getIntent().getStringExtra("COMPARTILHADO"));
        intent.putExtra("NOME", getIntent().getStringExtra("NOME"));
        intent.putExtra("ID_CRIADOR", getIntent().getStringExtra("ID_CRIADOR"));
        intent.putExtra("STATUS", STATUS);

        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("NOME_ETAPA", "OBSERVAÇÃO DA REALIDADE");
                startActivity(intent);

            }
        });

        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("NOME_ETAPA", "PONTOS CHAVES");
                startActivity(intent);

            }
        });

        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("NOME_ETAPA", "TEORIZAÇÃO");
                startActivity(intent);

            }
        });

        e4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("NOME_ETAPA", "HIPÓTESES DE SOLUÇÃO");
                startActivity(intent);

            }
        });

        e5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("NOME_ETAPA", "APLICAÇÃO A REALIDADE");
                startActivity(intent);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_arco, menu);
        MenuItem item = menu.findItem(R.id.lixo);

        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String ID = sharedPreferences.getString(String.valueOf(R.string.ID), "");
        Intent intent = getIntent();
        String ARCO_ID = intent.getStringExtra("ARCO_ID");
        String ID_CRIADOR = intent.getStringExtra("ID_CRIADOR");
        final String tipo_login = sharedPreferences.getString(String.valueOf(R.string.tipo_login), "");

        if (!UtilArco.verificarPermissao(ActArco.this, ID_CRIADOR, ID, getIntent().getStringExtra("ACESSO_RESTRITO"))) {
            item.setVisible(false);
        }

        if (tipo_login.equals("docente")) {
            item.setVisible(false);
        }

            return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = getIntent();
        String ARCO_ID = intent.getStringExtra("ARCO_ID");
        String ID_CRIADOR = intent.getStringExtra("ID_CRIADOR");
        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String ID = sharedPreferences.getString(String.valueOf(R.string.ID), "");

        if (item.getItemId() == R.id.sinc) {
            SocketStatic.getSocket().emit("ATUALIZOU_ARCO", ARCO_ID);
        } else

        if (item.getItemId() == R.id.lixo) {

            if (UtilArco.verificarPermissao(ActArco.this, ID_CRIADOR, ID, getIntent().getStringExtra("ACESSO_RESTRITO"))) {
                controllerArco.excluirArco(ActArco.this, ARCO_ID);
            }

        } else if (item.getItemId() == R.id.msg) {
            startActivity(new Intent(getApplicationContext(), ActMsg.class).putExtra("ARCO_ID", ARCO_ID));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SocketStatic.getSocket().connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketStatic.getSocket().disconnect();
    }
}
