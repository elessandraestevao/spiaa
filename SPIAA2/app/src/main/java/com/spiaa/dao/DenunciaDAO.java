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

import javax.crypto.spec.DESedeKeySpec;

/**
 * Created by eless on 05/10/2015.
 */
public class DenunciaDAO implements BaseDAO<Denuncia> {
    Context context;

    public DenunciaDAO(Context context) {
        this.context = context;
    }

    @Override
    public void insert(Denuncia denuncia) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Denuncia.ID, denuncia.getId());
        content.put(Denuncia.BAIRRO, denuncia.getBairro().getId());
        content.put(Denuncia.ENDERECO, denuncia.getEndereco());
        content.put(Denuncia.IRREGULARIDADE, denuncia.getIrregularidade());
        content.put(Denuncia.NUMERO, denuncia.getNumero());
        content.put(Denuncia.STATUS, denuncia.getStatus());
        content.put(Denuncia.USUARIO, denuncia.getUsuario().getId());
        sqlLite.insert(Denuncia.TABLE_NAME, null, content);
    }

    @Override
    public Denuncia select(Denuncia entity) throws Exception {
        Denuncia denuncia = null;
        Cursor cursor = null;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        String where = Denuncia.ID + " = ?";

        String[] colunas = new String[]{Denuncia.ID, Denuncia.ENDERECO, Denuncia.NUMERO,
        Denuncia.IRREGULARIDADE, Denuncia.OBSERVACAO, Denuncia.STATUS, Denuncia.BAIRRO, Denuncia.USUARIO};
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
        return denuncia;
    }

    @Override
    public void update(Denuncia entity) throws Exception {

    }

    @Override
    public void delete(Denuncia entity) throws Exception {

    }
}
