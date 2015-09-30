package com.spiaa.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.spiaa.R;
import com.spiaa.modelo.IsXLargeScreen;

public class DenunciaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

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

        //PREENCHER CAMPOS COM DADOS DAS DENÚNCIAS
        Intent intent = getIntent();
        Bundle dados = intent.getExtras();

        //Mudar título com nome da Denúncia
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(dados.get("denuncia").toString());

        TextView endereco = (TextView) findViewById(R.id.endereco_denuncia);
        endereco.setText(dados.get("endereco").toString());

        TextView numero = (TextView) findViewById(R.id.numero_denuncia);
        numero.setText(dados.get("numero").toString());

        TextView bairro = (TextView) findViewById(R.id.bairro_denuncia);
        bairro.setText(dados.get("bairro").toString());

        TextView irregularidade = (TextView) findViewById(R.id.irregularidade_denuncia);
        irregularidade.setText(dados.get("irregularidade").toString());

        TextView status = (TextView) findViewById(R.id.status_denuncia);
        status.setText(dados.get("status").toString());

        FloatingActionButton botaoFinalizar = (FloatingActionButton) findViewById(R.id.botao_finalizar);
        FloatingActionButton botaoEditar = (FloatingActionButton) findViewById(R.id.botao_editar);

        //Definir cor do fundo do status e texto do botão da tela
        if (status.getText().equals("EM ABERTO")) {
            //cor red
            status.setTextColor(Color.parseColor("#cc0000"));
            botaoFinalizar.setVisibility(View.VISIBLE);
        } else {
            //cor green
            status.setTextColor(Color.parseColor("#669900"));
            botaoEditar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_denuncia, menu);
        return true;
    }
}
