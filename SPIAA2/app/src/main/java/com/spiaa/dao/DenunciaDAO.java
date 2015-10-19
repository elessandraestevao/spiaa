package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Bairro;
import com.spiaa.modelo.Denuncia;
import com.spiaa.modelo.Usuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by eless on 05/10/2015.
 */
public class DenunciaDAO implements BaseDAO<Denuncia> {
    Context context;

    public DenunciaDAO(Context context) {
        this.context = context;
    }

    @Override
    public Long insert(Denuncia denuncia) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(Denuncia.ID, denuncia.getId());
        content.put(Denuncia.BAIRRO, denuncia.getBairro().getId());
        content.put(Denuncia.ENDERECO, denuncia.getEndereco());
        content.put(Denuncia.IRREGULARIDADE, denuncia.getIrregularidade());
        content.put(Denuncia.NUMERO, denuncia.getNumero());
        content.put(Denuncia.STATUS, denuncia.getStatus());

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        content.put(Denuncia.DATA_ABERTURA, format.format(denuncia.getDataAbertura()));
        if (denuncia.getDataFinalizacao() != null) {
            content.put(Denuncia.DATA_FINALIZACAO, format.format(denuncia.getDataFinalizacao()));
        }
        content.put(Denuncia.USUARIO, denuncia.getUsuario().getId());

