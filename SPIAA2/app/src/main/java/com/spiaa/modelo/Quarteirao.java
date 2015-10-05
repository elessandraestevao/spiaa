package com.spiaa.modelo;

import com.spiaa.base.entity.BaseEntity;

/**
 * Created by eless on 03/10/2015.
 */
public class Quarteirao extends BaseEntity {
    //Constantes utilizadas para o Banco de dados
    public static final String TABLE_NAME = "quarteirao";
    public static final String ID = "_id";
    public static final String DESCRICAO = "descricao";
    public static final String BAIRRO = "bairro_fk";

    //Atributos
    private String Descricao;
    private Bairro bairro;

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }
}
