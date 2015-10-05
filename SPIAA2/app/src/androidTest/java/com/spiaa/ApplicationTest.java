package com.spiaa;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.widget.Toast;

import com.spiaa.dados.DatabaseHelper;
import com.spiaa.dao.BairroDAO;
import com.spiaa.modelo.Bairro;

import junit.framework.Assert;
import junit.framework.Test;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testCreateDatabase(){
        //Cria banco de dados da aplicação ao fazer o login pela primeira vez
        new DatabaseHelper(getContext());
    }

    public void testInsertBairro(){
        BairroDAO dao = new BairroDAO(getContext());
        Bairro bairro = new Bairro();
        bairro.setId(19L);
        bairro.setCoordenadas("12345;67890");
        bairro.setNome("Bairro agente");
        try {
            dao.insert(bairro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testSelectBairros(){
        BairroDAO dao = new BairroDAO(getContext());
        Bairro bairro = new Bairro();
        Bairro bairro2 = new Bairro();
        bairro.setId(19L);
        try {
            bairro2 = dao.select(bairro);
            Log.d("SPIAA", "Select Bairro ok!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("19", bairro2.getId().toString());
    }
}