package com.spiaa.modelo;

import com.spiaa.base.BaseEntity;

/**
 * Created by eless on 03/10/2015.
 */
public class Bairro extends BaseEntity{
    //Constantes utilizadas para o Banco de dados
    public static final String TABLE_NAME = "bairro";
    public static final String ID = " _id";
    public static final String NOME = "nome";
    public static final String COORDENADAS = "coordenadas";

    //Atributos
    private String nome;
    private String coordenadas;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
}
