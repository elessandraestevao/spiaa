package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Bairro;

/**
 * Created by eless on 05/10/2015.
 */
public class BairroDAO implements BaseDAO<Bairro> {
    Context context;

    public BairroDAO(Context context) {
        this.context = context;
    }


    @Override
    public void insert(Bairro bairro) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Bairro.ID, bairro.getId());
        content.put(Bairro.NOME, bairro.getNome());
        content.put(Bairro.COORDENADAS, bairro.getCoordenadas());
        sqlLite.insert(Bairro.TABLE_NAME, null, content);
    }

    @Override
    public Bairro select(Bairro entity) throws Exception {
        Bairro bairro = null;
        Cursor cursor = null;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        String where = Bairro.ID + " = ?";

        String[] colunas = new String[]{Bairro.ID, Bairro.NOME, Bairro.COORDENADAS};
        String argumentos[] = new String[]{entity.getId().toString()};
        cursor = sqlLite.query(Bairro.TABLE_NAME, colunas, where, argumentos, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            bairro = new Bairro();
            bairro.setId(cursor.getLong(0));
            bairro.setNome(cursor.getString(1));
            bairro.setCoordenadas(cursor.getString(2));
            cursor.close();
        }
        return bairro;
    }

    @Override
    public void update(Bairro entity) throws Exception {

    }

    @Override
    public void delete(Bairro entity) throws Exception {

    }
}
