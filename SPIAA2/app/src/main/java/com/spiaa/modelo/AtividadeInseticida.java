package com.spiaa.modelo;

import com.spiaa.base.BaseEntity;

/**
 * Created by eless on 03/10/2015.
 */
public class AtividadeInseticida extends BaseEntity {
    //Constantes utilizadas para o Banco de dados
    public static final String TABLE_NAME = "atividade_inseticida";
    public static final String ATIVIDADE = "atividade_fk";
    public static final String INSETICIDA = "inseticida_fk";
    public static final String QUANTIDADE = "quantidade";

    //Atributos
    private Long idAtividade;
    private Inseticida inseticida;
    private Integer quantidadeInseticida;

    public Long getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(Long idAtividade) {
        this.idAtividade = idAtividade;
    }

    public Inseticida getInseticida() {
        return inseticida;
    }

    public void setInseticida(Inseticida inseticida) {
        this.inseticida = inseticida;
    }

    public Integer getQuantidadeInseticida() {
        return quantidadeInseticida;
    }

    public void setQuantidadeInseticida(Integer quantidadeInseticida) {
        this.quantidadeInseticida = quantidadeInseticida;
    }
}
