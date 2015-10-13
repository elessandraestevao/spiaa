package com.spiaa.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.spiaa.R;
import com.spiaa.dao.DenunciaDAO;
import com.spiaa.modelo.Denuncia;
import com.spiaa.modelo.IsXLargeScreen;

import java.util.Date;

public class DenunciaActivity extends AppCompatActivity implements View.OnClickListener {
    private Denuncia denuncia;
    private FloatingActionButton botaoFinalizar;
    private FloatingActionButton botaoEditar;
    private EditText observacoes;
    private android.support.v7.app.ActionBar ab;
    private TextView endereco;
    private TextView numero;
    private TextView bairro;
    private TextView irregularidade;
    private TextView status;

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

        ab = getSupportActionBar();
        endereco = (TextView) findViewById(R.id.endereco_denuncia);
        numero = (TextView) findViewById(R.id.numero_denuncia);
        bairro = (TextView) findViewById(R.id.bairro_denuncia);
        irregularidade = (TextView) findViewById(R.id.irregularidade_denuncia);
        status = (TextView) findViewById(R.id.status_denuncia);
        observacoes = (EditText) findViewById(R.id.observacoes_denuncia);

        botaoFinalizar = (FloatingActionButton) findViewById(R.id.botao_finalizar);
        botaoEditar = (FloatingActionButton) findViewById(R.id.botao_editar);

        botaoFinalizar.setOnClickListener(this);
        botaoEditar.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //PREENCHER CAMPOS COM DADOS DAS DENÚNCIAS
        Bundle dados = getIntent().getExtras();
        denuncia = (Denuncia) dados.getSerializable("Denuncia");

        if (denuncia != null) {
            //Mudar título com nome da Denúncia
            ab.setTitle(denuncia.getTitulo());

            //Preencher campos com os dados da denúncia
            endereco.setText(denuncia.getEndereco());
            numero.setText(String.valueOf(denuncia.getNumero()));
            bairro.setText(" " + denuncia.getBairro().getNome());
            irregularidade.setText(denuncia.getIrregularidade());
            status.setText(denuncia.getStatus());

            //Verificando se a denúncia encontra-se finalizada
            if (denuncia.getObservacao() != null && !denuncia.getObservacao().isEmpty()) {
                observacoes.setText(denuncia.getObservacao());
                observacoes.setInputType(0);
            }

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
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botao_finalizar:
                if (!observacoes.getText().toString().isEmpty()) {
                    denuncia.setDataFinalizacao(new Date());
                    denuncia.setObservacao(observacoes.getText().toString());
                    denuncia.setStatus("Finalizada");
                    try {
                        int retorno = new DenunciaDAO(DenunciaActivity.this).update(denuncia);
                        if (retorno > 0) {
                            Toast.makeText(DenunciaActivity.this, "Denúncia finalizada com sucesso", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    } catch (Exception e) {
                        Log.e("SPIAA", "Erro no UPDATE de denúncia finalizada", e);
                    }
                } else {
                    Snackbar.make(findViewById(R.id.linear_denuncia), "Preencha o campo Observações/Constatações", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.botao_editar:
                habilitaCampoObservacoesParaEdicao();
                habilitaBotaoFinalizar();
                desabilitaBotaoEditar();
                habilitaTecladoNaTela();
                break;

        }
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

    private void habilitaCampoObservacoesParaEdicao(){
        observacoes.setInputType(1);
        observacoes.requestFocus();
    }
}
