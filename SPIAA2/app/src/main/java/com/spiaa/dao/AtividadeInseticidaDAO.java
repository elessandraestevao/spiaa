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
import com.spiaa.modelo.AtividadeInseticida;
import com.spiaa.modelo.Criadouro;
import com.spiaa.modelo.Inseticida;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eless on 17/10/2015.
 */
public class AtividadeInseticidaDAO implements BaseDAO<AtividadeInseticida> {
    Context context;

    public AtividadeInseticidaDAO(Context context) {
        this.context = context;
    }

    @Override
    public Long insert(AtividadeInseticida atividadeInseticida) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(AtividadeInseticida.ATIVIDADE, atividadeInseticida.getIdAtividade());
        content.put(AtividadeInseticida.INSETICIDA, atividadeInseticida.getInseticida().getId());
        content.put(AtividadeInseticida.QUANTIDADE, atividadeInseticida.getQuantidadeInseticida());

        Long retorno = sqlLite.insert(AtividadeInseticida.TABLE_NAME, null, content);
        sqlLite.close();
        return retorno;
    }

    public Long insert(List<AtividadeInseticida> atividadeInseticidaList) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        Long retorno = 0L;

        for (AtividadeInseticida atividadeInseticida : atividadeInseticidaList) {
            content.put(AtividadeInseticida.ATIVIDADE, atividadeInseticida.getIdAtividade());
            content.put(AtividadeInseticida.INSETICIDA, atividadeInseticida.getInseticida().getId());
            content.put(AtividadeInseticida.QUANTIDADE, atividadeInseticida.getQuantidadeInseticida());

            retorno = sqlLite.insert(AtividadeInseticida.TABLE_NAME, null, content);
        }
        sqlLite.close();
        return retorno;
    }

    @Override
    public AtividadeInseticida select(AtividadeInseticida entity) throws Exception {
        return null;
    }

    @Override
    public List<AtividadeInseticida> selectAll() throws Exception {
        return null;
    }

    public List<AtividadeInseticida> selectAllDaAtividade(Long id) {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        Cursor cursor = sqlLite.rawQuery("SELECT * FROM " + AtividadeInseticida.TABLE_NAME + " WHERE " + AtividadeInseticida.ATIVIDADE + " = " + id, null);
        List<AtividadeInseticida> atividadeInseticidaList = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            atividadeInseticidaList = new ArrayList<>();

            while (!cursor.isAfterLast()) {
                AtividadeInseticida atividadeInseticida = new AtividadeInseticida();
                atividadeInseticida.setIdAtividade(cursor.getLong(0));
                //Inseticida
                Inseticida inseticida = new Inseticida();
                inseticida.setId(cursor.getLong(1));
                try {
                    atividadeInseticida.setInseticida(new InseticidaDAO(context).select(inseticida));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no select inseticida", e);
                }
                atividadeInseticida.setQuantidadeInseticida(cursor.getInt(2));

                atividadeInseticidaList.add(atividadeInseticida);
                cursor.moveToNext();
            }
            cursor.close();
        }
        sqlLite.close();
        return atividadeInseticidaList;
    }

    @Override
    public int update(AtividadeInseticida entity) throws Exception {
        return 0;
    }

    @Override
    public int delete(Long id) throws Exception {
        //Deleta todas as AtividadesInseticidas da atividade passada como parâmetro
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();

        String where = AtividadeInseticida.ATIVIDADE + " = ?";
        String argumentos[] = new String[]{id.toString()};

        int retorno = sqlLite.delete(AtividadeInseticida.TABLE_NAME, where, argumentos);
        sqlLite.close();

        return retorno;
    }

    public int delete(AtividadeInseticida atividadeInseticida) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        String where = AtividadeInseticida.ATIVIDADE + " = ? " + AtividadeInseticida.INSETICIDA + " = ?";
        String argumentos[] = new String[]{atividadeInseticida.getIdAtividade().toString(), atividadeInseticida.getInseticida().toString()};

        int retorno = sqlLite.delete(AtividadeInseticida.TABLE_NAME, where, argumentos);
        sqlLite.close();
        return retorno;
    }
}
