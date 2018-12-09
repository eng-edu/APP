package com.developer.edu.arco.view;

import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.arco.util.SocketStatic;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ActMsg extends AppCompatActivity {


    public Socket socket = IO.socket(ConfigRetrofit.URL_BASE);

    public ActMsg() throws URISyntaxException {
        socket.connect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_msg);
        SocketStatic.setSocket(socket);



       // monitorarMsg("");

    }


    private void monitorarMsg(String ARCO_ID) {

        //url de conexão com o servidor websocket
        socket = SocketStatic.getSocket();

        socket.emit("ARCO_ID",ARCO_ID);

        //ler mensagem do server
        socket.on("msg".concat(ARCO_ID), new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                ActMsg.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String s = args[0].toString();
                        Log.i("DEVEDU", s);
                    }
                });
            }
        });

        socket.connect();
    }

}
