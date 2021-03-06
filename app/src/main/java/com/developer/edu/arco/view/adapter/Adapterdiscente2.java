package com.developer.edu.arco.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.edu.arco.R;
import com.developer.edu.arco.model.Discente;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.developer.edu.arco.conectionAPI.ConfigRetrofit.URL_BASE;

public class Adapterdiscente2 extends ArrayAdapter<Discente> {

    private Context context;
    private List<Discente> discentes;

    public Adapterdiscente2(Context context, List<Discente> discentes) {
        super(context, R.layout.adapter_discentes2, discentes);
        this.context = context;
        this.discentes = discentes;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_discentes2, parent, false);

        final Discente discente = discentes.get(position);

        ImageView foto = (ImageView) view.findViewById(R.id.image_perfil_adapter2);

        //./uploads/16_discente.jpg
        //http://192.168.10.110:8052/IMG/16_discente.jpg

        String mfoto = discente.getFOTO().replace("./uploads/", "");

        Picasso.get().load(URL_BASE+"/IMG/"+mfoto).into(foto);

        TextView textView1 = view.findViewById(R.id.texte1);
        TextView textView2 = view.findViewById(R.id.texte2);
        TextView textView3 = view.findViewById(R.id.texte3);

        textView2.setText(discente.getINSTITUICAO());
        textView1.setText(discente.getNOME());
        textView3.setText(discente.getEMAIL());



        return view;
    }

}

