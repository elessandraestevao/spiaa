package com.spiaa.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.http.HttpResponseCache;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.spiaa.R;
import com.spiaa.api.SpiaaService;
import com.spiaa.modelo.IsXLargeScreen;
import com.spiaa.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //definição da orientação das telas da aplicação
        if (!new IsXLargeScreen().isXLargeScreen(getApplicationContext())) {
            //set phones to portrait;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //Tablets como Landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        Button login = (Button) findViewById(R.id.botao_login);
        login.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    ///Depois que fez o login, da tela Main, não deixar voltar pra tela de login
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.botao_login) {

            //Fazer login
            TextView usuarioLogin = (TextView) findViewById(R.id.usuario_login);
            TextView senha = (TextView) findViewById(R.id.senha_login);
            Usuario usuario = new Usuario();
            usuario.setUsuario(usuarioLogin.getText().toString());
            usuario.setSenha(senha.getText().toString());

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://spiaa.herokuapp.com/")
                    .build();

            SpiaaService service = restAdapter.create(SpiaaService.class);

            /*List<Usuario> usuarioList = new ArrayList<Usuario>();
            Usuario user1 = new Usuario();
            user1.setNome("User1");

            Usuario user2 = new Usuario();
            user1.setNome("User2");

            usuarioList.add(user1);
            usuarioList.add(user2);*/


            /*service.loginList(usuarioList, new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    Toast.makeText(LoginActivity.this, "SUCCESS!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(LoginActivity.this, "ERROR!", Toast.LENGTH_LONG).show();
                }
            });*/


            service.login(usuario, new Callback<Usuario>() {
                @Override
                public void success(Usuario usuario, Response response) {
                    if (usuario != null) {
                        //Toast.makeText(LoginActivity.this, usuario.getUsuario(), Toast.LENGTH_LONG).show();

                        SharedPreferences.Editor dadosUsuario = getSharedPreferences("UsuarioLogado", MODE_PRIVATE).edit();
                        dadosUsuario.putString("email", usuario.getEmail());
                        dadosUsuario.putString("numero", usuario.getNumero());
                        dadosUsuario.putString("nome", usuario.getNome());
                        dadosUsuario.putString("senha", usuario.getSenha());
                        dadosUsuario.putString("tipo", usuario.getTipo());
                        dadosUsuario.putString("turma", usuario.getTurma());
                        dadosUsuario.putString("usuario", usuario.getUsuario());
                        dadosUsuario.putString("id", usuario.getId());
                        dadosUsuario.commit();

                        //Vai para a página inicial da aplicação
                        Intent intent = new Intent(LoginActivity.this, SincronizarActivity.class);
                        startActivity(intent);
                    } else {
                        Snackbar.make(v, "Falha no login. Verifique os dados de acesso.", Snackbar.LENGTH_LONG).show();
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(LoginActivity.this, "Erro no login!", Toast.LENGTH_LONG).show();
                }
            });


            /*service.login(usuario1, new Callback<String>() {

                @Override
                public void success(String resposta, Response response) {
                    Gson gson = new Gson();
                    Usuario userLogado = gson.fromJson(response.getBody().toString(), Usuario.class);

                    Toast.makeText(LoginActivity.this, userLogado.getNome(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Toast.makeText(LoginActivity.this, "Error, cadastro!", Toast.LENGTH_LONG).show();
                }
            });*/

        }
    }
}
