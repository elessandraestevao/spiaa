package com.spiaa.modelo;

/**
 * Created by eless on 26/09/2015.
 */
public class Atividade {
    String atividade;
    String numero;
    String tipo;
    String tdf;
    String numero_quarteirao;
    String endereco;
    String total_inspecionado;
    String tipo_unidade;
    String larvicida;
    Boletim boletim;

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTdf() {
        return tdf;
    }

    public void setTdf(String tdf) {
        this.tdf = tdf;
    }

    public String getNumero_quarteirao() {
        return numero_quarteirao;
    }

    public void setNumero_quarteirao(String numero_quarteirao) {
        this.numero_quarteirao = numero_quarteirao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTotal_inspecionado() {
        return total_inspecionado;
    }

    public void setTotal_inspecionado(String total_inspecionado) {
        this.total_inspecionado = total_inspecionado;
    }

    public String getTipo_unidade() {
        return tipo_unidade;
    }

    public void setTipo_unidade(String tipo_unidade) {
        this.tipo_unidade = tipo_unidade;
    }

    public String getLarvicida() {
        return larvicida;
    }

    public void setLarvicida(String larvicida) {
        this.larvicida = larvicida;
    }

    public String getAdulticida() {
        return adulticida;
    }

    public void setAdulticida(String adulticida) {
        this.adulticida = adulticida;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    String adulticida;
    String observacoes;
}
