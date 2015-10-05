package com.spiaa.modelo;

import com.spiaa.base.entity.BaseEntity;

/**
 * Created by eless on 03/10/2015.
 */
public class TipoImoveis extends BaseEntity {
    //Constantes utilizadas para o Banco de dados
    public static final String TABLE_NAME = "tipo_imovel";
    public static final String ID = "_id";
    public static final String SIGLA = "sigla";
    public static final String DESCRICAO = "descricao";

    //Atributos
    private String sigla;
    private String descricao;

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
