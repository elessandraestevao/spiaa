package com.spiaa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spiaa.R;
import com.spiaa.modelo.Atividade;

import java.util.List;

/**
 * Created by eless on 26/09/2015.
 */
public class AtividadeListaAdapter extends BaseAdapter {

    private Context context;
    private List<Atividade> lista;

    public AtividadeListaAdapter(Context context, List<Atividade> lista) {
        this.context = context;
        this.lista = lista;
    }

    public AtividadeListaAdapter(Context context) {
        this.context = context;
    }

    public void setLista(List<Atividade> lista) {
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
        View viewAtividade = inflater.inflate(R.layout.atividade_item_list, null);

        TextView numeroQuarteirao = (TextView) viewAtividade.findViewById(R.id.numero_quarteirao);
        TextView enderecoCompleto = (TextView) viewAtividade.findViewById(R.id.endereco_atividade);
        TextView contagemAtividade = (TextView) viewAtividade.findViewById(R.id.contagem_atividade);
        TextView statusAtividade = (TextView) viewAtividade.findViewById(R.id.status_atividade);

        contagemAtividade.setText("Atividade " + (position + 1));
        numeroQuarteirao.setText(lista.get(position).getQuarteirao().getDescricao());
        enderecoCompleto.setText(lista.get(position).getEndereco() + ", " + lista.get(position).getNumero());
        statusAtividade.setText(lista.get(position).getObservacao());

        //Definir cor do status na listagem de todas as atividades
        if (lista.get(position).getObservacao().equals("FECHADO")) {
            //cor red
            statusAtividade.setTextColor(Color.parseColor("#cc0000"));
        } else if (lista.get(position).getObservacao().equals("RESGATADO")) {
            //cor blue
            statusAtividade.setTextColor(Color.parseColor("#0029A3"));
        } else {
            //cor green para RECEBIDO
            statusAtividade.setTextColor(Color.parseColor("#669900"));
        }

        return viewAtividade;
    }
}
