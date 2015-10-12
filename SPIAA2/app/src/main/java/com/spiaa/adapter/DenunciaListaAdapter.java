package com.spiaa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spiaa.R;
import com.spiaa.modelo.Denuncia;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import static android.support.v4.content.ContextCompat.getColor;

/**
 * Created by eless on 16/09/2015.
 */
public class DenunciaListaAdapter extends BaseAdapter {
    private Context context;
    private List<Denuncia> lista;

    public DenunciaListaAdapter(Context context, List<Denuncia> lista) {
        this.context = context;
        this.lista = lista;
    }

    public DenunciaListaAdapter(Context context) {
        this.context = context;
    }

    public void setLista(List<Denuncia> lista) {
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
        View viewDenuncia = inflater.inflate(R.layout.denuncia_item_list, null);
        TextView enderecoDenuncia = (TextView) viewDenuncia.findViewById(R.id.endereco_denuncia);
        TextView numeroDenuncia = (TextView) viewDenuncia.findViewById(R.id.numero_denuncia);
        TextView bairroDenuncia = (TextView) viewDenuncia.findViewById(R.id.bairro_denuncia);
        TextView statusDenuncia = (TextView) viewDenuncia.findViewById(R.id.status_denuncia);
        TextView contagemDenuncia = (TextView) viewDenuncia.findViewById(R.id.contagem_denuncia);
        TextView aberturaDenuncia = (TextView) viewDenuncia.findViewById(R.id.data_abertura_denuncia);

        contagemDenuncia.setText("Denúncia " + (position + 1));
        enderecoDenuncia.setText(lista.get(position).getEndereco());
        numeroDenuncia.setText(String.valueOf(lista.get(position).getNumero()));
        bairroDenuncia.setText(" " + lista.get(position).getBairro().getNome());
        statusDenuncia.setText(lista.get(position).getStatus());

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(lista.get(position).getDataAbertura());
        aberturaDenuncia.setText(" " + date);

        //Definir cor do status na listagem de todas as denúncias
        if (lista.get(position).getStatus().equals("Encaminhada")) {
            //cor red
            statusDenuncia.setTextColor(Color.parseColor("#cc0000"));
        } else {
            //cor green
            statusDenuncia.setTextColor(Color.parseColor("#669900"));
        }
        return viewDenuncia;
    }
}
