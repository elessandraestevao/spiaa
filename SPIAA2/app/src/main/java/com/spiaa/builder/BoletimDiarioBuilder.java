package com.spiaa.builder;

import com.spiaa.modelo.TratamentoAntiVetorial;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by eless on 22/09/2015.
 */
public class BoletimDiarioBuilder {

    public List<TratamentoAntiVetorial> geraBoletins(int quantidade) {
        List<TratamentoAntiVetorial> tratamentoAntiVetorialList = new ArrayList<TratamentoAntiVetorial>();
        Random random = new Random();

        for (int i = 1; i <= quantidade; i++) {
            TratamentoAntiVetorial tratamentoAntiVetorial = new TratamentoAntiVetorial();
            tratamentoAntiVetorial.setCategoria("Sede");
            tratamentoAntiVetorial.setData(new Date());
            tratamentoAntiVetorial.setNumero("123" + 1);
            tratamentoAntiVetorial.setSemana("L" + i + "Tratamento Vetorial");
            tratamentoAntiVetorial.setTurma("5960/L");
            tratamentoAntiVetorial.setNumeroAtividade("1223");
            tratamentoAntiVetorial.setTipoAtividade("Atividade Tipo");


            //Muda status e bairro
           /* if(random.nextInt(2) == 1){
                tratamentoAntiVetorial.setStatus("EM ABERTO");
                //boletim.setBairro("Fernandes");
            }else{
                tratamentoAntiVetorial.setStatus("CONCLUÍDO");
                //boletim.setBairro("Centro");
            }*/

            tratamentoAntiVetorialList.add(tratamentoAntiVetorial);
        }
        return tratamentoAntiVetorialList;
    }
}
