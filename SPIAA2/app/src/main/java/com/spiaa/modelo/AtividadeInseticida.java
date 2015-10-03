package com.spiaa.modelo;

import com.spiaa.base.entity.BaseEntity;

/**
 * Created by eless on 03/10/2015.
 */
public class AtividadeInseticida extends BaseEntity {
    private Inseticida inseticida;
    private Integer quantidadeInseticida;

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
