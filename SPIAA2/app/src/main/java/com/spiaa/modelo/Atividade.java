package com.spiaa.modelo;

import com.spiaa.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by eless on 26/09/2015.
 */
public class Atividade extends BaseEntity implements Serializable{
    //Constantes utilizadas para o Banco de dados
    public static final String TABLE_NAME = "atividade";
    public static final String ID = "_id";
    public static final String ENDERECO = "endereco";
    public static final String NUMERO = "numero";
    public static final String OBSERVACAO = "observacao";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String INSPECIONADO = "inspecionado";
    public static final String TIPO_IMOVEL = "tipo_imovel_fk";
    public static final String TRATAMENTO_ANTIVETORIAL = "tratamento_antivetorial_fk";
    public static final String QUARTEIRAO = "quarteirao_fk";
    public static final String DATA_INICIAL = "data_inicial";
    public static final String DATA_FINAL = "data_final";

    //Atributos
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
    String titulo;
    Date dataInicial;
    Date dataFinal;

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }
}
