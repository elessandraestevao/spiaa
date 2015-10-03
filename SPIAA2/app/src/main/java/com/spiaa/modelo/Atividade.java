package com.spiaa.modelo;

import com.spiaa.base.entity.BaseEntity;

import java.util.List;

/**
 * Created by eless on 26/09/2015.
 */
public class Atividade extends BaseEntity {
    String endereco;
    Quarteirao quarteirao;
    String numero;
    String observacao;
    Integer inspecionado;
    TipoImoveis tipoImoveis;
    List<AtividadeCriadouro> atividadeCriadouroList;
    List<AtividadeInseticida> atividadeInseticidasList;
    TratamentoAntiVetorial boletimDiario;
    String latitude;
    String longitude;

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Quarteirao getQuarteirao() {
        return quarteirao;
    }

    public void setQuarteirao(Quarteirao quarteirao) {
        this.quarteirao = quarteirao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getInspecionado() {
        return inspecionado;
    }

    public void setInspecionado(Integer inspecionado) {
        this.inspecionado = inspecionado;
    }

    public TipoImoveis getTipoImoveis() {
        return tipoImoveis;
    }

    public void setTipoImoveis(TipoImoveis tipoImoveis) {
        this.tipoImoveis = tipoImoveis;
    }

    public List<AtividadeCriadouro> getAtividadeCriadouroList() {
        return atividadeCriadouroList;
    }

    public void setAtividadeCriadouroList(List<AtividadeCriadouro> atividadeCriadouroList) {
        this.atividadeCriadouroList = atividadeCriadouroList;
    }

    public List<AtividadeInseticida> getAtividadeInseticidasList() {
        return atividadeInseticidasList;
    }

    public void setAtividadeInseticidasList(List<AtividadeInseticida> atividadeInseticidasList) {
        this.atividadeInseticidasList = atividadeInseticidasList;
    }

    public TratamentoAntiVetorial getBoletimDiario() {
        return boletimDiario;
    }

    public void setBoletimDiario(TratamentoAntiVetorial boletimDiario) {
        this.boletimDiario = boletimDiario;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
