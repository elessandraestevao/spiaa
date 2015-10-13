package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Criadouro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eless on 05/10/2015.
 */
public class CriadouroDAO implements BaseDAO<Criadouro> {
    Context context;

    public CriadouroDAO(Context context) {
        this.context = context;
    }

    @Override
    public Long insert(Criadouro criadouro) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(Criadouro.ID, criadouro.getId());
        content.put(Criadouro.GRUPO, criadouro.getGrupo());
        content.put(Criadouro.RECIPIENTE, criadouro.getRecipiente());

        Long retorno = sqlLite.insert(Criadouro.TABLE_NAME, null, content);
        sqlLite.close();
        return retorno;
    }

    @Override
    public Criadouro select(Criadouro entity) throws Exception {
        Criadouro criadouro = null;
        Cursor cursor = null;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        String where = Criadouro.ID + " = ?";

        String[] colunas = new String[]{Criadouro.ID, Criadouro.GRUPO, Criadouro.RECIPIENTE};
        String argumentos[] = new String[]{entity.getId().toString()};
        cursor = sqlLite.query(Criadouro.TABLE_NAME, colunas, where, argumentos, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            criadouro = new Criadouro();
            criadouro.setId(cursor.getLong(0));
            criadouro.setGrupo(cursor.getString(1));
            criadouro.setRecipiente(cursor.getString(2));
            cursor.close();
        }
        sqlLite.close();
        return criadouro;
    }

    @Override
    public List<Criadouro> selectAll() throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        Cursor cursor = sqlLite.rawQuery("SELECT * FROM " + Criadouro.TABLE_NAME, null);
        List<Criadouro> criadouroList = null;

        if (cursor != null) {
            cursor.moveToFirst();
            criadouroList = new ArrayList<>();

            while (!cursor.isAfterLast()) {
                Criadouro criadouro = new Criadouro();
                criadouro.setId(cursor.getLong(0));
                criadouro.setGrupo(cursor.getString(1));
                criadouro.setRecipiente(cursor.getString(2));
                criadouroList.add(criadouro);
            }
            cursor.close();
        }
        sqlLite.close();
        return criadouroList;
    }

    @Override
    public int update(Criadouro criadouro) throws Exception {
        return 0;
    }

    @Override
    public int delete(Long id) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        String where = Criadouro.ID + " = ?";
        String argumentos[] = new String[]{id.toString()};

        int retorno = sqlLite.delete(Criadouro.TABLE_NAME, where, argumentos);
        sqlLite.close();
        return retorno;
    }
}
