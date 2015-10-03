package com.spiaa.modelo;

import com.spiaa.base.entity.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by eless on 22/09/2015.
 */
public class TratamentoAntiVetorial extends BaseEntity{
    Bairro bairro;
    String categoria;
    Usuario usuario;
    String numero;
    String turma;
    Date dataBoletim;
    String status;
    String semana;
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
}
