package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Quarteirao;

/**
 * Created by eless on 05/10/2015.
 */
public class QuarteiraoDAO implements BaseDAO<Quarteirao> {
    Context context;

    public QuarteiraoDAO(Context context) {
        this.context = context;
    }

    @Override
    public void insert(Quarteirao quarteirao) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Quarteirao.ID, quarteirao.getId());
        content.put(Quarteirao.BAIRRO, quarteirao.getBairro().getId());
        content.put(Quarteirao.DESCRICAO, quarteirao.getDescricao());
        sqlLite.insert(Quarteirao.TABLE_NAME, null, content);
    }

    @Override
    public Quarteirao select(Quarteirao entity) throws Exception {
        return null;
    }

    @Override
    public void update(Quarteirao entity) throws Exception {

    }

    @Override
    public void delete(Quarteirao entity) throws Exception {

    }
}
