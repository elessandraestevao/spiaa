package com.spiaa.modelo;

import com.spiaa.base.entity.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by eless on 22/09/2015.
 */
public class TratamentoAntiVetorial extends BaseEntity{
    //Constantes utilizadas para o Banco de dados
    public static final String TABLE_NAME = "tratamento_antivetorial";
    public static final String ID = "_id";
    public static final String DATA = "data_boletim";
    public static final String NUMERO = "numero";
    public static final String SEMANA_EPIDEMIOLOGICA = "semana_epidemiologica";
    public static final String NUMERO_ATIVIDADE = "numero_atividade";
    public static final String TIPO_ATIVIDADE = "tipo_atividade";
    public static final String TURMA = "turma";
    public static final String USUARIO = "usuario_fk";
    public static final String BAIRRO = "bairro_fk";

    //Atributos
    Bairro bairro;
    String categoria;
    Usuario usuario;
    String numero;
    String turma;
    Date dataBoletim;
    String tipoAtividade;
    String status;
    String semana;
    String numeroAtividade;
    List<Atividade> atividadeList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public Date getData() {
        return dataBoletim;
    }

    public void setData(Date data) {
        this.dataBoletim = data;
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public List<Atividade> getAtividades() {
        return atividadeList;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividadeList = atividades;
    }

    public String getNumeroAtividade() {
        return numeroAtividade;
    }

    public void setNumeroAtividade(String numeroAtividade) {
        this.numeroAtividade = numeroAtividade;
    }

    public String getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(String tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }
}
