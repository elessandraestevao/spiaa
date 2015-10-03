package com.spiaa.modelo;

import com.spiaa.base.entity.BaseEntity;

/**
 * Created by eless on 03/10/2015.
 */
public class AtividadeCriadouro {
    private Criadouro criadouro;
    private Integer quantidadeCriadouro;

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
