package com.developer.edu.arco.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.edu.arco.R;
import com.developer.edu.arco.controller.ControllerArco;
import com.developer.edu.arco.controller.ControllerLogin;
import com.developer.edu.arco.model.Discente;
import com.developer.edu.arco.model.Solicitacao;
import com.developer.edu.arco.view.NovoArco;

import java.util.List;

public class Adaptersolicitacao extends ArrayAdapter<Solicitacao> {

    private Context context;
    private List<Solicitacao> solicitacoes;

    public Adaptersolicitacao(Context context, List<Solicitacao> solicitacoes) {
        super(context, R.layout.adapter_solicitacoes, solicitacoes);
        this.context = context;
        this.solicitacoes = solicitacoes;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.adapter_solicitacoes, parent, false);

        final Solicitacao solicitacao = solicitacoes.get(position);


        final Button aceitar = (Button) view.findViewById(R.id.adapter_solicitacoes_aceitar);
        TextView texto = (TextView) view.findViewById(R.id.adapter_solicitacoes_text);

        texto.setText(solicitacao.getNOME_ARCO());

        aceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ControllerArco().aceitarSolicitacao(context, solicitacao.getID(), solicitacao.getARCO_ID(), aceitar);
            }
        });


        return view;
    }

}

