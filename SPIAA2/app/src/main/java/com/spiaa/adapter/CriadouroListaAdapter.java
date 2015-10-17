package com.spiaa.adapter;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.spiaa.R;
import com.spiaa.modelo.Criadouro;

import java.util.List;

/**
 * Created by eless on 14/10/2015.
 */
public class CriadouroListaAdapter extends BaseAdapter {
    private Context context;
    private List<Criadouro> lista;

    public CriadouroListaAdapter(Context context, List<Criadouro> lista) {
        this.context = context;
        this.lista = lista;
    }

    public CriadouroListaAdapter(Context context) {
        this.context = context;
    }

    public void setLista(List<Criadouro> lista) {
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
        View viewCriadouro = inflater.inflate(R.layout.criadouro_item_list, null);

        //TextInputLayout qtdeCriadouro = (TextInputLayout) viewCriadouro.findViewById(R.id.mesage_qtde_criadouro);
        //qtdeCriadouro.setHint("Quantidade de " + lista.get(position).getGrupo());



        return viewCriadouro;
    }
}
