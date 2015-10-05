package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.TratamentoAntiVetorial;

/**
 * Created by eless on 05/10/2015.
 */
public class TratamentoAntiVetorialDAO implements BaseDAO<TratamentoAntiVetorial> {
    Context context;

    public TratamentoAntiVetorialDAO(Context context) {
        this.context = context;
    }

    @Override
    public void insert(TratamentoAntiVetorial tratamentoAntiVetorial) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(TratamentoAntiVetorial.BAIRRO, tratamentoAntiVetorial.getBairro().getId());
        content.put(TratamentoAntiVetorial.DATA, tratamentoAntiVetorial.getData().toString());
        content.put(TratamentoAntiVetorial.NUMERO_ATIVIDADE, tratamentoAntiVetorial.getNumeroAtividade());
        content.put(TratamentoAntiVetorial.NUMERO, tratamentoAntiVetorial.getNumero());
        content.put(TratamentoAntiVetorial.SEMANA_EPIDEMIOLOGICA, tratamentoAntiVetorial.getSemana());
        content.put(TratamentoAntiVetorial.USUARIO, tratamentoAntiVetorial.getUsuario().getId());
        content.put(TratamentoAntiVetorial.TIPO_ATIVIDADE, tratamentoAntiVetorial.getTipoAtividade());
        content.put(TratamentoAntiVetorial.TURMA, tratamentoAntiVetorial.getTurma());
        sqlLite.insert(TratamentoAntiVetorial.TABLE_NAME, null, content);
    }

    @Override
    public TratamentoAntiVetorial select(TratamentoAntiVetorial entity) throws Exception {
        return null;
    }

    @Override
    public void update(TratamentoAntiVetorial entity) throws Exception {

    }

    @Override
    public void delete(TratamentoAntiVetorial entity) throws Exception {

    }
}
