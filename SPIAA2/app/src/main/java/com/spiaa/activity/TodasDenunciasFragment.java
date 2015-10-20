package com.spiaa.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.spiaa.R;
import com.spiaa.adapter.DenunciaListaAdapter;
import com.spiaa.api.SpiaaService;
import com.spiaa.dao.BairroDAO;
import com.spiaa.dao.DenunciaDAO;
import com.spiaa.modelo.Bairro;
import com.spiaa.modelo.Denuncia;
import com.spiaa.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class TodasDenunciasFragment extends Fragment implements AdapterView.OnItemClickListener {
    private DenunciaListaAdapter adapter;
    private ListView listaDenuncias;
    private List<Denuncia> denunciasFinalizadas = new ArrayList<>();
    private TextView nenhumaDenunciaEncontrada;
    private ProgressDialog dialog;
    private List<Denuncia> denunciaList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_denuncias, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new DenunciaListaAdapter(getContext());
        listaDenuncias = (ListView) view.findViewById(R.id.lista_denuncias);
        nenhumaDenunciaEncontrada = (TextView) view.findViewById(R.id.nenhuma_denuncia);
    }

    @Override
    public void onResume() {
        super.onResume();

        /* Carregar denúncias na tela*/
        carregarDenunciasLocais();
        if (denunciaList != null) {
            if (denunciaList.isEmpty()) {
                nenhumaDenunciaEncontrada.setText("Nenhuma denúncia encontrada");
            }
        }
    }

    private void carregarDenunciasLocais() {
        /* Recuperar denúncias da base de dados local*/
        denunciaList = null;
        try {
            denunciaList = new DenunciaDAO(getContext()).selectAll();
        } catch (Exception e) {
            Log.e("SPIAA", "Erro ao tentar SELECT ALL Denúncias", e);
        }
        adapter.setLista(denunciaList);
        listaDenuncias.setAdapter(adapter);
        listaDenuncias.setOnItemClickListener(this);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_denuncias, menu);
        return true;
    }

    private void showProgressDialog() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Aguarde...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void hideProgressDialog() {
        dialog.dismiss();
    }

    private Usuario obterUsuarioLogado() {
        //Obtendo ID do usuário logado
        SharedPreferences dadosUsuario = getActivity().getSharedPreferences("UsuarioLogado", Activity.MODE_PRIVATE);
        Usuario agenteSaude = new Usuario();
        agenteSaude.setId(dadosUsuario.getLong("id", 0));
        return agenteSaude;
    }

    private SpiaaService getService() {
        //Configura RestAdapeter com dados do servidor e cria service
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://spiaa.herokuapp.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd")
                        .create()))
                .build();
        return restAdapter.create(SpiaaService.class);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_denuncias, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ///Fazer o sincronismo das denúncias com o web service
        switch (item.getItemId()) {
            case R.id.action_sync_denuncias:
                /*Processo de SINCRONIZAÇÃO */
                /*Mostrar progresso do sincronismo pro usuário*/
                showProgressDialog();

                //enviar denúncias finalizadas e atualizar listagem
                enviarDenuncias();

                //receber denúncias e atualizar listagem
                receberDenuncias();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void receberDenuncias() {
        getService().getDenuncias(obterUsuarioLogado(), new Callback<List<Denuncia>>() {
            @Override
            public void success(List<Denuncia> denunciaList, Response response) {
                if (denunciaList != null) {
                    try {
                        /*Inserir denúncias recebidas do servidor no Banco de dados local*/
                        DenunciaDAO dao = new DenunciaDAO(getContext());
                        for (Denuncia denuncia : denunciaList) {
                            dao.insert(denuncia);
                        }
                    } catch (Exception e) {
                        Log.e("SPIAA", "Erro ao inserir denúncia no banco de dados", e);
                    }
                }
                atualizaListaDeDenuncias();
                hideProgressDialog();
                showMessageSuccessSync();
            }

            @Override
            public void failure(RetrofitError error) {
                hideProgressDialog();
                showMessageErrorSyncReceive();
            }
        });
    }

    private void atualizaListaDeDenuncias() {
        List<Denuncia> denunciaList = null;
        try {
            denunciaList = new DenunciaDAO(getContext()).selectAll();
        } catch (Exception e) {
            Log.e("SPIAA", "Erro ao tentar SELECT ALL Denúncias", e);
        }
        adapter.setLista(denunciaList);
        adapter.notifyDataSetChanged();

        if (denunciaList != null) {
            if (denunciaList.isEmpty()) {
                nenhumaDenunciaEncontrada.setText("Nenhuma denúncia encontrada");
            } else {
                nenhumaDenunciaEncontrada.setText("");
            }
        }
    }

    private void enviarDenuncias() {
        //Obter denúncias do banco de dados local
        try {
            denunciasFinalizadas = new DenunciaDAO(getContext()).selectFinalizadas();
        } catch (Exception e) {
            Log.e("SPIAA", "Erro no SELECT de Denúncias Finalizadas", e);
        }
        if (denunciasFinalizadas != null) {
            getService().setDenuncias(denunciasFinalizadas, new Callback<String>() {
                @Override
                public void success(String resposta, Response response) {
                    try {
                        //Excluir finalizadas do banco local que foram enviadas pro servidor
                        boolean retorno = new DenunciaDAO(getContext()).deleteFinalizadas(denunciasFinalizadas);
                        if (retorno) {
                            atualizaListaDeDenuncias();
                        } else {
                            Log.e("SPIAA", "Erro ao tentar deletar denúncias no banco local");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    showMessageErrorSyncSend();
                }
            });
        } else {
            atualizaListaDeDenuncias();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Envia denúncia selecionada na listagem para a activity DenunciaActivity
        Intent intent = new Intent(getContext(), DenunciaActivity.class);
        Denuncia denuncia = (Denuncia) parent.getItemAtPosition(position);
        denuncia.setTitulo("Denúncia " + (position + 1));
        intent.putExtra("Denuncia", denuncia);
        startActivity(intent);
    }

    private void showMessageSuccessSync() {
        Snackbar.make(getView().findViewById(R.id.lista_denuncias), "Denúncias atualizadas com sucesso", Snackbar.LENGTH_LONG).show();
    }

    private void showMessageErrorSyncReceive() {
        Snackbar.make(getView().findViewById(R.id.lista_denuncias), "Erro ao receber denúncias", Snackbar.LENGTH_LONG).show();
    }

    private void showMessageErrorSyncSend() {
        Snackbar.make(getView().findViewById(R.id.lista_denuncias), "Erro ao enviar denúncias", Snackbar.LENGTH_LONG).show();
    }
}
