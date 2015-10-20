package com.spiaa.api;

import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by eless on 20/10/2015.
 */
public class APIManagerDenuncia {
    //INICIO SINGLETON
    private static APIManagerDenuncia instance = null;
    private static RestAdapter restAdapter = null;
    private static SpiaaService service = null;

    private APIManagerDenuncia() {
    }

    // singleton for RestAdapter
    public static SpiaaService getService() throws Exception {
        if (restAdapter == null) {
            synchronized (APIManager.class) {
                if (restAdapter == null) {
                    restAdapter = new RestAdapter.Builder()
                            .setEndpoint("http://spiaa.herokuapp.com")
                            .setLogLevel(RestAdapter.LogLevel.FULL)
                            .setConverter(new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd")
                                    .create()))
                            .build();
                }
            }
        }
        service = restAdapter.create(SpiaaService.class);
        return service;
    }

    public static APIManagerDenuncia getInstance() {
        if (instance == null) {
            instance = new APIManagerDenuncia();
        }
        return instance;
    }

}
