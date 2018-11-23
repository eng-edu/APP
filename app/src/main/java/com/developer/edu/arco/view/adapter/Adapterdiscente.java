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
import com.developer.edu.arco.view.ActNovoArco;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.developer.edu.arco.conectionAPI.ConfigRetrofit.URL_BASE;

public class Adapterdiscente extends ArrayAdapter<Discente> {

    private Context context;
    private List<Discente> discentes;

    public Adapterdiscente(Context context, List<Discente> discentes) {
        super(context, R.layout.adapter_discentes, discentes);
        this.context = context;
        this.discentes = discentes;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_discentes, parent, false);

        final Discente discente = discentes.get(position);

        ImageView foto = (ImageView) view.findViewById(R.id.image_perfil_adapter);

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

        final CheckBox checkBox = view.findViewById(R.id.adapter_discente_checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {

                    if (ActNovoArco.verificarSize(context)<10) {
                        ActNovoArco.addDiscente(getItem(position));
                        discente.setCHEKED(true);
                    }

                } else {
                    ActNovoArco.rmDiscente(getItem(position));
                    discente.setCHEKED(false);

                }

            }
        });


        if (discente.isCHEKED()) {
            if (!checkBox.isChecked()) {
                checkBox.setChecked(true);
            }
        }

        return view;
    }

}

