package com.spiaa.modelo;

import com.spiaa.base.BaseEntity;

/**
 * Created by eless on 29/09/2015.
 */
public class Usuario extends BaseEntity {
    //Constantes utilizadas para o Banco de dados
    public static final String TABLE_NAME = "usuario";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String USUARIO = "usuario";
    public static final String SENHA = "senha";
    public static final String EMAIL = "email";
    public static final String TIPO = "tipo";
    public static final String NUMERO = "numero";
    public static final String TURMA = "turma";


    //Atributos
    private String nome;
    private String email;
    private String usuario;
    private String senha;
    private String tipo;
    private String numero;
    private String turma;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

}
