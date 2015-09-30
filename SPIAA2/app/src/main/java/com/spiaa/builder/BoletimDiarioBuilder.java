package com.spiaa.builder;

import android.content.SharedPreferences;

import com.spiaa.modelo.Boletim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by eless on 22/09/2015.
 */
public class BoletimDiarioBuilder {

    public List<Boletim> geraBoletins(int quantidade) {
        List<Boletim> boletimList = new ArrayList<Boletim>();
        Random random = new Random();

        for (int i = 1; i <= quantidade; i++) {
            Boletim boletim = new Boletim();
            boletim.setCategoria("Sede");
            boletim.setData(i + "/" + i + 1 + "/2015");
            boletim.setNumero("123" + 1);
            boletim.setSemanaEpidemiologia("L" + i + "Tratamento Vetorial");
            boletim.setTurma("5960/L");

            //Muda status e bairro
            if(random.nextInt(2) == 1){
                boletim.setStatus("EM ABERTO");
                boletim.setBairro("Fernandes");
            }else{
                boletim.setStatus("CONCLUÍDO");
                boletim.setBairro("Centro");
            }

            boletimList.add(boletim);
        }
        return boletimList;
    }
}
