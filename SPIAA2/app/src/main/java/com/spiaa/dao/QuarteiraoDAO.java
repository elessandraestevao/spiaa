package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Bairro;
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
        Quarteirao quarteirao = null;
        Cursor cursor = null;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        String where = Quarteirao.ID + " = ?";

        String[] colunas = new String[]{Quarteirao.ID, Quarteirao.DESCRICAO, Quarteirao.BAIRRO};
        String argumentos[] = new String[]{entity.getId().toString()};
        cursor = sqlLite.query(Quarteirao.TABLE_NAME, colunas, where, argumentos, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            quarteirao = new Quarteirao();
            quarteirao.setId(cursor.getLong(0));
            quarteirao.setDescricao(cursor.getString(1));

            try {
                //Bairro
                Bairro bairro = new Bairro();
                bairro.setId(cursor.getLong(2));
                quarteirao.setBairro(new BairroDAO(context).select(bairro));
            } catch (Exception e) {
                Log.e("SPIAA", "Erro no SELECT de Bairro", e);
            }
            cursor.close();
        }
        return quarteirao;
    }

    @Override
    public void update(Quarteirao entity) throws Exception {

    }

    @Override
    public void delete(Quarteirao entity) throws Exception {

    }
}
