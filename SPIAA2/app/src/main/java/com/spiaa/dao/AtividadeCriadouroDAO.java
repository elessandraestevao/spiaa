package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Atividade;
import com.spiaa.modelo.AtividadeCriadouro;
import com.spiaa.modelo.Bairro;
import com.spiaa.modelo.Criadouro;
import com.spiaa.modelo.Quarteirao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eless on 14/10/2015.
 */
public class AtividadeCriadouroDAO implements BaseDAO<AtividadeCriadouro> {
    Context context;

    public AtividadeCriadouroDAO(Context context) {
        this.context = context;
    }

    @Override
    public Long insert(AtividadeCriadouro atividadeCriadouro) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(AtividadeCriadouro.ATIVIDADE, atividadeCriadouro.getIdAtividade());
        content.put(AtividadeCriadouro.CRIADOURO, atividadeCriadouro.getCriadouro().getId());
        content.put(AtividadeCriadouro.QUANTIDADE, atividadeCriadouro.getQuantidadeCriadouro());

        Long retorno = sqlLite.insert(AtividadeCriadouro.TABLE_NAME, null, content);
        sqlLite.close();
        return retorno;
    }

    public Long insert(List<AtividadeCriadouro> atividadeCriadouroList) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        Long retorno = 0L;

        for (AtividadeCriadouro atividadeCriadouro : atividadeCriadouroList) {
            content.put(AtividadeCriadouro.ATIVIDADE, atividadeCriadouro.getIdAtividade());
            content.put(AtividadeCriadouro.CRIADOURO, atividadeCriadouro.getCriadouro().getId());
            content.put(AtividadeCriadouro.QUANTIDADE, atividadeCriadouro.getQuantidadeCriadouro());

            retorno = sqlLite.insert(AtividadeCriadouro.TABLE_NAME, null, content);
        }
        sqlLite.close();
        return retorno;
    }

    @Override
    public AtividadeCriadouro select(AtividadeCriadouro entity) throws Exception {
        return null;
    }

    @Override
    public List<AtividadeCriadouro> selectAll() throws Exception {
        return null;
    }

    public List<AtividadeCriadouro> selectAllDaAtividade(Long id) {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        Cursor cursor = sqlLite.rawQuery("SELECT * FROM " + AtividadeCriadouro.TABLE_NAME + " WHERE " + AtividadeCriadouro.ATIVIDADE + " = " + id, null);
        List<AtividadeCriadouro> atividadeCriadouroList = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            atividadeCriadouroList = new ArrayList<>();

            while (!cursor.isAfterLast()) {
                AtividadeCriadouro atividadeCriadouro = new AtividadeCriadouro();

                //Atividade
                atividadeCriadouro.setIdAtividade(cursor.getLong(0));

                //Criadouro
                Criadouro criadouro = new Criadouro();
                criadouro.setId(cursor.getLong(1));

                try {
                    atividadeCriadouro.setCriadouro(new CriadouroDAO(context).select(criadouro));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no Select de Criadouro", e);
                }
                atividadeCriadouro.setQuantidadeCriadouro(cursor.getInt(2));
                atividadeCriadouroList.add(atividadeCriadouro);
                cursor.moveToNext();
            }
            cursor.close();
        }
        sqlLite.close();
        return atividadeCriadouroList;
    }

    @Override
    public int update(AtividadeCriadouro entity) throws Exception {
        return 0;
    }

    @Override
    public int delete(Long id) throws Exception {
        //Deleta todas as AtividadesCriadouro da atividade passada como parâmetro
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();

        String where = AtividadeCriadouro.ATIVIDADE + " = ?";
        String argumentos[] = new String[]{id.toString()};

        int retorno = sqlLite.delete(AtividadeCriadouro.TABLE_NAME, where, argumentos);
        sqlLite.close();

        return retorno;
    }
}