        Long retorno = sqlLite.insert(Denuncia.TABLE_NAME, null, content);
        sqlLite.close();
        return retorno;
    }

    @Override
    public Denuncia select(Denuncia entity) throws Exception {
        Denuncia denuncia = null;
        Cursor cursor = null;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        String where = Denuncia.ID + " = ?";

        String[] colunas = new String[]{Denuncia.ID, Denuncia.ENDERECO, Denuncia.NUMERO,
                Denuncia.IRREGULARIDADE, Denuncia.OBSERVACAO, Denuncia.STATUS, Denuncia.DATA_ABERTURA,
                Denuncia.DATA_FINALIZACAO, Denuncia.BAIRRO, Denuncia.USUARIO};
        String argumentos[] = new String[]{entity.getId().toString()};
        cursor = sqlLite.query(Denuncia.TABLE_NAME, colunas, where, argumentos, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            denuncia = new Denuncia();
            denuncia.setId(cursor.getLong(0));
            denuncia.setEndereco(cursor.getString(1));
            denuncia.setNumero(cursor.getInt(2));
            denuncia.setIrregularidade(cursor.getString(3));
            denuncia.setObservacao(cursor.getString(4));
            denuncia.setStatus(cursor.getString(5));

            try {
                //Bairro
                Bairro bairro = new Bairro();
                bairro.setId(cursor.getLong(6));
                denuncia.setBairro(new BairroDAO(context).select(bairro));
            } catch (Exception e) {
                Log.e("SPIAA", "Erro no SELECT de Bairro", e);
            }

            try {
                //Usuário
                Usuario usuario = new Usuario();
                usuario.setId(cursor.getLong(7));
                denuncia.setUsuario(new UsuarioDAO(context).select(usuario));
            } catch (Exception e) {
                Log.e("SPIAA", "Erro no SELECT de Usuário", e);
            }
            cursor.close();
        }
        sqlLite.close();
        return denuncia;
    }

    @Override
    public List<Denuncia> selectAll() throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        Cursor cursor = sqlLite.rawQuery("SELECT * FROM " + Denuncia.TABLE_NAME, null);
        List<Denuncia> denunciaList = null;

        if (cursor != null) {
            cursor.moveToFirst();
            denunciaList = new ArrayList<>();

            while (!cursor.isAfterLast()) {
                Denuncia denuncia = new Denuncia();
                denuncia.setId(cursor.getLong(0));
                denuncia.setEndereco(cursor.getString(1));
                denuncia.setNumero(cursor.getInt(2));
                denuncia.setIrregularidade(cursor.getString(3));
                denuncia.setObservacao(cursor.getString(4));
                denuncia.setStatus(cursor.getString(5));

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                denuncia.setDataAbertura(formatter.parse(cursor.getString(6)));

                try {
                    //Bairro
                    Bairro bairro = new Bairro();
                    bairro.setId(cursor.getLong(8));
                    denuncia.setBairro(new BairroDAO(context).select(bairro));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no SELECT de Bairro", e);
                }

                try {
                    //Usuário
                    Usuario usuario = new Usuario();
                    usuario.setId(cursor.getLong(9));
                    denuncia.setUsuario(new UsuarioDAO(context).select(usuario));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no SELECT de Usuário", e);
                }
                denunciaList.add(denuncia);
                cursor.moveToNext();
            }
            cursor.close();
        }
        sqlLite.close();
        return denunciaList;
    }

    public List<Denuncia> selectFinalizadas() throws Exception {
        List<Denuncia> denunciaList = null;
        Cursor cursor = null;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        String where = Denuncia.STATUS + " = ?";

        String[] colunas = new String[]{Denuncia.ID, Denuncia.ENDERECO, Denuncia.NUMERO,
                Denuncia.IRREGULARIDADE, Denuncia.OBSERVACAO, Denuncia.STATUS, Denuncia.DATA_ABERTURA,
                Denuncia.DATA_FINALIZACAO, Denuncia.BAIRRO, Denuncia.USUARIO};
        String argumentos[] = new String[]{"Finalizada"};
        cursor = sqlLite.query(Denuncia.TABLE_NAME, colunas, where, argumentos, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            denunciaList = new ArrayList<>();

            while (!cursor.isAfterLast()) {
                Denuncia denuncia = new Denuncia();
                denuncia.setId(cursor.getLong(0));
                denuncia.setEndereco(cursor.getString(1));
                denuncia.setNumero(cursor.getInt(2));
                denuncia.setIrregularidade(cursor.getString(3));
                denuncia.setObservacao(cursor.getString(4));
                denuncia.setStatus(cursor.getString(5));

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                denuncia.setDataAbertura(formatter.parse(cursor.getString(6)));
                denuncia.setDataFinalizacao(formatter.parse(cursor.getString(7)));

                try {
                    //Bairro
                    Bairro bairro = new Bairro();
                    bairro.setId(cursor.getLong(8));
                    denuncia.setBairro(new BairroDAO(context).select(bairro));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no SELECT de Bairro", e);
                }

                try {
                    //Usuário
                    Usuario usuario = new Usuario();
                    usuario.setId(cursor.getLong(9));
                    denuncia.setUsuario(new UsuarioDAO(context).select(usuario));
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no SELECT de Usuário", e);
                }
                denunciaList.add(denuncia);
                cursor.moveToNext();
            }
            cursor.close();
        }
        sqlLite.close();
        return denunciaList;
    }

    @Override
    public int update(Denuncia denuncia) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(Denuncia.ID, denuncia.getId());
        content.put(Denuncia.BAIRRO, denuncia.getBairro().getId());
        content.put(Denuncia.ENDERECO, denuncia.getEndereco());
        content.put(Denuncia.IRREGULARIDADE, denuncia.getIrregularidade());
        content.put(Denuncia.OBSERVACAO, denuncia.getObservacao());
        content.put(Denuncia.NUMERO, denuncia.getNumero());
        content.put(Denuncia.STATUS, denuncia.getStatus());

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        content.put(Denuncia.DATA_ABERTURA, format.format(denuncia.getDataAbertura()));
        content.put(Denuncia.DATA_FINALIZACAO, format.format(denuncia.getDataFinalizacao()));

        content.put(Denuncia.USUARIO, denuncia.getUsuario().getId());

        String where = Denuncia.ID + " = ?";
        String argumentos[] = new String[]{denuncia.getId().toString()};

        int retorno = sqlLite.update(Denuncia.TABLE_NAME, content, where, argumentos);
        sqlLite.close();
        return retorno;
    }

    @Override
    public int delete(Long id) throws Exception {
        return 0;
    }

    public boolean deleteFinalizadas(List<Denuncia> denunciaList) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        boolean retorno = false;
        String where = Denuncia.ID + " = ?";
        for (Denuncia denuncia : denunciaList) {
            String argumentos[] = new String[]{denuncia.getId().toString()};
            sqlLite.delete(Denuncia.TABLE_NAME, where, argumentos);
            retorno = true;
        }
        sqlLite.close();
        return retorno;
    }
}
