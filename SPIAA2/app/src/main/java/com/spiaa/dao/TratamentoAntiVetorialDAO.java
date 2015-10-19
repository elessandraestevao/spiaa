package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Atividade;
import com.spiaa.modelo.Bairro;
import com.spiaa.modelo.TratamentoAntiVetorial;
import com.spiaa.modelo.Usuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eless on 05/10/2015.
 */
public class TratamentoAntiVetorialDAO implements BaseDAO<TratamentoAntiVetorial> {
    Context context;

    public TratamentoAntiVetorialDAO(Context context) {
        this.context = context;
    }

    @Override
    public Long insert(TratamentoAntiVetorial tratamentoAntiVetorial) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(TratamentoAntiVetorial.BAIRRO, tratamentoAntiVetorial.getBairro().getId());

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        content.put(TratamentoAntiVetorial.DATA, format.format(tratamentoAntiVetorial.getData()));

        content.put(TratamentoAntiVetorial.NUMERO_ATIVIDADE, tratamentoAntiVetorial.getNumeroAtividade());
        content.put(TratamentoAntiVetorial.NUMERO, tratamentoAntiVetorial.getNumero());
        content.put(TratamentoAntiVetorial.SEMANA_EPIDEMIOLOGICA, tratamentoAntiVetorial.getSemana());
        content.put(TratamentoAntiVetorial.USUARIO, tratamentoAntiVetorial.getUsuario().getId());
        content.put(TratamentoAntiVetorial.TIPO_ATIVIDADE, tratamentoAntiVetorial.getTipoAtividade());
        content.put(TratamentoAntiVetorial.TURMA, tratamentoAntiVetorial.getTurma());
        content.put(TratamentoAntiVetorial.STATUS, tratamentoAntiVetorial.getStatus());

        Long retorno = sqlLite.insert(TratamentoAntiVetorial.TABLE_NAME, null, content);
        sqlLite.close();

