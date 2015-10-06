package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        TipoImoveis tipoImoveis = null;
        Cursor cursor = null;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        String where = TipoImoveis.ID + " = ?";

        String[] colunas = new String[]{TipoImoveis.ID, TipoImoveis.SIGLA, TipoImoveis.DESCRICAO};
        String argumentos[] = new String[]{entity.getId().toString()};
        cursor = sqlLite.query(TipoImoveis.TABLE_NAME, colunas, where, argumentos, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            tipoImoveis = new TipoImoveis();
            tipoImoveis.setId(cursor.getLong(0));
            tipoImoveis.setSigla(cursor.getString(1));
            tipoImoveis.setDescricao(cursor.getString(2));
            cursor.close();
        }
        return tipoImoveis;
    }

    @Override
    public void update(TipoImoveis entity) throws Exception {

    }

    @Override
    public void delete(TipoImoveis entity) throws Exception {

    }
}
