package com.spiaa.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.spiaa.R;

public class PerfilEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_edit);

        //esconder teclado ao entrar nesta activity
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Recuperar dados do usuário logado
        SharedPreferences dadosUsuario = getSharedPreferences("UsuarioLogado", MODE_PRIVATE);

        EditText usuario = (EditText) findViewById(R.id.perfil_usuario);
        usuario.setText(dadosUsuario.getString("usuario", ""));

        final EditText nome = (EditText) findViewById(R.id.perfil_nome);
        nome.setText(dadosUsuario.getString("nome", ""));

        final EditText email = (EditText) findViewById(R.id.perfil_email);
        email.setText(dadosUsuario.getString("email", ""));

        EditText numero = (EditText) findViewById(R.id.perfil_numero);
        numero.setText(dadosUsuario.getString("numero", ""));

        EditText turma = (EditText) findViewById(R.id.perfil_turma);
        turma.setText(dadosUsuario.getString("turma", ""));

        final FloatingActionButton fabEditar = (FloatingActionButton) findViewById(R.id.botao_editar_perfil);
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //habilitar campos para edição do usuário
                nome.setInputType(1);
                email.setInputType(1);

                //Habilitar visibilidade de campos de senha
                TextInputLayout tilSenha = (TextInputLayout) findViewById(R.id.message_senha);
                tilSenha.setVisibility(View.VISIBLE);

                TextInputLayout tilSenhaConfirm = (TextInputLayout) findViewById(R.id.message_confirmacao_senha);
                tilSenhaConfirm.setVisibility(View.VISIBLE);

                fabEditar.setVisibility(View.INVISIBLE);

                FloatingActionButton fabSalvar = (FloatingActionButton) findViewById(R.id.botao_salvar_perfil);
                fabSalvar.setVisibility(View.VISIBLE);
            }
        });

        FloatingActionButton fabSalvar = (FloatingActionButton) findViewById(R.id.botao_salvar_perfil);
        fabSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilEditActivity.this, "Perfil atualizado com secesso!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PerfilEditActivity.this, PerfilEditActivity.class);
                startActivity(intent);


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
