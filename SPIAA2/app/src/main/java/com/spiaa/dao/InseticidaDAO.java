package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Inseticida;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eless on 05/10/2015.
 */
public class InseticidaDAO implements BaseDAO<Inseticida> {
    Context context;

    public InseticidaDAO(Context context) {
        this.context = context;
    }

    @Override
    public Long insert(Inseticida inseticida) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(Inseticida.ID, inseticida.getId());
        content.put(Inseticida.NOME, inseticida.getNome());
        content.put(Inseticida.UNIDADE, inseticida.getUnidade());

        return sqlLite.insert(Inseticida.TABLE_NAME, null, content);
    }

    @Override
    public Inseticida select(Inseticida entity) throws Exception {
        Inseticida inseticida = null;
        Cursor cursor = null;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        String where = Inseticida.ID + " = ?";

        String[] colunas = new String[]{Inseticida.ID, Inseticida.NOME, Inseticida.UNIDADE};
        String argumentos[] = new String[]{entity.getId().toString()};
        cursor = sqlLite.query(Inseticida.TABLE_NAME, colunas, where, argumentos, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            inseticida = new Inseticida();
            inseticida.setId(cursor.getLong(0));
            inseticida.setNome(cursor.getString(1));
            inseticida.setUnidade(cursor.getString(2));
            cursor.close();
        }
        return inseticida;
    }

    @Override
    public List<Inseticida> selectAll() throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        Cursor cursor = sqlLite.rawQuery("SELECT * FROM " + Inseticida.TABLE_NAME, null);
        List<Inseticida> inseticidaList = null;

        if (cursor != null) {
            cursor.moveToFirst();
            inseticidaList = new ArrayList<>();

            while (!cursor.isAfterLast()) {
                Inseticida inseticida = new Inseticida();
                inseticida.setId(cursor.getLong(0));
                inseticida.setNome(cursor.getString(1));
                inseticida.setUnidade(cursor.getString(2));
                inseticidaList.add(inseticida);
            }
            cursor.close();
        }
        return inseticidaList;
    }

    @Override
    public int update(Inseticida entity) throws Exception {
        return 0;
    }

    @Override
    public int delete(Long id) throws Exception {
        return 0;
    }
}
