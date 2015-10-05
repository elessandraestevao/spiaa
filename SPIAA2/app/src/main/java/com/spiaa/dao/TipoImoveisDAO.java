package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.TipoImoveis;

/**
 * Created by eless on 05/10/2015.
 */
public class TipoImoveisDAO implements BaseDAO<TipoImoveis> {
    Context context;

    public TipoImoveisDAO(Context context) {
        this.context = context;
    }

    @Override
    public void insert(TipoImoveis tipoImoveis) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(TipoImoveis.ID, tipoImoveis.getId());
        content.put(TipoImoveis.DESCRICAO, tipoImoveis.getDescricao());
        content.put(TipoImoveis.SIGLA, tipoImoveis.getSigla());
        sqlLite.insert(TipoImoveis.TABLE_NAME, null, content);
    }

    @Override
    public TipoImoveis select(TipoImoveis entity) throws Exception {
        return null;
    }

    @Override
    public void update(TipoImoveis entity) throws Exception {

    }

    @Override
    public void delete(TipoImoveis entity) throws Exception {

    }
}
