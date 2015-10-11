package com.spiaa.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.spiaa.R;
import com.spiaa.dao.DenunciaDAO;
import com.spiaa.modelo.Denuncia;
import com.spiaa.modelo.IsXLargeScreen;

public class DenunciaActivity extends AppCompatActivity {
    private Denuncia denuncia;
    private FloatingActionButton botaoFinalizar;
    private FloatingActionButton botaoEditar;
    private EditText observacoes;

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
        Bundle dados = getIntent().getExtras();
        denuncia = (Denuncia) dados.getSerializable("Denuncia");

        //Mudar título com nome da Denúncia
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        // ab.setTitle(dados.get("denuncia").toString());
        ab.setTitle(denuncia.getTitulo());

        TextView endereco = (TextView) findViewById(R.id.endereco_denuncia);
        //endereco.setText(dados.get("endereco").toString());
        endereco.setText(denuncia.getEndereco());


        TextView numero = (TextView) findViewById(R.id.numero_denuncia);
        numero.setText(String.valueOf(denuncia.getNumero()));

        TextView bairro = (TextView) findViewById(R.id.bairro_denuncia);
        bairro.setText(" " + denuncia.getBairro().getNome());

        TextView irregularidade = (TextView) findViewById(R.id.irregularidade_denuncia);
        irregularidade.setText(denuncia.getIrregularidade());

        TextView status = (TextView) findViewById(R.id.status_denuncia);
        //status.setText(dados.get("status").toString());
        status.setText(denuncia.getStatus());

        observacoes = (EditText) findViewById(R.id.observacoes_denuncia);
        //Verificando se a denúncia encontra-se finalizada
        if (denuncia.getObservacao() != null && !denuncia.getObservacao().isEmpty()) {
            observacoes.setText(denuncia.getObservacao());
            observacoes.setInputType(0);
        }

        botaoFinalizar = (FloatingActionButton) findViewById(R.id.botao_finalizar);
        botaoEditar = (FloatingActionButton) findViewById(R.id.botao_editar);

        //Definir cor do fundo do status e texto do botão da tela
        if (status.getText().equals("Encaminhada")) {
            //cor red
            status.setTextColor(Color.parseColor("#cc0000"));
            habilitaBotaoFinalizar();
        } else {
            //cor green
            status.setTextColor(Color.parseColor("#669900"));
            habilitaBotaoEditar();
        }

        botaoFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!observacoes.getText().toString().isEmpty()) {
                    denuncia.setObservacao(observacoes.getText().toString());
                    denuncia.setStatus("Finalizada");
                    try {
                        int retorno = new DenunciaDAO(DenunciaActivity.this).update(denuncia);
                        if (retorno > 0) {
                            Toast.makeText(DenunciaActivity.this, "Denúncia finalizada com sucesso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DenunciaActivity.this, DenunciasActivity.class);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        Log.e("SPIAA", "Erro no UPDATE de denúncia finalizada", e);
                    }
                } else {
                    Snackbar.make(findViewById(R.id.linear_denuncia), "Preencha o campo Observações/Constatações", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        botaoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Habilitar campo observações para edição
                observacoes.setInputType(1);
                observacoes.requestFocus();
                habilitaBotaoFinalizar();
                desabilitaBotaoEditar();
                habilitaTecladoNaTela();

            }
        });
    }

    private void habilitaBotaoEditar() {
        botaoEditar.setVisibility(View.VISIBLE);
    }

    private void desabilitaBotaoEditar() {
        botaoEditar.setVisibility(View.INVISIBLE);
    }

    private void habilitaBotaoFinalizar() {
        botaoFinalizar.setVisibility(View.VISIBLE);
    }

    private void habilitaTecladoNaTela() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(observacoes, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_denuncia, menu);
        return true;
    }

    private void aoVoltar(){
        Intent intent = new Intent(this, DenunciasActivity.class);
        startActivity(intent);
        finish();
    }

}
