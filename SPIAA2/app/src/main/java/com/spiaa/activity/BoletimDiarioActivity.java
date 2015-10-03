package com.spiaa.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.spiaa.R;
import com.spiaa.modelo.IsXLargeScreen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoletimDiarioActivity extends AppCompatActivity {
    FloatingActionButton criarBoletim;
    Button botaoAtividades;
    Button botaoConcluirBoletim;
    android.support.v7.app.ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletim_diario);

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

        //Preencher dropdown com bairros destinados ao Agente de Saúde
        Spinner spinner = (Spinner) findViewById(R.id.dropdown_bairros);
        String[] bairros = new String[]{"Fernandes", "Centro", "Maristela"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bairros);
        spinner.setAdapter(adapter);

        //Botão de ATIVIDADES fica invisible inicialmente
        botaoAtividades = (Button) findViewById(R.id.atividades);
        botaoAtividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BoletimDiarioActivity.this, TodasAtividadesActivity.class);
                startActivity(intent1);
            }
        });

        //Botão de CONCLUIR BOLETIM fica invisible inicialmente
        botaoConcluirBoletim = (Button) findViewById(R.id.concluir_boletim);
        botaoConcluirBoletim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BoletimDiarioActivity.this, "Boletim Diário concluído com secesso!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(BoletimDiarioActivity.this, TodosBoletinsDiariosActivity.class);
                startActivity(intent);
            }
        });

        //Preencher dados do Boletim Diário selecionado na listagem de Boletins Diários
        Intent intent = getIntent();
        Bundle dados = intent.getExtras();
        if (dados != null) {

            //Mudar título do Boletim Diário
            ab = getSupportActionBar();
            ab.setTitle(dados.get("boletim").toString());

            //Setar Bairro definido
            for (int position = 0; position < adapter.getCount(); position++) {
                if (adapter.getItem(position).equals(dados.get("bairro"))) {
                    spinner.setSelection(position);
                    break;
                }
            }

            //Semana epidemiológica
            EditText semanaEpidemiologica = (EditText) findViewById(R.id.semana_epidemiologica_bd);
            semanaEpidemiologica.setText(dados.get("semana_epidemiologica").toString());

            manipulaBotoes();
        }

        //Dados do Agente de Saúde referentes ao LOGIN
        //Recuperar nome do usuário logado (Agente)
        SharedPreferences dadosUsuario = getSharedPreferences("UsuarioLogado", MODE_PRIVATE);
        EditText agente = (EditText) findViewById(R.id.agente_bd);
        agente.setText(dadosUsuario.getString("nome", ""));
        //Apenas visualização, não pode editar esse campo
        agente.setInputType(0);

        //Número do agente de saúde
        EditText numeroAgente = (EditText) findViewById(R.id.numero_agente_bd);
        numeroAgente.setText(dadosUsuario.getString("numero", ""));
        //Apenas visualização, não pode editar esse campo
        numeroAgente.setInputType(0);

        //Turma do agente de saúde
        EditText turmaAgente = (EditText) findViewById(R.id.turma_agente_bd);
        turmaAgente.setText(dadosUsuario.getString("turma", ""));
        //Apenas visualização, não pode editar esse campo
        turmaAgente.setInputType(0);

        //Botão CRIAR BOLETIM
        criarBoletim = (FloatingActionButton) findViewById(R.id.fab_criar_boletim);
        criarBoletim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Boletim Diário criado com secesso!", Snackbar.LENGTH_LONG).show();
                manipulaBotoes();
            }
        });
    }

    private void manipulaBotoes(){
        //Esconde botão de CRIAR BOLETIM
        criarBoletim = (FloatingActionButton) findViewById(R.id.fab_criar_boletim);
        criarBoletim.setVisibility(View.INVISIBLE);
        //Mostra botões de ATIVIDADES e CONCLUIR BOLETIM
        botaoAtividades.setVisibility(View.VISIBLE);
        botaoConcluirBoletim.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_boletim_diario, menu);
        return true;
    }
}