        return retorno;
    }

    @Override
    public TratamentoAntiVetorial select(TratamentoAntiVetorial entity) throws Exception {
        TratamentoAntiVetorial tratamentoAntiVetorial = null;
        Cursor cursor = null;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        String where = TratamentoAntiVetorial.ID + " = ?";

        String[] colunas = new String[]{TratamentoAntiVetorial.ID, TratamentoAntiVetorial.DATA,
                TratamentoAntiVetorial.NUMERO, TratamentoAntiVetorial.SEMANA_EPIDEMIOLOGICA,
                TratamentoAntiVetorial.NUMERO_ATIVIDADE, TratamentoAntiVetorial.TIPO_ATIVIDADE,
                TratamentoAntiVetorial.TURMA, TratamentoAntiVetorial.USUARIO, TratamentoAntiVetorial.BAIRRO};
        String argumentos[] = new String[]{entity.getId().toString()};
        cursor = sqlLite.query(TratamentoAntiVetorial.TABLE_NAME, colunas, where, argumentos, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            tratamentoAntiVetorial = new TratamentoAntiVetorial();
            tratamentoAntiVetorial.setId(cursor.getLong(0));

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            tratamentoAntiVetorial.setData(formatter.parse(cursor.getString(1)));

            tratamentoAntiVetorial.setNumero(cursor.getString(2));
            tratamentoAntiVetorial.setSemana(cursor.getString(3));
            tratamentoAntiVetorial.setNumeroAtividade(cursor.getString(4));
            tratamentoAntiVetorial.setTipoAtividade(cursor.getString(5));
            tratamentoAntiVetorial.setTurma(cursor.getString(6));

            try {
                //Usuário
                Usuario usuario = new Usuario();
                usuario.setId((long) cursor.getInt(7));
                tratamentoAntiVetorial.setUsuario(new UsuarioDAO(context).select(usuario));
            } catch (Exception e) {
                Log.e("SPIAA", "Erro no SELECT de Usuário", e);
            }

            try {
                //Bairro
                Bairro bairro = new Bairro();
                bairro.setId(cursor.getLong(8));
                tratamentoAntiVetorial.setBairro(new BairroDAO(context).select(bairro));
            } catch (Exception e) {
                Log.e("SPIAA", "Erro no SELECT de Bairro", e);
            }
            cursor.close();
        }
        sqlLite.close();

        return tratamentoAntiVetorial;
    }

    @Override
    public List<TratamentoAntiVetorial> selectAll() throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        Cursor cursor = sqlLite.rawQuery("SELECT * FROM " + TratamentoAntiVetorial.TABLE_NAME, null);
        List<TratamentoAntiVetorial> tratamentoAntiVetorialList = null;

        if (cursor != null) {
            cursor.moveToFirst();
            tratamentoAntiVetorialList = new ArrayList<>();

            while (!cursor.isAfterLast()) {
                TratamentoAntiVetorial tratamentoAntiVetorial = new TratamentoAntiVetorial();
                tratamentoAntiVetorial.setId(cursor.getLong(0));

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                tratamentoAntiVetorial.setData(formatter.parse(cursor.getString(1)));
                tratamentoAntiVetorial.setNumero(cursor.getString(2));
                tratamentoAntiVetorial.setSemana(cursor.getString(3));
                tratamentoAntiVetorial.setNumeroAtividade(cursor.getString(4));
                tratamentoAntiVetorial.setTipoAtividade(cursor.getString(5));
                tratamentoAntiVetorial.setTurma(cursor.getString(6));
                tratamentoAntiVetorial.setStatus(cursor.getString(7));

                try {
                    //Usuário
                    Usuario usuario = new Usuario();
                    usuario.setId(cursor.getLong(8));
                    tratamentoAntiVetorial.setUsuario(new UsuarioDAO(context).select(usuario));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no SELECT de Usuário", e);
                }

                try {
                    //Bairro
                    Bairro bairro = new Bairro();
                    bairro.setId(cursor.getLong(9));
                    tratamentoAntiVetorial.setBairro(new BairroDAO(context).select(bairro));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no SELECT de Bairro", e);
                }
                tratamentoAntiVetorialList.add(tratamentoAntiVetorial);
                cursor.moveToNext();
            }
            cursor.close();
        }
        sqlLite.close();
        return tratamentoAntiVetorialList;
    }

    public List<TratamentoAntiVetorial> selectAllConcluidos() throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();

        Cursor cursor = sqlLite.rawQuery("SELECT * FROM " + TratamentoAntiVetorial.TABLE_NAME + " WHERE "
                + TratamentoAntiVetorial.STATUS + " = 'Concluído'", null);

        List<TratamentoAntiVetorial> tratamentoAntiVetorialList = null;

        if (cursor != null) {
            cursor.moveToFirst();
            tratamentoAntiVetorialList = new ArrayList<>();

            while (!cursor.isAfterLast()) {
                TratamentoAntiVetorial tratamentoAntiVetorial = new TratamentoAntiVetorial();
                tratamentoAntiVetorial.setId(cursor.getLong(0));

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                tratamentoAntiVetorial.setData(formatter.parse(cursor.getString(1)));
                tratamentoAntiVetorial.setNumero(cursor.getString(2));
                tratamentoAntiVetorial.setSemana(cursor.getString(3));
                tratamentoAntiVetorial.setNumeroAtividade(cursor.getString(4));
                tratamentoAntiVetorial.setTipoAtividade(cursor.getString(5));
                tratamentoAntiVetorial.setTurma(cursor.getString(6));
                tratamentoAntiVetorial.setStatus(cursor.getString(7));

                try {
                    //Usuário
                    Usuario usuario = new Usuario();
                    usuario.setId(cursor.getLong(8));
                    tratamentoAntiVetorial.setUsuario(new UsuarioDAO(context).select(usuario));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no SELECT de Usuário", e);
                }

                try {
                    //Bairro
                    Bairro bairro = new Bairro();
                    bairro.setId(cursor.getLong(9));
                    tratamentoAntiVetorial.setBairro(new BairroDAO(context).select(bairro));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no SELECT de Bairro", e);
                }

                try {
                    //Atividades
                    tratamentoAntiVetorial.setAtividades(new AtividadeDAO(context).selectAllDoBoletim(cursor.getLong(0)));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no SELECT de Bairro", e);
                }

                tratamentoAntiVetorialList.add(tratamentoAntiVetorial);
                cursor.moveToNext();
            }
            cursor.close();
        }
        sqlLite.close();

        return tratamentoAntiVetorialList;
    }

    @Override
    public int update(TratamentoAntiVetorial tratamentoAntiVetorial) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(TratamentoAntiVetorial.SEMANA_EPIDEMIOLOGICA, tratamentoAntiVetorial.getSemana());
        content.put(TratamentoAntiVetorial.STATUS, tratamentoAntiVetorial.getStatus());

        String where = TratamentoAntiVetorial.ID + " = ?";
        String argumentos[] = new String[]{tratamentoAntiVetorial.getId().toString()};

        int retorno = sqlLite.update(TratamentoAntiVetorial.TABLE_NAME, content, where, argumentos);
        sqlLite.close();

        return retorno;
    }

    @Override
    public int delete(Long id) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();

        String where = TratamentoAntiVetorial.ID + " = ?";
        String argumentos[] = new String[]{id.toString()};

        int retorno = sqlLite.delete(TratamentoAntiVetorial.TABLE_NAME, where, argumentos);
        sqlLite.close();

        return retorno;
    }
}
