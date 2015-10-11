package com.spiaa.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.spiaa.R;
import com.spiaa.adapter.DenunciaListaAdapter;
import com.spiaa.api.SpiaaService;
import com.spiaa.dao.DenunciaDAO;
import com.spiaa.modelo.Denuncia;
import com.spiaa.modelo.IsXLargeScreen;
import com.spiaa.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DenunciasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    DenunciaListaAdapter adapter = new DenunciaListaAdapter(this);
    ListView listaDenuncias;
    RestAdapter restAdapter;
    Usuario agenteSaude;
    SpiaaService service;
    private List<Denuncia> denunciasFinalizadas = new ArrayList<>();
    private boolean atualizado = false;
    private TextView nenhumaDenuncia;
    private ProgressDialog dialog;
    private boolean sincronizado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias);

        //definição da orientação das telas da aplicação
        if (!new IsXLargeScreen().isXLargeScreen(getApplicationContext())) {
            //set phones to portrait;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //Tablets como Landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        listaDenuncias = (ListView) findViewById(R.id.lista_denuncias);
        //TextView para mensagem de Nenuma denúncia encontrada
        nenhumaDenuncia = (TextView) findViewById(R.id.nenhuma_denuncia);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Recuperar denúncias do banco de dados local
        List<Denuncia> denunciaList = null;
        try {
            denunciaList = new DenunciaDAO(DenunciasActivity.this).selectAll();
        } catch (Exception e) {
            Log.e("SPIAA", "Erro ao tentar SELECT ALL Denúncias", e);
        }
        adapter.setLista(denunciaList);
        listaDenuncias.setAdapter(adapter);
        listaDenuncias.setOnItemClickListener(this);

        //Ao carregar a tela, sem utilizar a sincronização
        if (denunciaList.isEmpty()) {
            nenhumaDenuncia.setText("Nenhuma denúncia encontrada");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_denuncias, menu);
        return true;
    }

    private void showDialogAguarde() {
        dialog = new ProgressDialog(DenunciasActivity.this);
        dialog.setMessage("Aguarde...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ///Fazer o sincronismo das denúncias com o web service
        switch (item.getItemId()) {
            case R.id.action_sync_denuncias:
                //SINCRONIZAÇÃO
                //Mostrar progresso do sincronismo pro usuário
                showDialogAguarde();

                //Obtendo ID do usuário logado
                SharedPreferences dadosUsuario = getSharedPreferences("UsuarioLogado", MODE_PRIVATE);
                agenteSaude = new Usuario();
                agenteSaude.setId(dadosUsuario.getLong("id", 0));

                //Configurando Web Service
                restAdapter = new RestAdapter.Builder()
                        .setEndpoint("http://spiaa.herokuapp.com")
                        .build();
                service = restAdapter.create(SpiaaService.class);

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
        service.getDenuncias(agenteSaude, new Callback<List<Denuncia>>() {
            @Override
            public void success(List<Denuncia> denunciaList, Response response) {
                if (denunciaList != null) {
                    //Inserir denúncias recebidas do servidor no Banco de dados local
                    try {
                        DenunciaDAO dao = new DenunciaDAO(DenunciasActivity.this);
                        for (Denuncia denuncia : denunciaList) {
                            dao.insert(denuncia);
                        }
                    } catch (Exception e) {
                        Log.e("SPIAA", "Erro ao inserir denúncia no banco de dados", e);
                    }
                }
                atualizaListaDeDenuncias();
                //Retirar da tela o progresso do sincronismo
                dialog.dismiss();
                //Mostra mensagem de sincronismo realizado ao usuário
                mostraMensagemSincronismo();
            }

            @Override
            public void failure(RetrofitError error) {
                //Retirar da tela o progresso do sincronismo
                dialog.dismiss();
                Snackbar.make(findViewById(R.id.lista_denuncias), "Erro ao receber denúncias", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void atualizaListaDeDenuncias() {
        List<Denuncia> denunciaList = null;
        try {
            denunciaList = new DenunciaDAO(DenunciasActivity.this).selectAll();
            //atualizado = true;
        } catch (Exception e) {
            Log.e("SPIAA", "Erro ao tentar SELECT ALL Denúncias", e);
        }
        //Atualiza a lista de denúncias
        adapter.setLista(denunciaList);
        adapter.notifyDataSetChanged();

        if (denunciaList.isEmpty()) {
            nenhumaDenuncia.setText("Nenhuma denúncia encontrada");
        } else {
            nenhumaDenuncia.setText("");
        }

    }

    private void mostraMensagemSincronismo() {
        Snackbar.make(findViewById(R.id.lista_denuncias), "Denúncias atualizadas com sucesso", Snackbar.LENGTH_LONG).show();
    }

    private void enviarDenuncias() {
        //Obter denúncias do banco de dados local
        try {
            denunciasFinalizadas = new DenunciaDAO(DenunciasActivity.this).selectFinalizadas();
        } catch (Exception e) {
            Log.e("SPIAA", "Erro no SELECT de Denúncias Finalizadas", e);
        }
        if (!denunciasFinalizadas.isEmpty()) {
            service.setDenuncias(denunciasFinalizadas, new Callback<String>() {
                @Override
                public void success(String resposta, Response response) {
                    try {
                        //Excluir finalizadas do banco local que foram enviadas pro servidor
                        boolean retorno = new DenunciaDAO(DenunciasActivity.this).deleteFinalizadas(denunciasFinalizadas);
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
                    Snackbar.make(findViewById(R.id.lista_denuncias), "Erro ao enviar denúncias", Snackbar.LENGTH_LONG).show();
                }
            });
        } else {
            atualizaListaDeDenuncias();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(DenunciasActivity.this, DenunciaActivity.class);
        Denuncia denuncia = (Denuncia) parent.getItemAtPosition(position);
        denuncia.setTitulo("Denúncia " + (position + 1));
        intent.putExtra("Denuncia", denuncia);
        startActivity(intent);
    }
}
