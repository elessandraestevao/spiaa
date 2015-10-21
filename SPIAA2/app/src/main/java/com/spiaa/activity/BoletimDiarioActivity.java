package com.spiaa.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.spiaa.R;
import com.spiaa.adapter.BoletimListaAdapter;
import com.spiaa.dao.AtividadeDAO;
import com.spiaa.dao.BairroDAO;
import com.spiaa.dao.TratamentoAntiVetorialDAO;
import com.spiaa.dao.UsuarioDAO;
import com.spiaa.modelo.Bairro;
import com.spiaa.modelo.IsXLargeScreen;
import com.spiaa.modelo.TratamentoAntiVetorial;
import com.spiaa.modelo.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoletimDiarioActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton criarBoletim;
    Button botaoAtividades;
    Button botaoAtualizarBoletim;
    Button botaoConcluirBoletim;
    Button botaoExcluirBoletim;
    android.support.v7.app.ActionBar ab;
    public static Long BAIRRO_ID;
    private List<Bairro> bairroList;
    private Spinner spinnerBairros;
    private SharedPreferences dadosUsuario;
    private TratamentoAntiVetorial tratamentoAntiVetorial;
    private ArrayAdapter<String> adapter;
    private EditText agente;
    private EditText numeroAgente;
    private EditText semanaEpidemiologica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletim_diario);

        setOrientationOfScreen();
        hideKeyboard();

        spinnerBairros = (Spinner) findViewById(R.id.dropdown_bairros);

        botaoAtividades = (Button) findViewById(R.id.atividades);
        botaoAtividades.setOnClickListener(this);

        botaoAtualizarBoletim = (Button) findViewById(R.id.atualizar_boletim);
        botaoAtualizarBoletim.setOnClickListener(this);

        botaoConcluirBoletim = (Button) findViewById(R.id.concluir_boletim);
        botaoConcluirBoletim.setOnClickListener(this);

        botaoExcluirBoletim = (Button) findViewById(R.id.excluir_boletim);
        botaoExcluirBoletim.setOnClickListener(this);

        //Campos referentes ao Agente de Saúde
        agente = (EditText) findViewById(R.id.agente_bd);
        numeroAgente = (EditText) findViewById(R.id.numero_agente_bd);

        //Semana epidemiológica
        semanaEpidemiologica = (EditText) findViewById(R.id.semana_epidemiologica_bd);

        criarBoletim = (FloatingActionButton) findViewById(R.id.fab_criar_boletim);
        criarBoletim.setOnClickListener(this);
    }

    private void setOrientationOfScreen() {
        if (!new IsXLargeScreen().isXLargeScreen(getApplicationContext())) {
            //set phones to portrait
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //Tablets como Landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private boolean verificaSeTemAtividades() {
        if (tratamentoAntiVetorial != null) {
            if (new AtividadeDAO(BoletimDiarioActivity.this)
                    .countAtividadesDoBoletim(tratamentoAntiVetorial.getId()) > 0) {
                return true;
            }
        }
        return false;
    }

    private void preencherListaDeBairros() {
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
    }

    private TratamentoAntiVetorial recuperarBoletimDiarioSelecionado() {
        Bundle dados = getIntent().getExtras();
        tratamentoAntiVetorial = (TratamentoAntiVetorial) dados.getSerializable("Boletim");
        return tratamentoAntiVetorial;
    }

    private void alterarTitulo() {
        ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(tratamentoAntiVetorial.getTitulo());
        }
    }

    private void setBairroSelecionado() {
        for (int position = 0; position < adapter.getCount(); position++) {
            if (adapter.getItem(position).equals(tratamentoAntiVetorial.getBairro().getNome())) {
                spinnerBairros.setSelection(position);
                break;
            }
        }
    }

    private void setSemanaEpidemiologica() {
        semanaEpidemiologica.setText(tratamentoAntiVetorial.getSemana());
    }

    private void setFieldOnlyVisualization(TextView campo) {
        campo.setInputType(0);
    }

    private void recuperarDadosDoUsuario() {
        agente.setText(tratamentoAntiVetorial.getUsuario().getNome());
        setFieldOnlyVisualization(agente);

        numeroAgente.setText(tratamentoAntiVetorial.getNumero());
        setFieldOnlyVisualization(numeroAgente);
    }

    private void setDadosUsuarioLogado() {
        dadosUsuario = getSharedPreferences("UsuarioLogado", MODE_PRIVATE);
        agente.setText(dadosUsuario.getString("nome", ""));
        setFieldOnlyVisualization(agente);

        numeroAgente.setText(dadosUsuario.getString("numero", ""));
        setFieldOnlyVisualization(numeroAgente);
    }

    @Override
    protected void onResume() {
        super.onResume();

        preencherListaDeBairros();
        if (!TodosBoletinsDiariosFragment.NOVO_BOLETIM) {
            //Preencher dados do Boletim Diário selecionado na listagem de Boletins Diários
            if (recuperarBoletimDiarioSelecionado() != null) {
                setIdBoletim();
                alterarTitulo();
                setBairroSelecionado();
                obterIdBairroSelecionado();
                setSemanaEpidemiologica();
                manipulaBotoes();
            }
            recuperarDadosDoUsuario();

        } else {
            setDadosUsuarioLogado();
        }

        if (verificaSeTemAtividades()) {
            spinnerBairros.setEnabled(false);
        }
    }

    private void setIdBoletim() {
        //Id do Boletim utilizado nas Atividades do Boletim respectivo
        TratamentoAntiVetorial.ID_BOLETIM = tratamentoAntiVetorial.getId();
    }

    private void obterIdBairroSelecionado() {
        for (Bairro bairro : bairroList) {
            if (spinnerBairros.getSelectedItem().toString().equals(bairro.getNome())) {
                BAIRRO_ID = bairro.getId();
                break;
            }
        }
    }

    private Bairro obterBairroSelecionado() {
        Bairro b = null;
        for (Bairro bairro : bairroList) {
            if (spinnerBairros.getSelectedItem().toString().equals(bairro.getNome())) {
                b = bairro;
                break;
            }
        }
        return b;
    }

    private void manipulaBotoes() {
        //Esconde botão de CRIAR BOLETIM
        criarBoletim = (FloatingActionButton) findViewById(R.id.fab_criar_boletim);
        criarBoletim.setVisibility(View.GONE);
        //Mostra botões de ATIVIDADES, ATUALIZAR, CONCLUIR BOLETIM e EXCLUIR BOLTEIM
        botaoAtividades.setVisibility(View.VISIBLE);
        botaoAtualizarBoletim.setVisibility(View.VISIBLE);
        botaoConcluirBoletim.setVisibility(View.VISIBLE);
        botaoExcluirBoletim.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_boletim_diario, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.atividades:
                Intent intent1 = new Intent(BoletimDiarioActivity.this, TodasAtividadesActivity.class);
                startActivity(intent1);
                break;
            case R.id.atualizar_boletim:
                tratamentoAntiVetorial.setSemana(semanaEpidemiologica.getText().toString());
                tratamentoAntiVetorial.setBairro(obterBairroSelecionado());
                try {
                    new TratamentoAntiVetorialDAO(BoletimDiarioActivity.this).update(tratamentoAntiVetorial);
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro no UPDATE do Boletim Diário", e);
                }
                Toast.makeText(BoletimDiarioActivity.this, "Boletim Diário atualizado com sucesso", Toast.LENGTH_LONG).show();
                //vaiParaListaDeTodosBoletins
                onBackPressed();
                break;
            case R.id.concluir_boletim:
                if (semanaEpidemiologica.getText().toString().isEmpty() || semanaEpidemiologica.getText().toString().trim().equals("")) {
                    Snackbar.make(findViewById(R.id.linear_boletim), "Preencha o campo Semana Epidemiológica", Snackbar.LENGTH_LONG).show();
                } else {
                    final AlertDialog.Builder dialogConcluir = new AlertDialog.Builder(BoletimDiarioActivity.this);
                    dialogConcluir.setMessage("O Boletim Diário será enviado ao servidor no próximo sincronismo. " +
                            "Recomenda-se concluí-lo após levantamento de todas atividades.");
                    dialogConcluir.setPositiveButton("Concluir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //definir Status como Concluído e Semana Epidemiológica
                            tratamentoAntiVetorial.setStatus(getResources().getString(R.string.concluido));
                            tratamentoAntiVetorial.setSemana(semanaEpidemiologica.getText().toString());
                            try {
                                new TratamentoAntiVetorialDAO(BoletimDiarioActivity.this).updateStatusConcluido(tratamentoAntiVetorial);
                            } catch (Exception e) {
                                Log.e("SPIAA", "Erro no UPDATE de Status do Boletim Diário", e);
                            }
                            Toast.makeText(BoletimDiarioActivity.this, "Boletim Diário concluído com sucesso", Toast.LENGTH_LONG).show();
                            //vaiParaListaDeTodosBoletins
                            onBackPressed();
                        }
                    });

                    dialogConcluir.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialogConcluir = dialogConcluir.show();
                    mudaCorTextoBotaoDialog(alertDialogConcluir);
                }
                break;
            case R.id.fab_criar_boletim:
                //Criar um objeto TAV e preencher com os dados da tela
                TratamentoAntiVetorial tratamentoAntiVetorial = new TratamentoAntiVetorial();

                tratamentoAntiVetorial.setTurma(dadosUsuario.getString("turma", ""));
                tratamentoAntiVetorial.setTipoAtividade("Tratamento");
                tratamentoAntiVetorial.setNumero(numeroAgente.getText().toString());
                tratamentoAntiVetorial.setSemana(semanaEpidemiologica.getText().toString());

                //definir Status como Em aberto
                tratamentoAntiVetorial.setStatus(String.valueOf(getResources().getString(R.string.em_aberto)));

                //Data de criação do Boletim
                tratamentoAntiVetorial.setData(new Date());

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
                    new TratamentoAntiVetorialDAO(BoletimDiarioActivity.this).insert(tratamentoAntiVetorial);
                    Toast.makeText(BoletimDiarioActivity.this, "Boletim Diário criado com sucesso", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } catch (Exception e) {
                    Log.e("SPIAA", "Erro ao tentar salvar novo Tratamento anti-vetorial no banco local", e);
                }
                setNovoBoletimFalse();
                break;
            case R.id.excluir_boletim:
                final AlertDialog.Builder dialog = new AlertDialog.Builder(BoletimDiarioActivity.this);
                dialog.setMessage("Tem certeza de que deseja excluir " + this.tratamentoAntiVetorial.getTitulo() + "?");

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            new TratamentoAntiVetorialDAO(BoletimDiarioActivity.this).delete(TratamentoAntiVetorial.ID_BOLETIM);
                            Toast.makeText(BoletimDiarioActivity.this, "Boletim Diário excluído", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (Exception e) {
                            Log.e("SPIAA", "Erro ao deletar Boletim Diário", e);
                            Toast.makeText(BoletimDiarioActivity.this, "Erro ao tentar excluir Boletim Diário", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }
                });
                dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = dialog.show();
                mudaCorTextoBotaoDialog(alertDialog);
                break;

        }
    }

    private void mudaCorTextoBotaoDialog(AlertDialog alertDialog) {
        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        positiveButton.setTextColor(getResources().getColor(R.color.red_padrao));
        negativeButton.setTextColor(getResources().getColor(R.color.red_padrao));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setNovoBoletimFalse();
    }

    private void setNovoBoletimFalse() {
        TodosBoletinsDiariosFragment.NOVO_BOLETIM = false;
    }
}
