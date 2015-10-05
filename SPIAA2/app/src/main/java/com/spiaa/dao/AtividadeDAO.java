package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Atividade;

/**
 * Created by eless on 05/10/2015.
 */
public class AtividadeDAO implements BaseDAO<Atividade> {
    Context context;

    public AtividadeDAO(Context context) {
        this.context = context;
    }

    @Override
    public void insert(Atividade atividade) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Atividade.ID, atividade.getId());
        content.put(Atividade.ENDERECO, atividade.getEndereco());
        content.put(Atividade.INSPECIONADO, atividade.getInspecionado());
        content.put(Atividade.LATITUDE, atividade.getLatitude());
        content.put(Atividade.LONGITUDE, atividade.getLongitude());
        content.put(Atividade.NUMERO, atividade.getNumero());
        content.put(Atividade.OBSERVACAO, atividade.getObservacao());
        content.put(Atividade.QUARTEIRAO, atividade.getQuarteirao().getId());
        content.put(Atividade.TIPO_IMOVEL, atividade.getTipoImoveis().getId());
        content.put(Atividade.TRATAMENTO_ANTIVETORIAL, atividade.getBoletimDiario().getId());
        sqlLite.insert(Atividade.TABLE_NAME, null, content);
    }

    @Override
    public Atividade select(Atividade entity) throws Exception {
        return null;
    }

    @Override
    public void update(Atividade entity) throws Exception {

    }

    @Override
    public void delete(Atividade entity) throws Exception {

    }
}
