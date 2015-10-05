package com.spiaa.modelo;

import com.spiaa.base.entity.BaseEntity;

/**
 * Created by eless on 03/10/2015.
 */
public class Bairro {
    //Constantes utilizadas para o Banco de dados
    public static final String TABLE_NAME = "bairro";
    public static final String ID = " _id";
    public static final String NOME = "nome";
    public static final String COORDENADAS = "coordenadas";

    //Atributos
    private String nome;
    private Long id;
    private String coordenadas;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
}
