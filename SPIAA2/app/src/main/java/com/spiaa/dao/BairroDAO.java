package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Bairro;
import com.spiaa.modelo.Quarteirao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eless on 05/10/2015.
 */
public class BairroDAO implements BaseDAO<Bairro> {
    Context context;

    public BairroDAO(Context context) {
        this.context = context;
    }


    @Override
    public Long insert(Bairro bairro) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getConnection().getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(Bairro.ID, bairro.getId());
        content.put(Bairro.NOME, bairro.getNome());
        content.put(Bairro.COORDENADAS, bairro.getCoordenadas());

        Long retorno = sqlLite.insert(Bairro.TABLE_NAME, null, content);
        if (retorno != -1) {
            QuarteiraoDAO dao = new QuarteiraoDAO(context);
            for (Quarteirao q : bairro.getQuarteiraoList()) {
                dao.insert(q);
            }
        }
        sqlLite.close();
        return retorno;
    }

    @Override
    public Bairro select(Bairro entity) throws Exception {
        Bairro bairro = null;
        Cursor cursor = null;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getConnection().getReadableDatabase();
        String where = Bairro.ID + " = ?";

        String[] colunas = new String[]{Bairro.ID, Bairro.NOME, Bairro.COORDENADAS};
        String argumentos[] = new String[]{entity.getId().toString()};
        cursor = sqlLite.query(Bairro.TABLE_NAME, colunas, where, argumentos, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            bairro = new Bairro();
            bairro.setId(cursor.getLong(0));
            bairro.setNome(cursor.getString(1));
            bairro.setCoordenadas(cursor.getString(2));
            cursor.close();
        }
        sqlLite.close();
        return bairro;
    }

    @Override
    public List<Bairro> selectAll() throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getConnection().getReadableDatabase();
        Cursor cursor = sqlLite.rawQuery("SELECT * FROM " + Bairro.TABLE_NAME, null);
        List<Bairro> bairroList = null;

        if (cursor != null) {
            cursor.moveToFirst();
            bairroList = new ArrayList<>();

            while (!cursor.isAfterLast()) {
                Bairro bairro = new Bairro();
                bairro.setId(cursor.getLong(0));
                bairro.setNome(cursor.getString(1));
                bairro.setCoordenadas(cursor.getString(2));
                bairroList.add(bairro);
                cursor.moveToNext();
            }
            cursor.close();
        }
        sqlLite.close();
        return bairroList;
    }

    @Override
    public int update(Bairro bairro) throws Exception {
        return 0;
    }

    @Override
    public int delete(Long id) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        String where = Bairro.ID + " = ?";
        String argumentos[] = new String[]{id.toString()};

        int retorno = sqlLite.delete(Bairro.TABLE_NAME, where, argumentos);
        if(retorno == 1){
            //exclui também os quarteirões deste bairro
            new QuarteiraoDAO(context).delete(id);
        }
        sqlLite.close();
        return retorno;

    }

}
