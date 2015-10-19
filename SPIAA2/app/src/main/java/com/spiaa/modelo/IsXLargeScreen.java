package com.spiaa.modelo;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

/**
 * Created by eless on 30/09/2015.
 */
public class IsXLargeScreen {
    Context context;
    boolean tela;

    //Verifica se está em tablet ou não
    public static boolean isXLargeScreen(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
