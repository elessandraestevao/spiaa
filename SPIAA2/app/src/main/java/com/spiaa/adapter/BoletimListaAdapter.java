package com.spiaa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spiaa.R;
import com.spiaa.modelo.Boletim;

import java.util.List;

/**
 * Created by eless on 22/09/2015.
 */
public class BoletimListaAdapter extends BaseAdapter {
    private Context context;
    private List<Boletim> lista;

    public BoletimListaAdapter(Context context, List<Boletim> lista) {
        this.context = context;
        this.lista = lista;
    }

    public BoletimListaAdapter(Context context) {
        this.context = context;
    }

    public void setLista(List<Boletim> lista) {
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

        contagemBoletim.setText("Boletim Diário " + (position + 1));
        bairro.setText(lista.get(position).getBairro());
        data.setText(String.valueOf(lista.get(position).getData()));
        numeroAtividades.setText(Integer.toString(lista.size()));

        return viewBoletim;
    }
}
