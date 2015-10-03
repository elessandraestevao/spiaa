package com.spiaa.builder;

import com.spiaa.modelo.Denuncia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by eless on 18/09/2015.
 */
public class DenunciaBuilder {

    public List<Denuncia> geraDenuncias(int quantidade){
        List<Denuncia> denunciaList = new ArrayList<Denuncia>();
        Random random = new Random();
        for(int i = 1; i <= quantidade; i++){
            Denuncia denuncia = new Denuncia();
            denuncia.getBairro().setNome("Bairro da cidade " + i);
            denuncia.setEndereco("Endereço do local onde tem focos " + i);
            denuncia.setNumero(i);
            denuncia.setObservacao(" ");

            //Muda status
            if(random.nextInt(2) == 1){
                denuncia.setStatus("EM ABERTO");
            }else{
                denuncia.setStatus("FINALIZADA");
            }

            denuncia.setIrregularidade("Existe foco do mosquito da Dengue no local indicado na denúncia.");
            denunciaList.add(denuncia);
        }
        return denunciaList;
    }
}
