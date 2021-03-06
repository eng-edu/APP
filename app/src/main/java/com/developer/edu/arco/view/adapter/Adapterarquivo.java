package com.developer.edu.arco.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.model.Arco;
import com.developer.edu.arco.model.Arquivo;

import java.util.List;

public class Adapterarquivo extends ArrayAdapter<Arquivo> {

    private Context context;
    private List<Arquivo> arquivos;

    public Adapterarquivo(Context context, List<Arquivo> arquivos) {
        super(context, R.layout.adapter_arquivo, arquivos);
        this.context = context;
        this.arquivos = arquivos;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_arquivo, parent, false);

        final Arquivo arquivo = arquivos.get(position);

        TextView textView1 = view.findViewById(R.id.texte1);

        textView1.setText(arquivo.getNOME());


        return view;
    }

}

