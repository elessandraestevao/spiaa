package com.spiaa.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.base.BaseDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Criadouro;
import com.spiaa.modelo.Usuario;

import java.util.List;

/**
 * Created by eless on 05/10/2015.
 */
public class UsuarioDAO implements BaseDAO<Usuario> {
    Context context;

    public UsuarioDAO(Context context) {
        this.context = context;
    }

    @Override
    public Long insert(Usuario usuario) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(Usuario.ID, usuario.getId());
        content.put(Usuario.EMAIL, usuario.getEmail());
        content.put(Usuario.NOME, usuario.getNome());
        content.put(Usuario.NUMERO, usuario.getNumero());
        content.put(Usuario.TURMA, usuario.getTurma());
        content.put(Usuario.USUARIO, usuario.getUsuario());

        return sqlLite.insert(Usuario.TABLE_NAME, null, content);
    }

    @Override
    public Usuario select(Usuario entity) throws Exception {
        Usuario usuario = null;
        Cursor cursor = null;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getReadableDatabase();
        String where = Usuario.ID + " = ?";

        String[] colunas = new String[]{Usuario.ID, Usuario.NOME, Usuario.USUARIO, Usuario.EMAIL,
        Usuario.TIPO, Usuario.NUMERO, Usuario.TURMA};
        String argumentos[] = new String[]{entity.getId().toString()};
        cursor = sqlLite.query(Usuario.TABLE_NAME, colunas, where, argumentos, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            usuario = new Usuario();
            usuario.setId(cursor.getLong(0));
            usuario.setNome(cursor.getString(1));
            usuario.setUsuario(cursor.getString(2));
            usuario.setEmail(cursor.getString(3));
            usuario.setTipo(cursor.getString(4));
            usuario.setNumero(cursor.getString(5));
            usuario.setTurma(cursor.getString(6));
            cursor.close();
        }
        return usuario;
    }

    @Override
    public List<Usuario> selectAll() throws Exception {
        return null;
    }

    @Override
    public int update(Usuario usuario) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(Usuario.ID, usuario.getId());
        content.put(Usuario.EMAIL, usuario.getEmail());
        content.put(Usuario.NOME, usuario.getNome());
        content.put(Usuario.NUMERO, usuario.getNumero());
        content.put(Usuario.TURMA, usuario.getTurma());
        content.put(Usuario.USUARIO, usuario.getUsuario());

        String where = Usuario.ID + " = ?";
        String argumentos[] = new String[]{usuario.getId().toString()};

        return sqlLite.update(Usuario.TABLE_NAME, content, where, argumentos);
    }

    @Override
    public int delete(Long id) throws Exception {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();

        String where = Usuario.ID + " = ?";
        String argumentos[] = new String[] { id.toString() };

        return sqlLite.delete(Usuario.TABLE_NAME, where, argumentos);
    }
}
