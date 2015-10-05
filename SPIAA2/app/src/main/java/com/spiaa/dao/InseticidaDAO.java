package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Inseticida;

/**
 * Created by eless on 05/10/2015.
 */
public class InseticidaDAO implements BaseDAO<Inseticida> {
    Context context;

    public InseticidaDAO(Context context) {
        this.context = context;
    }

    @Override
    public void insert(Inseticida inseticida) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Inseticida.ID, inseticida.getId());
        content.put(Inseticida.NOME, inseticida.getNome());
        content.put(Inseticida.UNIDADE, inseticida.getUnidade());
        sqlLite.insert(Inseticida.TABLE_NAME, null, content);
    }

    @Override
    public Inseticida select(Inseticida entity) throws Exception {
        return null;
    }

    @Override
    public void update(Inseticida entity) throws Exception {

    }

    @Override
    public void delete(Inseticida entity) throws Exception {

    }
}
