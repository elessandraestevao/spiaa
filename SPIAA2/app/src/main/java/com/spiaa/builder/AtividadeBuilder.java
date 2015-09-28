package com.spiaa.builder;

import com.spiaa.modelo.Atividade;

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

        for (int i = 1; i <= quantidade; i++) {
            Atividade atividade = new Atividade();
            atividade.setNumero(String.valueOf(i));
            atividade.setAdulticida("1" + i);
            atividade.setEndereco("Rua Inatel, número " + i);
            atividade.setLarvicida("2" + i);
            atividade.setNumero_quarteirao("F-1" + i);
            atividade.setTipo("Levantamento de Índice");
            atividade.setTotal_inspecionado("1" + (i % 2));

            //Muda observações, tdf
            if (random.nextInt(3) == 0) {
                atividade.setObservacoes("RECEBIDO");
                atividade.setTdf("ARM");
                atividade.setTipo_unidade("R");
            } else if (random.nextInt(3) == 1) {
                atividade.setObservacoes("RESGATADO");
                atividade.setTdf("P.E.");
                atividade.setTipo_unidade("C");
            } else {
                atividade.setObservacoes("FECHADO");
                atividade.setTdf("P.V.E.");
                atividade.setTipo_unidade("P.E.");
            }
            atividadeList.add(atividade);
        }
        return atividadeList;
    }
}
