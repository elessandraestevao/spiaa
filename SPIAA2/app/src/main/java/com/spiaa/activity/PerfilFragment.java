package com.spiaa.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spiaa.R;
import com.spiaa.api.APIManager;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.dao.UsuarioDAO;
import com.spiaa.modelo.Usuario;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PerfilFragment extends Fragment {
    private FloatingActionButton fabEditar;
    private FloatingActionButton fabSalvar;
    private TextInputLayout tilSenha;
    private TextInputLayout tilSenhaConfirm;
    private EditText usuario;
    private EditText nome;
    private EditText email;
    private EditText numero;
    private EditText turma;
    private EditText senha;
    private EditText senhaConfirm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hideKeyboard();

        usuario = (EditText) view.findViewById(R.id.perfil_usuario);
        nome = (EditText) view.findViewById(R.id.perfil_nome);
        email = (EditText) view.findViewById(R.id.perfil_email);
        numero = (EditText) view.findViewById(R.id.perfil_numero);
        turma = (EditText) view.findViewById(R.id.perfil_turma);
        tilSenha = (TextInputLayout) view.findViewById(R.id.message_senha);
        tilSenhaConfirm = (TextInputLayout) view.findViewById(R.id.message_confirmacao_senha);
        senha = (EditText) view.findViewById(R.id.perfil_senha);
        senhaConfirm = (EditText) view.findViewById(R.id.perfil_confirmacao_senha);
        fabEditar = (FloatingActionButton) view.findViewById(R.id.botao_editar_perfil);
        fabSalvar = (FloatingActionButton) view.findViewById(R.id.botao_salvar_perfil);

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habilitarCamposParaEdicao();
                habilitarVisibilidadeDeCamposSenha();
                manipularVisibilidadeDeBotoes();
            }
        });

        fabSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enviaUsuarioAtualizadoParaServidor(getDadosUsuario());


            }
        });
    }

    private void enviaUsuarioAtualizadoParaServidor(final Usuario usuario) {
        try {
            APIManager.getInstance().getService().alterarDadosUsuario(usuario, new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    try {
                        new UsuarioDAO(getContext()).update(usuario);
                        atualiarSharedPreferences(usuario);
                        showMessageUpdateSuccess();
                        desabilitarCamposParaEdicao();
                        habilitarVisibilidadeBotaoEditar();
                        removerSenhasDigitadas();
                        desabilitarVisibilidadeDeCamposSenha();
                    } catch (Exception e) {
                        Log.e("SPIAA", "Ero ao Update Usuário com perfil atualizado", e);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Snackbar.make(getView().findViewById(R.id.frame_perfil), "Falha ao atualizar perfil", Snackbar.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("SPIAA", "Erro ao obter conexão com API via APIManager", e);
        }
    }

    private void removerSenhasDigitadas(){
        senha.setText("");
        senhaConfirm.setText("");
    }

    private void atualiarSharedPreferences(Usuario agente) {
        SharedPreferences.Editor dadosUsuario = getActivity().getSharedPreferences("UsuarioLogado", Context.MODE_PRIVATE).edit();
        dadosUsuario.putString("email", agente.getEmail());
        dadosUsuario.putString("numero", agente.getNumero());
        dadosUsuario.putString("nome", agente.getNome());
        dadosUsuario.putString("tipo", agente.getTipo());
        dadosUsuario.putString("turma", agente.getTurma());
        dadosUsuario.putString("usuario", agente.getUsuario());
        dadosUsuario.putLong("id", agente.getId());
        dadosUsuario.commit();
    }

    private Usuario getDadosUsuario() {
        Usuario user = new Usuario();

        //Verificar e validar senhas digitadas
        if (senha.getText().toString().isEmpty() || senhaConfirm.getText().toString().isEmpty()
                || senha.getText().toString().trim().equals("") || senhaConfirm.getText().toString().trim().equals("")) {
            Snackbar.make(getView().findViewById(R.id.frame_perfil), "Senha não pode estar em branco", Snackbar.LENGTH_SHORT).show();
            senha.requestFocus();
        } else if (senha.getText().length() < 8) {
            Snackbar.make(getView().findViewById(R.id.frame_perfil), "A senha deve conter no mínimo 8 caracteres", Snackbar.LENGTH_SHORT).show();
            senha.requestFocus();
        } else if (!senha.getText().toString().equals(senhaConfirm.getText().toString())) {
            Snackbar.make(getView().findViewById(R.id.frame_perfil), "Senhas digitadas não conferem", Snackbar.LENGTH_SHORT).show();
            senha.setText("");
            senhaConfirm.setText("");
            senha.requestFocus();
        } else {
            user.setEmail(email.getText().toString());
            user.setNome(nome.getText().toString());
            user.setSenha(senha.getText().toString());
            user.setTipo("AGS");

            //Dados que não podem ser alterados pelo usuário
            SharedPreferences dados = dadosDoUsuarioLogado();
            user.setTurma(dados.getString("turma", ""));
            user.setNumero(dados.getString("numero", ""));
            user.setUsuario(dados.getString("usuario", ""));
            user.setId(dados.getLong("id", 0));
        }

        return user;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_perfil, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_exit) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage("Ao sair, todos os dados não sincronizados serão perdidos. Deseja continuar?");
            dialog.setPositiveButton("SAIR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new DatabaseHelper(getContext()).deleteDatabase();
                    //Para realizar o sync de dados ao logar novamente
                    TodosBoletinsDiariosFragment.SYNC_REALIZADO = false;
                    vaiParaTelaDeLogin();
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void mudaCorTextoBotaoDialog(AlertDialog alertDialog) {
        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(getResources().getColor(R.color.red_padrao));

        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(getResources().getColor(R.color.red_padrao));
    }

    private void vaiParaTelaDeLogin() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (dadosDoUsuarioLogado() != null) {
            carregarDadosDoUsuario(dadosDoUsuarioLogado());
        }
    }

    private void hideKeyboard() {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private SharedPreferences dadosDoUsuarioLogado() {
        return getContext().getSharedPreferences("UsuarioLogado", Activity.MODE_PRIVATE);
    }

    private void carregarDadosDoUsuario(SharedPreferences dadosUsuario) {
        usuario.setText(dadosUsuario.getString("usuario", ""));
        nome.setText(dadosUsuario.getString("nome", ""));
        email.setText(dadosUsuario.getString("email", ""));
        numero.setText(dadosUsuario.getString("numero", ""));
        turma.setText(dadosUsuario.getString("turma", ""));
    }

    private void habilitarCamposParaEdicao() {
        nome.setInputType(1);
        email.setInputType(1);
    }

    private void desabilitarCamposParaEdicao() {
        nome.setInputType(0);
        email.setInputType(0);
    }

    private void habilitarVisibilidadeDeCamposSenha() {
        tilSenha.setVisibility(View.VISIBLE);
        tilSenhaConfirm.setVisibility(View.VISIBLE);
    }

    private void desabilitarVisibilidadeDeCamposSenha() {
        tilSenha.setVisibility(View.INVISIBLE);
        tilSenhaConfirm.setVisibility(View.INVISIBLE);
    }

    private void habilitarVisibilidadeBotaoEditar() {
        fabEditar.setVisibility(View.VISIBLE);
        fabSalvar.setVisibility(View.INVISIBLE);
    }

    private void manipularVisibilidadeDeBotoes() {
        fabEditar.setVisibility(View.INVISIBLE);
        fabSalvar.setVisibility(View.VISIBLE);
    }

    private void showMessageUpdateSuccess() {
        Snackbar.make(getView().findViewById(R.id.frame_perfil), "Perfil atualizado com sucesso", Snackbar.LENGTH_LONG).show();
    }
}
