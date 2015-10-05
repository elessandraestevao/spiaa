package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Criadouro;

/**
 * Created by eless on 05/10/2015.
 */
public class CriadouroDAO implements BaseDAO<Criadouro> {
    Context context;

    public CriadouroDAO(Context context) {
        this.context = context;
    }

    @Override
    public void insert(Criadouro criadouro) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Criadouro.ID, criadouro.getId());
        content.put(Criadouro.GRUPO, criadouro.getGrupo());
        content.put(Criadouro.RECIPIENTE, criadouro.getRecipiente());
        sqlLite.insert(Criadouro.TABLE_NAME, null, content);
    }

    @Override
    public Criadouro select(Criadouro entity) throws Exception {
        return null;
    }

    @Override
    public void update(Criadouro entity) throws Exception {

    }

    @Override
    public void delete(Criadouro entity) throws Exception {

    }
}
