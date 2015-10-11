package com.spiaa.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.spiaa.R;
import com.spiaa.dao.BairroDAO;
import com.spiaa.dao.TratamentoAntiVetorialDAO;
import com.spiaa.dao.UsuarioDAO;
import com.spiaa.modelo.Bairro;
import com.spiaa.modelo.IsXLargeScreen;
import com.spiaa.modelo.TratamentoAntiVetorial;
import com.spiaa.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class BoletimDiarioActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton criarBoletim;
    Button botaoAtividades;
    Button botaoConcluirBoletim;
    android.support.v7.app.ActionBar ab;
    public static Long BAIRRO_ID;
    private List<Bairro> bairroList;
    private Spinner spinnerBairros;
    private SharedPreferences dadosUsuario;
    private TratamentoAntiVetorial tratamentoAntiVetorial;
    private ArrayAdapter<String> adapter;
    private EditText agente;
    private EditText numeroAgente;
    private EditText turmaAgente;
    private EditText semanaEpidemiologica;

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

        spinnerBairros = (Spinner) findViewById(R.id.dropdown_bairros);

        //Botão de ATIVIDADES fica invisible inicialmente
        botaoAtividades = (Button) findViewById(R.id.atividades);
        botaoAtividades.setOnClickListener(this);

        //Botão de CONCLUIR BOLETIM fica invisible inicialmente
        botaoConcluirBoletim = (Button) findViewById(R.id.concluir_boletim);
        botaoConcluirBoletim.setOnClickListener(this);

        //Campos referentes ao Agente de Saúde
        agente = (EditText) findViewById(R.id.agente_bd);
        numeroAgente = (EditText) findViewById(R.id.numero_agente_bd);
        turmaAgente = (EditText) findViewById(R.id.turma_agente_bd);

        //Semana epidemiológica
        semanaEpidemiologica = (EditText) findViewById(R.id.semana_epidemiologica_bd);

        //Usuário Logado
        dadosUsuario = getSharedPreferences("UsuarioLogado", MODE_PRIVATE);

        //Botão CRIAR BOLETIM
        criarBoletim = (FloatingActionButton) findViewById(R.id.fab_criar_boletim);
        criarBoletim.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Preencher dropdown com bairros destinados ao Agente de Saúde
        bairroList = new ArrayList<>();
        try {
            bairroList = new BairroDAO(BoletimDiarioActivity.this).selectAll();
        } catch (Exception e) {
            Log.e("SPIAA", "Erro ao tentar SELECT ALL Bairros", e);
        }
        String[] bairros = new String[bairroList.size()];
        int i = 0;
        for (Bairro bairro : bairroList) {
            bairros[i] = bairro.getNome();
            i++;
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bairros);
        spinnerBairros.setAdapter(adapter);

        if (!TodosBoletinsDiariosActivity.NOVO_BOLETIM) {
            //Obter ID do Bairro selecionado no Dropdown
            obterIdBairroSelecionado();

            //Preencher dados do Boletim Diário selecionado na listagem de Boletins Diários
            Bundle dados = getIntent().getExtras();
            tratamentoAntiVetorial = (TratamentoAntiVetorial) dados.getSerializable("Boletim");

            if (tratamentoAntiVetorial != null) {

                //Mudar título do Boletim Diário
                ab = getSupportActionBar();
                if (ab != null) {
                    ab.setTitle(tratamentoAntiVetorial.getTitulo());
                }

                //Setar Bairro definido
                for (int position = 0; position < adapter.getCount(); position++) {
                    if (adapter.getItem(position).equals(tratamentoAntiVetorial.getBairro().getNome())) {
                        spinnerBairros.setSelection(position);
                        break;
                    }
                }
                //Semana epidemiológica
                semanaEpidemiologica.setText(tratamentoAntiVetorial.getSemana());

                manipulaBotoes();
            }


            //Recuperar nome do usuário logado (Agente)
            agente.setText(tratamentoAntiVetorial.getUsuario().getNome());
            //Apenas visualização, não pode editar esse campo
            agente.setInputType(0);

            //Número do agente de saúde
            numeroAgente.setText(tratamentoAntiVetorial.getNumero());
            //Apenas visualização, não pode editar esse campo
            numeroAgente.setInputType(0);

            //Turma do agente de saúde
            turmaAgente.setText(tratamentoAntiVetorial.getTurma());
            //Apenas visualização, não pode editar esse campo
            turmaAgente.setInputType(0);
        }else{
            //Novo boletim com dados do agente carregados
            //Dados do Agente de Saúde referentes ao LOGIN
            //Recuperar nome do usuário logado (Agente)
            agente.setText(dadosUsuario.getString("nome", ""));
            //Apenas visualização, não pode editar esse campo
            agente.setInputType(0);

            //Número do agente de saúde
            numeroAgente.setText(dadosUsuario.getString("numero", ""));
            //Apenas visualização, não pode editar esse campo
            numeroAgente.setInputType(0);

            //Turma do agente de saúde
            turmaAgente.setText(dadosUsuario.getString("turma", ""));
            //Apenas visualização, não pode editar esse campo
            turmaAgente.setInputType(0);
        }
    }

    private void obterIdBairroSelecionado() {
        for (Bairro bairro : bairroList) {
            if (spinnerBairros.getSelectedItem().toString().equals(bairro.getNome())) {
                BAIRRO_ID = bairro.getId();
            }
        }
        Toast.makeText(BoletimDiarioActivity.this, BAIRRO_ID.toString(), Toast.LENGTH_SHORT).show();
    }

    private Bairro obterBairroSelecionado() {
        Bairro b = null;
        for (Bairro bairro : bairroList) {
            if (spinnerBairros.getSelectedItem().toString().equals(bairro.getNome())) {
                return bairro;
            }
        }
        return b;
    }

    private void manipulaBotoes() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.atividades:
                Intent intent1 = new Intent(BoletimDiarioActivity.this, TodasAtividadesActivity.class);
                startActivity(intent1);
                break;
            case R.id.concluir_boletim:
                //definir Status como Concluído
                tratamentoAntiVetorial.setStatus("Em aberto");

                Toast.makeText(BoletimDiarioActivity.this, "Boletim Diário concluído com secesso!", Toast.LENGTH_LONG).show();
                vaiParaListaDeTodosBoletins();
                break;
            case R.id.fab_criar_boletim:
                //Criar um objeto TAV e preencher com os dados da tela
                TratamentoAntiVetorial tratamentoAntiVetorial = new TratamentoAntiVetorial();

                tratamentoAntiVetorial.setTurma(turmaAgente.getText().toString());
                tratamentoAntiVetorial.setTipoAtividade("Tratamento");
                tratamentoAntiVetorial.setNumero(numeroAgente.getText().toString());
                tratamentoAntiVetorial.setSemana(semanaEpidemiologica.getText().toString());

                //definir Status como Em aberto
                tratamentoAntiVetorial.setStatus("Em aberto");

                //Converter data para String
                java.util.Date data = new java.util.Date();
                tratamentoAntiVetorial.setData(String.valueOf(data));

                //Obter bairro selecionado
                tratamentoAntiVetorial.setBairro(obterBairroSelecionado());

                //Obter agente de saúde
                dadosUsuario = getSharedPreferences("UsuarioLogado", MODE_PRIVATE);
                Usuario usuario = new Usuario();
                usuario.setId(dadosUsuario.getLong("id", 0));
                Usuario agente = new Usuario();
                try {
                    agente = new UsuarioDAO(BoletimDiarioActivity.this).select(usuario);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tratamentoAntiVetorial.setUsuario(agente);

                try {
                    Long retorno = new TratamentoAntiVetorialDAO(BoletimDiarioActivity.this).insert(tratamentoAntiVetorial);
                    Snackbar.make(v, "Boletim Diário criado com secesso!", Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro ao tentar salvar novo Tratamento anti-vetorial no banco local", e);
                }
                TodosBoletinsDiariosActivity.NOVO_BOLETIM = false;
                manipulaBotoes();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TodosBoletinsDiariosActivity.NOVO_BOLETIM = false;
    }

    private void vaiParaListaDeTodosBoletins(){
        Intent intent = new Intent(BoletimDiarioActivity.this, TodosBoletinsDiariosActivity.class);
        startActivity(intent);
    }
}
