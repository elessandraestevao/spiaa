package com.spiaa.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.spiaa.R;

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
                showMessageUpdateSuccess();
                desabilitarCamposParaEdicao();
                habilitarVisibilidadeBotaoEditar();
                desabilitarVisibilidadeDeCamposSenha();

            }
        });
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
    }

    private void manipularVisibilidadeDeBotoes() {
        fabEditar.setVisibility(View.INVISIBLE);
        fabSalvar.setVisibility(View.VISIBLE);
    }

    private void showMessageUpdateSuccess() {
        Snackbar.make(getView().findViewById(R.id.frame_perfil), "Perfil atualizado com secesso", Snackbar.LENGTH_LONG).show();
    }
}
