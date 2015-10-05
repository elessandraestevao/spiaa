package com.spiaa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.dados.DatabaseHelper;
import com.spiaa.modelo.Bairro;
import com.spiaa.modelo.Criadouro;
import com.spiaa.modelo.Denuncia;
import com.spiaa.modelo.Inseticida;
import com.spiaa.modelo.Quarteirao;
import com.spiaa.modelo.TipoImoveis;
import com.spiaa.modelo.TratamentoAntiVetorial;

import java.util.Date;

/**
 * Created by eless on 04/10/2015.
 */
public class DatabaseController {
    private SQLiteDatabase db;
    private DatabaseHelper banco;
    private Context context;

    public DatabaseController(Context context) {
        this.banco = new DatabaseHelper(context);
        this.context = context;
    }

    public void insertBairro(Bairro bairro) {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Bairro.ID, bairro.getId());
        content.put(Bairro.NOME, bairro.getNome());
        content.put(Bairro.COORDENADAS, bairro.getCoordenadas());
        sqlLite.insert(Bairro.TABLE_NAME, null, content);
    }

    public void insertDenuncia(Denuncia denuncia) {
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

    public void insertCriadouro(Criadouro criadouro) {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Criadouro.ID, criadouro.getId());
        content.put(Criadouro.GRUPO, criadouro.getGrupo());
        content.put(Criadouro.RECIPIENTE, criadouro.getRecipiente());
        sqlLite.insert(Criadouro.TABLE_NAME, null, content);
    }

    public void insertQuarteirao(Quarteirao quarteirao) {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Quarteirao.ID, quarteirao.getId());
        content.put(Quarteirao.BAIRRO, quarteirao.getBairro().getId());
        content.put(Quarteirao.DESCRICAO, quarteirao.getDescricao());
        sqlLite.insert(Quarteirao.TABLE_NAME, null, content);
    }

    public void insertTipoImovel(TipoImoveis tipoImoveis) {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(TipoImoveis.ID, tipoImoveis.getId());
        content.put(TipoImoveis.DESCRICAO, tipoImoveis.getDescricao());
        content.put(TipoImoveis.SIGLA, tipoImoveis.getSigla());
        sqlLite.insert(TipoImoveis.TABLE_NAME, null, content);
    }

    public void insertInseticida(Inseticida inseticida) {
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Inseticida.ID, inseticida.getId());
        content.put(Inseticida.NOME, inseticida.getNome());
        content.put(Inseticida.UNIDADE, inseticida.getUnidade());
        sqlLite.insert(Inseticida.TABLE_NAME, null, content);
    }

    public void insertTratamentoAntiVetorial(TratamentoAntiVetorial tratamentoAntiVetorial) {
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
}
