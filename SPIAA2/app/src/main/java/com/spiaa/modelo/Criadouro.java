package com.spiaa.modelo;

import com.spiaa.base.BaseEntity;

/**
 * Created by eless on 03/10/2015.
 */
public class Criadouro extends BaseEntity{
    //Constantes utilizadas para o Banco de dados
    public static final String TABLE_NAME = "criadouro";
    public static final String ID = "_id";
    public static final String GRUPO = "grupo";
    public static final String RECIPIENTE = "recipiente";

    //Atributos
    private String grupo;
    private String recipiente ;

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getRecipiente() {
        return recipiente;
    }

    public void setRecipiente(String recipiente) {
        this.recipiente = recipiente;
    }
}
