package com.spiaa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spiaa.R;
import com.spiaa.modelo.TratamentoAntiVetorial;

import java.util.List;

/**
 * Created by eless on 22/09/2015.
 */
public class BoletimListaAdapter extends BaseAdapter {
    private Context context;
    private List<TratamentoAntiVetorial> lista;

    public BoletimListaAdapter(Context context, List<TratamentoAntiVetorial> lista) {
        this.context = context;
        this.lista = lista;
    }

    public BoletimListaAdapter(Context context) {
        this.context = context;
    }

    public void setLista(List<TratamentoAntiVetorial> lista) {
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewBoletim = inflater.inflate(R.layout.boletim_item_list, null);

        TextView bairro = (TextView) viewBoletim.findViewById(R.id.bairro_boletim);
        TextView data = (TextView) viewBoletim.findViewById(R.id.data_boletim);
        TextView numeroAtividades = (TextView) viewBoletim.findViewById(R.id.numero_atividades_boletim);
        TextView contagemBoletim = (TextView) viewBoletim.findViewById(R.id.contagem_boletim);
        TextView statusBoletim = (TextView) viewBoletim.findViewById(R.id.status_boletim);

        contagemBoletim.setText("Boletim Diário " + (position + 1));
        bairro.setText(lista.get(position).getBairro().getNome());
        data.setText(String.valueOf(lista.get(position).getData()));
        numeroAtividades.setText(Integer.toString(lista.size()));
        statusBoletim.setText(lista.get(position).getStatus());

        //Definir cor do status na listagem de todos os boletins
        if (lista.get(position).getStatus().equals("Em aberto")) {
            //cor red
            statusBoletim.setTextColor(Color.parseColor("#cc0000"));
        } else {
            //cor green
            statusBoletim.setTextColor(Color.parseColor("#669900"));
        }

        return viewBoletim;
    }
}
