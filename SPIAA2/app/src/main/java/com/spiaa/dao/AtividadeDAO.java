package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Atividade;
import com.spiaa.modelo.Quarteirao;
import com.spiaa.modelo.TipoImoveis;
import com.spiaa.modelo.TratamentoAntiVetorial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eless on 05/10/2015.
 */
public class AtividadeDAO implements BaseDAO<Atividade> {
    Context context;

    public AtividadeDAO(Context context) {
        this.context = context;
    }

    @Override
    public Long insert(Atividade atividade) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(Atividade.ID, atividade.getId());
        content.put(Atividade.ENDERECO, atividade.getEndereco());
        content.put(Atividade.INSPECIONADO, atividade.getInspecionado());
        content.put(Atividade.LATITUDE, atividade.getLatitude());
        content.put(Atividade.LONGITUDE, atividade.getLongitude());
        content.put(Atividade.NUMERO, atividade.getNumero());
        content.put(Atividade.OBSERVACAO, atividade.getObservacao());
        content.put(Atividade.QUARTEIRAO, atividade.getQuarteirao().getId());
        content.put(Atividade.TIPO_IMOVEL, atividade.getTipoImoveis().getId());
        content.put(Atividade.TRATAMENTO_ANTIVETORIAL, atividade.getBoletimDiario().getId());

        return sqlLite.insert(Atividade.TABLE_NAME, null, content);
    }

    @Override
    public Atividade select(Atividade entity) throws Exception {
        Atividade atividade = null;
        Cursor cursor = null;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        String where = Atividade.ID + " = ?";

        String[] colunas = new String[]{Atividade.ID, Atividade.ENDERECO, Atividade.NUMERO,
                Atividade.OBSERVACAO, Atividade.INSPECIONADO, Atividade.LATITUDE, Atividade.LONGITUDE,
                Atividade.TIPO_IMOVEL, Atividade.TRATAMENTO_ANTIVETORIAL, Atividade.QUARTEIRAO};
        String argumentos[] = new String[]{entity.getId().toString()};
        cursor = sqlLite.query(Atividade.TABLE_NAME, colunas, where, argumentos, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            atividade = new Atividade();
            atividade.setId(cursor.getLong(0));
            atividade.setEndereco(cursor.getString(1));
            atividade.setNumero(cursor.getString(2));
            atividade.setObservacao(cursor.getString(3));
            atividade.setInspecionado(cursor.getInt(4));
            atividade.setLatitude(cursor.getString(5));
            atividade.setLongitude(cursor.getString(6));

            try {
                //TipoImóveis
                TipoImoveis tp = new TipoImoveis();
                tp.setId(cursor.getLong(7));
                atividade.setTipoImoveis(new TipoImoveisDAO(context).select(tp));
            } catch (Exception e) {
                Log.e("SPIAA", "Erro no SELECT de TipoImóvel", e);
            }

            try {
                //Tratamento anti vetorial
                TratamentoAntiVetorial tav = new TratamentoAntiVetorial();
                tav.setId(cursor.getLong(8));
                atividade.setBoletimDiario(new TratamentoAntiVetorialDAO(context).select(tav));
            } catch (Exception e) {
                Log.e("SPIAA", "Erro no SELECT TratamentoAntiVetorial", e);
            }

            try {
                //Quarteirão
                Quarteirao q = new Quarteirao();
                q.setId(cursor.getLong(9));
                atividade.setQuarteirao(new QuarteiraoDAO(context).select(q));
            } catch (Exception e) {
                Log.e("SPIAA", "Erro no SELECT de Quarteirão", e);
            }
            cursor.close();
        }
        return atividade;
    }

    @Override
    public List<Atividade> selectAll() throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        Cursor cursor = sqlLite.rawQuery("SELECT * FROM " + Atividade.TABLE_NAME, null);
        List<Atividade> atividadeList = null;

        if (cursor != null) {
            cursor.moveToFirst();
            atividadeList = new ArrayList<>();

            while (!cursor.isAfterLast()) {
                Atividade atividade = new Atividade();
                atividade.setId(cursor.getLong(0));
                atividade.setEndereco(cursor.getString(1));
                atividade.setNumero(cursor.getString(2));
                atividade.setObservacao(cursor.getString(3));
                atividade.setInspecionado(cursor.getInt(4));
                atividade.setLatitude(cursor.getString(5));
                atividade.setLongitude(cursor.getString(6));

                try {
                    //TipoImóveis
                    TipoImoveis tp = new TipoImoveis();
                    tp.setId(cursor.getLong(7));
                    atividade.setTipoImoveis(new TipoImoveisDAO(context).select(tp));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no SELECT de TipoImóvel", e);
                }

                try {
                    //Tratamento anti vetorial
                    TratamentoAntiVetorial tav = new TratamentoAntiVetorial();
                    tav.setId(cursor.getLong(8));
                    atividade.setBoletimDiario(new TratamentoAntiVetorialDAO(context).select(tav));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no SELECT TratamentoAntiVetorial", e);
                }

                try {
                    //Quarteirão
                    Quarteirao q = new Quarteirao();
                    q.setId(cursor.getLong(9));
                    atividade.setQuarteirao(new QuarteiraoDAO(context).select(q));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no SELECT de Quarteirão", e);
                }
                atividadeList.add(atividade);
            }
            cursor.close();
        }
        return atividadeList;
    }

    @Override
    public int update(Atividade atividade) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(Atividade.ID, atividade.getId());
        content.put(Atividade.ENDERECO, atividade.getEndereco());
        content.put(Atividade.INSPECIONADO, atividade.getInspecionado());
        content.put(Atividade.LATITUDE, atividade.getLatitude());
        content.put(Atividade.LONGITUDE, atividade.getLongitude());
        content.put(Atividade.NUMERO, atividade.getNumero());
        content.put(Atividade.OBSERVACAO, atividade.getObservacao());
        content.put(Atividade.QUARTEIRAO, atividade.getQuarteirao().getId());
        content.put(Atividade.TIPO_IMOVEL, atividade.getTipoImoveis().getId());
        content.put(Atividade.TRATAMENTO_ANTIVETORIAL, atividade.getBoletimDiario().getId());

        String where = Atividade.ID + " = ?";
        String argumentos[] = new String[]{atividade.getId().toString()};

        return sqlLite.update(Atividade.TABLE_NAME, content, where, argumentos);
    }

    @Override
    public int delete(Long id) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();

        String where = Atividade.ID + " = ?";
        String argumentos[] = new String[]{id.toString()};

        return sqlLite.delete(Atividade.TABLE_NAME, where, argumentos);
    }
}
