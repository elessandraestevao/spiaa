package com.spiaa.modelo;

/**
 * Created by eless on 18/09/2015.
 */
public class Denuncia {
    String endereco;
    int numero;
    String tipo_irregularidades;
    String status;
    String observacao;
    String bairro;

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTipo_irregularidades() {
        return tipo_irregularidades;
    }

    public void setTipo_irregularidades(String tipo_irregularidades) {
        this.tipo_irregularidades = tipo_irregularidades;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
