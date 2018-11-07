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
import android.widget.TextView;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.model.Discente;
import com.developer.edu.arco.view.NovoArco;

import java.util.List;

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

        TextView nome = view.findViewById(R.id.adapter_discente_nome);
        nome.setText(discente.toString());
        final CheckBox checkBox = view.findViewById(R.id.adapter_discente_checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {

                    if (NovoArco.verificarSize(context)<10) {
                        NovoArco.addDiscente(getItem(position));
                        discente.setCHEKED(true);
                    }

                } else {
                    NovoArco.rmDiscente(getItem(position));
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

