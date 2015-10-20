package com.spiaa.api;

import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by eless on 20/10/2015.
 */
public class APIManager {

    //INICIO SINGLETON
    private static APIManager instance = null;
    private static RestAdapter restAdapter = null;
    private static SpiaaService service = null;

    private APIManager() {
    }

    // singleton for RestAdapter
    public static SpiaaService getService() throws Exception {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://spiaa.herokuapp.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        service = restAdapter.create(SpiaaService.class);
        return service;
    }

    public static APIManager getInstance() {
        if (instance == null) {
            instance = new APIManager();
        }
        return instance;
    }

}
