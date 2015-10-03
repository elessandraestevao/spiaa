package com.spiaa.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.spiaa.R;
import com.spiaa.modelo.IsXLargeScreen;

public class PerfilEditActivity extends AppCompatActivity {
    android.support.v7.app.ActionBar ab;
    FloatingActionButton fabEditar;
    TextInputLayout tilSenha;
    TextInputLayout tilSenhaConfirm;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_edit);

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

        final EditText nome = (EditText) findViewById(R.id.perfil_nome);
        final EditText email = (EditText) findViewById(R.id.perfil_email);
        fabEditar = (FloatingActionButton) findViewById(R.id.botao_editar_perfil);

        //Recuperar dados do usuário logado
        SharedPreferences dadosUsuario = getSharedPreferences("UsuarioLogado", MODE_PRIVATE);
        if (dadosUsuario != null) {
            //Preencher campos com os dados do usuário logado
            EditText usuario = (EditText) findViewById(R.id.perfil_usuario);
            usuario.setText(dadosUsuario.getString("usuario", ""));

            nome.setText(dadosUsuario.getString("nome", ""));

            email.setText(dadosUsuario.getString("email", ""));

            EditText numero = (EditText) findViewById(R.id.perfil_numero);
            numero.setText(dadosUsuario.getString("numero", ""));

            EditText turma = (EditText) findViewById(R.id.perfil_turma);
            turma.setText(dadosUsuario.getString("turma", ""));

            fabEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Habilitar campos que podem ser editados
                    //Mudar título para Editar Perfil
                    ab = getSupportActionBar();
                    ab.setTitle("Editar Perfil");

                    //habilitar campos para edição do usuário
                    nome.setInputType(1);
                    email.setInputType(1);

                    //Habilitar visibilidade de campos de senha
                    tilSenha = (TextInputLayout) findViewById(R.id.message_senha);
                    tilSenha.setVisibility(View.VISIBLE);

                    tilSenhaConfirm = (TextInputLayout) findViewById(R.id.message_confirmacao_senha);
                    tilSenhaConfirm.setVisibility(View.VISIBLE);

                    fabEditar.setVisibility(View.INVISIBLE);

                    FloatingActionButton fabSalvar = (FloatingActionButton) findViewById(R.id.botao_salvar_perfil);
                    fabSalvar.setVisibility(View.VISIBLE);
                }
            });
        }
        FloatingActionButton fabSalvar = (FloatingActionButton) findViewById(R.id.botao_salvar_perfil);
        fabSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilEditActivity.this, "Perfil atualizado com secesso!", Toast.LENGTH_LONG).show();
                //Perfil volta a ser apenas para visualização
                //Desabilitar campos para edição do usuário
                nome.setInputType(0);
                email.setInputType(0);
                fabEditar.setVisibility(View.VISIBLE);
                //Desabilitar visibilidade de campos de senha
                tilSenha.setVisibility(View.INVISIBLE);
                tilSenhaConfirm.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

}
