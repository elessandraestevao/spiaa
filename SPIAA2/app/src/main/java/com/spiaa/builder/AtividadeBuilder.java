package com.spiaa.builder;

import com.spiaa.modelo.Atividade;
import com.spiaa.modelo.AtividadeCriadouro;
import com.spiaa.modelo.AtividadeInseticida;
import com.spiaa.modelo.Criadouro;
import com.spiaa.modelo.Inseticida;
import com.spiaa.modelo.Quarteirao;
import com.spiaa.modelo.TipoImoveis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by eless on 26/09/2015.
 */
public class AtividadeBuilder {

    public List<Atividade> geraAtividades(int quantidade) {
        List<Atividade> atividadeList = new ArrayList<Atividade>();
        Random random = new Random();

       /* Quarteirao quarteirao = new Quarteirao();
        quarteirao.setId(1L);

        TipoImoveis tipoImoveis = new TipoImoveis();
        tipoImoveis.setId(1L);

        Criadouro c = new Criadouro();
        c.setId(1L);

        AtividadeCriadouro ac = new AtividadeCriadouro();
        ac.setCriadouro(c);
        ac.setQuantidadeCriadouro(3);
        List<AtividadeCriadouro> acList = new ArrayList<AtividadeCriadouro>();
        acList.add(ac);

        Inseticida inseticida = new Inseticida();
        inseticida.setId(1L);

        AtividadeInseticida ai = new AtividadeInseticida();
        ai.setId(1L);
        ai.setQuantidadeInseticida(23);
        List<AtividadeInseticida> aiList = new ArrayList<AtividadeInseticida>();
        aiList.add(ai);*/


        for (int i = 1; i <= quantidade; i++) {
            Atividade atividade = new Atividade();
            atividade.setEndereco("Rua das Araras");
            atividade.setNumero(String.valueOf(i));
            atividade.setInspecionado(12);
            atividade.setLatitude("latitude");
            atividade.setLongitude("longitude");
            atividade.setObservacao("Observação");
            //atividade.setQuarteirao(quarteirao);
            //atividade.setTipoImoveis(tipoImoveis);
            //atividade.setAtividadeCriadouroList(acList);
            //atividade.setAtividadeInseticidasList(aiList);
            atividadeList.add(atividade);
        }
        return atividadeList;
    }
}
