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

    @Override
    public Long insert(AtividadeCriadouro atividadeCriadouro) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(AtividadeCriadouro.ATIVIDADE, atividadeCriadouro.getAtividade().getId());
        content.put(AtividadeCriadouro.CRIADOURO, atividadeCriadouro.getCriadouro().getId());
        content.put(AtividadeCriadouro.QUANTIDADE, atividadeCriadouro.getQuantidadeCriadouro());

        Long retorno = sqlLite.insert(AtividadeCriadouro.TABLE_NAME, null, content);
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

    public List<AtividadeCriadouro> selectAllDaAtividade(Long id){
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        Cursor cursor = sqlLite.rawQuery("SELECT * FROM " + AtividadeCriadouro.TABLE_NAME + " WHERE " + AtividadeCriadouro.ATIVIDADE + " = " + id, null);
        List<AtividadeCriadouro> atividadeCriadouroList = null;

        if (cursor != null) {
            cursor.moveToFirst();
            atividadeCriadouroList = new ArrayList<>();

            while (!cursor.isAfterLast()) {
                AtividadeCriadouro atividadeCriadouro = new AtividadeCriadouro();

                //Atividade
                Atividade atividade = new Atividade();
                atividade.setId(cursor.getLong(0));
                atividadeCriadouro.setAtividade(atividade);

                //Criadouro
                Criadouro criadouro = new Criadouro();
                criadouro.setId(cursor.getLong(1));

                try {
                    atividadeCriadouro.setCriadouro(new CriadouroDAO(context).select(criadouro));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no Select de Criadouro", e);
                }
                atividadeCriadouro.setQuantidadeCriadouro(cursor.getInt(2));
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
        return 0;
    }
}
