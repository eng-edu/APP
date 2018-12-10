package com.developer.edu.arco.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

        TextView nome = (TextView) view.findViewById(R.id.nome);
        TextView msg = (TextView) view.findViewById(R.id.msg);

        nome.setText(msMensagem.getNOME_AUTOR());
        msg.setText(msMensagem.getTEXTO());

        return view;
    }

}

