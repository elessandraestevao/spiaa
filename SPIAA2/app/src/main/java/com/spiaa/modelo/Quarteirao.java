package com.spiaa.modelo;

import com.spiaa.base.BaseEntity;

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
    private String descricao;
    private Bairro bairro;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {

        this.descricao = descricao;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }
}
