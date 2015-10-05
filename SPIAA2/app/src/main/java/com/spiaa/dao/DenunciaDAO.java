package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Denuncia;

/**
 * Created by eless on 05/10/2015.
 */
public class DenunciaDAO implements BaseDAO<Denuncia> {
    Context context;

    public DenunciaDAO(Context context) {
        this.context = context;
    }

    @Override
    public void insert(Denuncia denuncia) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Denuncia.ID, denuncia.getId());
        content.put(Denuncia.BAIRRO, denuncia.getBairro().getId());
        content.put(Denuncia.ENDERECO, denuncia.getEndereco());
        content.put(Denuncia.IRREGULARIDADE, denuncia.getIrregularidade());
        content.put(Denuncia.NUMERO, denuncia.getNumero());
        content.put(Denuncia.STATUS, denuncia.getStatus());
        content.put(Denuncia.USUARIO, denuncia.getUsuario().getId());
        sqlLite.insert(Denuncia.TABLE_NAME, null, content);
    }

    @Override
    public Denuncia select(Denuncia entity) throws Exception {
        return null;
    }

    @Override
    public void update(Denuncia entity) throws Exception {

    }

    @Override
    public void delete(Denuncia entity) throws Exception {

    }
}
