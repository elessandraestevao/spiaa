package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Atividade;
import com.spiaa.modelo.AtividadeCriadouro;
import com.spiaa.modelo.AtividadeInseticida;
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

        content.put(Atividade.ENDERECO, atividade.getEndereco());
        content.put(Atividade.NUMERO, atividade.getNumero());
        content.put(Atividade.OBSERVACAO, atividade.getObservacao());
        content.put(Atividade.INSPECIONADO, atividade.getInspecionado());
        content.put(Atividade.LATITUDE, atividade.getLatitude());
        content.put(Atividade.LONGITUDE, atividade.getLongitude());
        content.put(Atividade.TIPO_IMOVEL, atividade.getTipoImoveis().getId());
        content.put(Atividade.TRATAMENTO_ANTIVETORIAL, atividade.getBoletimDiario().getId());
        content.put(Atividade.QUARTEIRAO, atividade.getQuarteirao().getId());

        Long atividadeId = sqlLite.insert(Atividade.TABLE_NAME, null, content);


        if ((atividadeId != -1)) {
            if (atividade.getAtividadeCriadouroList() != null) {
                //Inserir lista de AtividadeCriadouros
                for (AtividadeCriadouro atividadeCriadouro : atividade.getAtividadeCriadouroList()) {
                    atividadeCriadouro.setIdAtividade(atividadeId);
                }
                try {
                    new AtividadeCriadouroDAO(context).insert(atividade.getAtividadeCriadouroList());
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro ao Inserir lista de AtividadeCriadouro", e);
                }
            }

            if (atividade.getAtividadeInseticidasList() != null) {
                //Inserir lista de AtividadeInseticidas
                for (AtividadeInseticida atividadeInseticida : atividade.getAtividadeInseticidasList()) {
                    atividadeInseticida.setIdAtividade(atividadeId);
                }
                try {
                    new AtividadeInseticidaDAO(context).insert(atividade.getAtividadeInseticidasList());
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro ao Inserir lista de AtividadeInseticida", e);
                }
            }
        }

        sqlLite.close();
        return atividadeId;
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

        if (cursor != null && cursor.getCount() > 0) {
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
        sqlLite.close();

        return atividade;
    }

    @Override
    public List<Atividade> selectAll() throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        Cursor cursor = sqlLite.rawQuery("SELECT * FROM " + Atividade.TABLE_NAME, null);
        List<Atividade> atividadeList = null;

        if (cursor != null && cursor.getCount() > 0) {
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
                cursor.moveToNext();
            }
            cursor.close();
        }
        sqlLite.close();

        return atividadeList;
    }

    public Long countAtividadesDoBoletim(Long id){
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        String where = Atividade.TRATAMENTO_ANTIVETORIAL + " = ?";
        String argumentos[] = new String[]{id.toString()};

        Long count = DatabaseUtils.queryNumEntries(sqlLite, Atividade.TABLE_NAME, where, argumentos);
        sqlLite.close();
        return count;
    }

    public List<Atividade> selectAllDoBoletim(Long id) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        Cursor cursor = sqlLite.rawQuery("SELECT * FROM " + Atividade.TABLE_NAME + " WHERE "
                + Atividade.TRATAMENTO_ANTIVETORIAL + " = " + id, null);
        List<Atividade> atividadeList =  new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

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

                try {
                    //Lista de AtividadeCriadouro
                    atividade.setAtividadeCriadouroList(new AtividadeCriadouroDAO(context).selectAllDaAtividade(cursor.getLong(0)));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no SELECT de AtividadeCraidouro", e);
                }

                try {
                    //Lista de AtividadeInseticida
                    atividade.setAtividadeInseticidasList(new AtividadeInseticidaDAO(context).selectAllDaAtividade(cursor.getLong(0)));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no SELECT de AtividadeInseticida", e);
                }

                atividadeList.add(atividade);
                cursor.moveToNext();
            }
            cursor.close();
        }
        sqlLite.close();

        return atividadeList;
    }

    @Override
    public int update(Atividade atividade) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();

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

        int retorno = sqlLite.update(Atividade.TABLE_NAME, content, where, argumentos);
        sqlLite.close();

        return retorno;
    }

    @Override
    public int delete(Long id) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();

        String where = Atividade.ID + " = ?";
        String argumentos[] = new String[]{id.toString()};

        int retorno = sqlLite.delete(Atividade.TABLE_NAME, where, argumentos);
        if(retorno == 1){
            //Deleta todas atividadesCriadouros desta atividade
            new AtividadeCriadouroDAO(context).delete(id);
            //Deleta todas atividadesInseticidas desta atividade
            new AtividadeInseticidaDAO(context).delete(id);
        }

        sqlLite.close();

        return retorno;
    }

    public int deleteAllDoBoletim(Long id) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        String where = Atividade.ID + " = ?";
        int retorno = 0;

        //Delete cascade AtividadeCriadouro e AtividadeInseticida
        AtividadeCriadouroDAO atividadeCriadouroDAO = new AtividadeCriadouroDAO(context);
        AtividadeInseticidaDAO atividadeInseticidaDAO = new AtividadeInseticidaDAO(context);
        List<Atividade> aux = selectAllDoBoletim(id);
        for (Atividade atividade: aux){
            String argumentos[] = new String[]{atividade.getId().toString()};
            if(sqlLite.delete(Atividade.TABLE_NAME, where, argumentos) == 1){
                //Deleta todas atividadesCriadouros desta atividade
                atividadeCriadouroDAO.delete(atividade.getId());

                //Deleta todas atividadesInseticidas desta atividade
                atividadeInseticidaDAO.delete(atividade.getId());

                retorno ++;
            }
        }
        sqlLite.close();

        return retorno;
    }
}
