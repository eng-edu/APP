package com.developer.edu.arco.view.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.model.Arquivo;
import com.developer.edu.arco.model.Mensagem;

import java.util.List;

public class AdapterMsg extends ArrayAdapter<Mensagem> {

    private Context context;
    private List<Mensagem> mensagens;

    public AdapterMsg(Context context, List<Mensagem> mensagens) {
        super(context, R.layout.adapter_msg, mensagens);
        this.context = context;
        this.mensagens = mensagens;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_msg, parent, false);

        final Mensagem msMensagem = mensagens.get(position);

        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_config), Context.MODE_PRIVATE);
        final String ID_AUTOR = sharedPreferences.getString(String.valueOf(R.string.ID), "");

        TextView nome1 = (TextView) view.findViewById(R.id.nome1);
        TextView msg1 = (TextView) view.findViewById(R.id.texto1);
        TextView nome2 = (TextView) view.findViewById(R.id.nome2);
        TextView msg2 = (TextView) view.findViewById(R.id.texto2);

        if(msMensagem.getID_AUTOR().equals(ID_AUTOR)){

            nome2.setText(msMensagem.getNOME_AUTOR() + " " + msMensagem.getDATA());
            msg2.setText(msMensagem.getTEXTO());
        }else {
            nome1.setText(msMensagem.getNOME_AUTOR() + " " + msMensagem.getDATA());
            msg1.setText(msMensagem.getTEXTO());
        }
        return view;
    }

}

