package com.spiaa.modelo;


import com.spiaa.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by eless on 18/09/2015.
 */
public class Denuncia extends BaseEntity implements Serializable{
    //Constantes utilizadas para o Banco de dados
    public static final String TABLE_NAME = "denuncia";
    public static final String ID = "_id";
    public static final String ENDERECO = "endereco";
    public static final String NUMERO = "numero";
    public static final String IRREGULARIDADE = "irregularidade";
    public static final String OBSERVACAO = "observacao";
    public static final String STATUS = "status";
    public static final String DATA_ABERTURA = "data_abertura";
    public static final String DATA_FINALIZACAO = "data_finalizacao";
    public static final String BAIRRO = "bairro_fk";
    public static final String USUARIO = "usuario_fk";

    //Atributos
    String endereco;
    int numero;
    String irregularidade;
    String status;
    String observacao;
    Bairro bairro;
    Usuario usuario;
    Date dataAbertura;
    Date dataFinalizacao;
    String titulo;

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

    public String getIrregularidade() {
        return irregularidade;
    }

    public void setIrregularidade(String irregularidade) {
        this.irregularidade = irregularidade;
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

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(Date dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }
}
