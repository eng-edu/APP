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
import com.developer.edu.arco.model.Discente;
import com.developer.edu.arco.model.Docente;
import com.developer.edu.arco.view.ActNovoArco;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.developer.edu.arco.conectionAPI.ConfigRetrofit.URL_BASE;

public class Adapterdocente extends ArrayAdapter<Docente> {

    private Context context;
    private List<Docente> docentes;

    public Adapterdocente(Context context, List<Docente> docentes) {
        super(context, R.layout.adapter_docentes, docentes);
        this.context = context;
        this.docentes = docentes;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_docentes, parent, false);

        final Docente docente = docentes.get(position);

        ImageView foto = (ImageView) view.findViewById(R.id.image_perfil_adapter);

        String mfoto = docente.getFOTO().replace("./uploads/", "");

        Picasso.get().load(URL_BASE+"/IMG/"+mfoto).into(foto);
        TextView textView1 = view.findViewById(R.id.texte1);
        TextView textView2 = view.findViewById(R.id.texte2);
        TextView textView3 = view.findViewById(R.id.texte3);

        textView2.setText(docente.getFORMACAO());
        textView1.setText(docente.getNOME());
        textView3.setText(docente.getEMAIL());

        return view;
    }

}

