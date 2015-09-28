package com.spiaa.modelo;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by eless on 22/09/2015.
 */
public class Boletim {
    String bairro;
    String categoria;
    String agente;
    String numero;
    String turma;
    String data;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSemanaEpidemiologia() {
        return semanaEpidemiologia;
    }

    public void setSemanaEpidemiologia(String semanaEpidemiologia) {
        this.semanaEpidemiologia = semanaEpidemiologia;
    }

    String semanaEpidemiologia;
}
