package com.spiaa.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.spiaa.R;
import com.spiaa.adapter.BoletimListaAdapter;
import com.spiaa.api.SpiaaService;
import com.spiaa.builder.BoletimDiarioBuilder;
import com.spiaa.dao.BairroDAO;
import com.spiaa.dao.TratamentoAntiVetorialDAO;
import com.spiaa.modelo.Bairro;
import com.spiaa.modelo.TratamentoAntiVetorial;
import com.spiaa.modelo.IsXLargeScreen;
import com.spiaa.modelo.Usuario;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TodosBoletinsDiariosActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, com.melnykov.fab.ScrollDirectionListener {

    public static boolean NOVO_BOLETIM = false;
    private BoletimListaAdapter adapter = new BoletimListaAdapter(this);
    private ListView listaBoletins;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView navigationView;
    private com.melnykov.fab.FloatingActionButton fabCriar;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_boletins_diarios);

        //definição da orientação das telas da aplicação
        if (!new IsXLargeScreen().isXLargeScreen(getApplicationContext())) {
            //set phones to portrait;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //Tablets como Landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        //Criar ícone de Menu na Toolbar
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_perfil:
                        Intent intent1 = new Intent(TodosBoletinsDiariosActivity.this, PerfilEditActivity.class);
                        startActivity(intent1);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_boletim:
                        Intent intent2 = new Intent(TodosBoletinsDiariosActivity.this, TodosBoletinsDiariosActivity.class);
                        startActivity(intent2);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_denuncias:
                        Intent intent3 = new Intent(TodosBoletinsDiariosActivity.this, DenunciasActivity.class);
                        startActivity(intent3);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_sincronizar:
                        Intent intent4 = new Intent(TodosBoletinsDiariosActivity.this, SincronizarActivity.class);
                        startActivity(intent4);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
                return true;
            }
        });

        //Vincular botão de criar novo Boletim à lista
        listaBoletins = (ListView) findViewById(R.id.lista_boletins);
        fabCriar = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fab_criar_boletim);
        fabCriar.attachToListView(listaBoletins);
        fabCriar.setOnClickListener(this);

        doSync();

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Preencher lista com os Boletins Diários contidos no banco de dados local
        try {
            adapter.setLista(new TratamentoAntiVetorialDAO(TodosBoletinsDiariosActivity.this).selectAll());
        } catch (Exception e) {
            Log.e("SPIAA", "Erro ao tentar SELECT ALL Tratamento anti-vetorial", e);
        }
        listaBoletins.setAdapter(adapter);
        listaBoletins.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Enviar dados do Boletim selecionado para a activity BoletimDiarioActivity
        Intent intent = new Intent(TodosBoletinsDiariosActivity.this, BoletimDiarioActivity.class);
        TratamentoAntiVetorial tratamentoAntiVetorial = (TratamentoAntiVetorial) parent.getItemAtPosition(position);
        tratamentoAntiVetorial.setTitulo("Boletim Diário " + (position + 1));
        intent.putExtra("Boletim", tratamentoAntiVetorial);
        startActivity(intent);
    }

    @Override
    public void onScrollDown() {
        fabCriar.show(true); // Show with an animation
        fabCriar.hide(true); //Hide with animation
    }

    @Override
    public void onScrollUp() {
        fabCriar.show(true); // Show with an animation
        fabCriar.hide(true); //Hide with animation
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_criar_boletim:
                 /*Constante utilizada na Activity BoletimDiarioActivity para distinguir tela para
                    Novo e para quando selecionou um Boletim na listagem
                */
                NOVO_BOLETIM = true;
                Intent intent = new Intent(TodosBoletinsDiariosActivity.this, BoletimDiarioActivity.class);
                startActivity(intent);
                //Finalizar esta Activity para resetar CONSTANTE NOVO_BOLETIM para False
                finish();
        }
    }

    private void doSync() {
        /*Fazer sincronização dos dados ao logar, automaticamente*/
        showProgressDialog();

        getService().getBairrosQuarteiroes(getUsuarioLogado(), new Callback<List<Bairro>>() {
            @Override
            public void success(List<Bairro> bairroList, Response response) {
                if (bairroList != null) {
                    boolean insercaoOk = false;
                    //Inserir bairros no Banco de dados
                    try {
                        BairroDAO dao = new BairroDAO(TodosBoletinsDiariosActivity.this);
                        for (Bairro bairro : bairroList) {
                            if ((dao.delete(bairro.getId()) == 1) || dao.select(bairro) == null) {
                                dao.insert(bairro);
                                insercaoOk = true;
                            }
                        }
                        if (insercaoOk) {
                            //Retirar da tela o progresso do sincronismo
                            dialog.dismiss();
                            Snackbar.make(findViewById(R.id.linear_boletins), "Bairros recebidos com sucesso!", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(findViewById(R.id.linear_boletins), "Erro no recebimento dos Bairros", Snackbar.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Log.e("SPIAA", "Erro ao inserir bairro no banco de dados", e);
                    }
                } else {
                    //Retirar da tela o progresso do sincronismo
                    dialog.dismiss();
                    Snackbar.make(findViewById(R.id.linear_boletins), "Nenhum bairro encontrado.", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                //Retirar da tela o progresso do sincronismo
                dialog.dismiss();
                Snackbar.make(findViewById(R.id.linear_boletins), "Erro ao receber bairros.", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void showProgressDialog() {
        dialog = new ProgressDialog(TodosBoletinsDiariosActivity.this);
        dialog.setMessage("Aguarde...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    private SpiaaService getService() {
        //Configura RestAdapeter com dados do servidor e cria service
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://spiaa.herokuapp.com")
                .build();
        SpiaaService service = restAdapter.create(SpiaaService.class);
        return service;
    }

    private Usuario getUsuarioLogado() {
        //Obtendo ID do usuário logado
        SharedPreferences dadosUsuario = getSharedPreferences("UsuarioLogado", MODE_PRIVATE);
        Usuario agenteSaude = new Usuario();
        agenteSaude.setId(dadosUsuario.getLong("id", 0));
        return agenteSaude;
    }
}
