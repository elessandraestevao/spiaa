package com.spiaa.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.spiaa.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoletimDiarioActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletim_diario);

        //esconder teclado ao entrar nesta activity
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Preencher dropdown com bairros destinados ao Agente de Saúde
        Spinner spinner = (Spinner) findViewById(R.id.dropdown_bairros);
        String[] bairros = new String[]{"Fernandes", "Centro", "Maristela"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bairros);
        spinner.setAdapter(adapter);

        //Botão de ATIVIDADES fica invisible inicialmente
        final Button botaoAtividades = (Button) findViewById(R.id.atividades);
        botaoAtividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BoletimDiarioActivity.this, TodasAtividadesActivity.class);
                startActivity(intent1);
            }
        });

        //Botão de CONCLUIR BOLETIM fica invisible inicialmente
        final Button botaoConcluirBoletim = (Button) findViewById(R.id.concluir_boletim);
        botaoConcluirBoletim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BoletimDiarioActivity.this, "Boletim Diário concluído com secesso!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(BoletimDiarioActivity.this, TodosBoletinsDiariosActivity.class);
                startActivity(intent);
            }
        });

        //Botão CRIAR BOLETIM
        final FloatingActionButton criarBoletim = (FloatingActionButton) findViewById(R.id.fab_criar_boletim);
        criarBoletim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Boletim Diário criado com secesso!", Snackbar.LENGTH_LONG).show();
                criarBoletim.setVisibility(View.INVISIBLE);
                botaoAtividades.setVisibility(View.VISIBLE);
                botaoConcluirBoletim.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_boletim_diario, menu);
        return true;
    }
}
