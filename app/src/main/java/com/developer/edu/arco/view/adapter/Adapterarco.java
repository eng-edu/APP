package com.developer.edu.arco.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.model.Arco;
import com.developer.edu.arco.model.Discente;
import com.developer.edu.arco.view.ActNovoArco;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.developer.edu.arco.conectionAPI.ConfigRetrofit.URL_BASE;

public class Adapterarco extends ArrayAdapter<Arco> {

    private Context context;
    private List<Arco> arcos;

    public Adapterarco(Context context, List<Arco> arcos) {
        super(context, R.layout.adaoter_arco, arcos);
        this.context = context;
        this.arcos = arcos;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adaoter_arco, parent, false);

        final Arco arco = arcos.get(position);

        TextView textView1 = view.findViewById(R.id.texte1);
        TextView textView2 = view.findViewById(R.id.texte2);

        textView1.setText("NOME: " + arco.getNOME());
        textView2.setText("STATUS: "+arco.getSTATUS());

        return view;
    }

}

