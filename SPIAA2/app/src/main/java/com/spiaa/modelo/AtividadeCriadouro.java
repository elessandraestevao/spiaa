package com.spiaa.modelo;

import com.spiaa.base.BaseEntity;

/**
 * Created by eless on 03/10/2015.
 */
public class AtividadeCriadouro extends BaseEntity{
    //Constantes utilizadas para o Banco de dados
    public static final String TABLE_NAME = "atividade_criadouro";
    public static final String ATIVIDADE = "atividade_fk";
    public static final String CRIADOURO = "criadouro_fk";
    public static final String QUANTIDADE = "quantidade";

    //Atributos
    private Atividade atividade;
    private Criadouro criadouro;
    private Integer quantidadeCriadouro;

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    public Criadouro getCriadouro() {
        return criadouro;
    }

    public void setCriadouro(Criadouro criadouro) {
        this.criadouro = criadouro;
    }

    public Integer getQuantidadeCriadouro() {
        return quantidadeCriadouro;
    }

    public void setQuantidadeCriadouro(Integer quantidadeCriadouro) {
        this.quantidadeCriadouro = quantidadeCriadouro;
    }
}
