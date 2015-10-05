package com.spiaa.modelo;

import com.spiaa.base.entity.BaseEntity;

/**
 * Created by eless on 03/10/2015.
 */
public class Inseticida extends BaseEntity{
    //Constantes utilizadas para o Banco de dados
    public static final String TABLE_NAME = "inseticida";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String UNIDADE = "unidade";

    //Atributos
    private String nome;
    private String unidade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
}
