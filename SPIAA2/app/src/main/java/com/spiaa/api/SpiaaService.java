package com.spiaa.api;

import com.google.gson.Gson;
import com.spiaa.modelo.Usuario;

import org.json.JSONObject;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by eless on 29/09/2015.
 */
public interface SpiaaService {
    @POST("/agente/login")
    void login(@Body Usuario usuario, Callback<Usuario> callback);

    @POST("/agente/login/list")
    void loginList(@Body List<Usuario> usuarioList, Callback<String> callback);

    @GET("/agente")
    void loginGet(Callback<Usuario> callback);
}
