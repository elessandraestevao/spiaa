package com.spiaa.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.melnykov.fab.ObservableScrollView;
import com.spiaa.R;
import com.spiaa.modelo.IsXLargeScreen;

public class AtividadeActivity extends AppCompatActivity {
    android.support.v7.app.ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);

        //definição da orientação das telas da aplicação
        if (!new IsXLargeScreen().isXLargeScreen(getApplicationContext())) {
            //set phones to portrait;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //Tablets como Landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        //esconder teclado ao entrar nesta activity
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Preencher dados da Atividade selecionada na listagem de Atividades
        Intent intent = getIntent();
        Bundle dados = intent.getExtras();

        if(dados != null){
            //Mudar título da Atividade selecionada na listagem de atividades
            ab = getSupportActionBar();
            ab.setTitle(dados.get("atividade").toString());

            EditText numeroQuarteirao = (EditText) findViewById(R.id.numero_quarteirao_atividade);
            numeroQuarteirao.setText(dados.get("numero_quarteirao").toString());

            EditText endereco = (EditText) findViewById(R.id.endereco_atividade);
            endereco.setText(dados.get("endereco").toString());

            //Verifica qual tipo de unidade foi selecionado
            RadioButton tipoR = (RadioButton) findViewById(R.id.radio_tipo_r);
            RadioButton tipoC = (RadioButton) findViewById(R.id.radio_tipo_c);
            RadioButton tipoTB = (RadioButton) findViewById(R.id.radio_tipo_tb);
            RadioButton tipoPE = (RadioButton) findViewById(R.id.radio_tipo_pe);
            RadioButton tipoOutros = (RadioButton) findViewById(R.id.radio_tipo_outros);
            if (dados.get("tipo_unidade").toString().equals(tipoR.getText())){
                tipoR.setChecked(true);
            }else if(dados.get("tipo_unidade").toString().equals(tipoC.getText())){
                tipoC.setChecked(true);
            }else if(dados.get("tipo_unidade").toString().equals(tipoTB.getText())){
                tipoTB.setChecked(true);
            }else if(dados.get("tipo_unidade").toString().equals(tipoPE.getText())){
                tipoPE.setChecked(true);
            }else if(dados.get("tipo_unidade").toString().equals(tipoOutros.getText())){
                tipoOutros.setChecked(true);
            }

            //Verifica qual Obsercação foi selecionada
            RadioButton recebido = (RadioButton) findViewById(R.id.radio_recebido);
            RadioButton fechado = (RadioButton) findViewById(R.id.radio_fechado);
            RadioButton resgatado = (RadioButton) findViewById(R.id.radio_resgatado);
            if (dados.get("observacoes").toString().equals(recebido.getText())){
                recebido.setChecked(true);
            }else if(dados.get("observacoes").toString().equals(fechado.getText())){
                fechado.setChecked(true);
            }else if(dados.get("observacoes").toString().equals(resgatado.getText())){
                resgatado.setChecked(true);
            }

            //Seta valores dos criadouros
            EditText criadouroA1 = (EditText) findViewById(R.id.criadouro_a1);
            criadouroA1.setText(dados.get("criadouro_a1").toString());

            EditText criadouroA2 = (EditText) findViewById(R.id.criadouro_a2);
            criadouroA2.setText(dados.get("criadouro_a2").toString());

            EditText criadouroB = (EditText) findViewById(R.id.criadouro_b);
            criadouroB.setText(dados.get("criadouro_b").toString());

            EditText criadouroC = (EditText) findViewById(R.id.criadouro_c);
            criadouroC.setText(dados.get("criadouro_c").toString());

            EditText criadouroD1 = (EditText) findViewById(R.id.criadouro_d1);
            criadouroD1.setText(dados.get("criadouro_d1").toString());

            EditText criadouroD2 = (EditText) findViewById(R.id.criadouro_d2);
            criadouroD2.setText(dados.get("criadouro_d2").toString());

            EditText criadouroE = (EditText) findViewById(R.id.criadouro_e);
            criadouroE.setText(dados.get("criadouro_e").toString());

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_atividade, menu);
        return true;
    }
}