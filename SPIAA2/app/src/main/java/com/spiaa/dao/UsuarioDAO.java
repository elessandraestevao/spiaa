package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Criadouro;
import com.spiaa.modelo.Usuario;

/**
 * Created by eless on 05/10/2015.
 */
public class UsuarioDAO implements BaseDAO<Usuario> {
    Context context;

    public UsuarioDAO(Context context) {
        this.context = context;
    }

    @Override
    public void insert(Usuario usuario) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Usuario.ID, usuario.getId());
        content.put(Usuario.EMAIL, usuario.getEmail());
        content.put(Usuario.NOME, usuario.getNome());
        content.put(Usuario.NUMERO, usuario.getNumero());
        content.put(Usuario.TURMA, usuario.getTurma());
        content.put(Usuario.USUARIO, usuario.getUsuario());
        sqlLite.insert(Usuario.TABLE_NAME, null, content);
    }

    @Override
    public Usuario select(Usuario entity) throws Exception {
        return null;
    }

    @Override
    public void update(Usuario entity) throws Exception {

    }

    @Override
    public void delete(Usuario entity) throws Exception {

    }
}
