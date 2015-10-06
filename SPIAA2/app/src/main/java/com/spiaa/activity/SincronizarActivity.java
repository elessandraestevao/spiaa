package com.spiaa.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.spiaa.R;
import com.spiaa.api.SpiaaService;
import com.spiaa.builder.AtividadeBuilder;
import com.spiaa.builder.BoletimDiarioBuilder;
import com.spiaa.dao.BairroDAO;
import com.spiaa.dados.DatabaseHelper;
import com.spiaa.dao.DenunciaDAO;
import com.spiaa.modelo.Atividade;
import com.spiaa.modelo.AtividadeCriadouro;
import com.spiaa.modelo.Bairro;
import com.spiaa.modelo.TratamentoAntiVetorial;
import com.spiaa.modelo.Denuncia;
import com.spiaa.modelo.IsXLargeScreen;
import com.spiaa.modelo.Usuario;

import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SincronizarActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView navigationView;
    RestAdapter restAdapter;
    Usuario agenteSaude;
    SpiaaService service;
    List<Denuncia> denuncias;

    //teste com banco SQLite
    private DatabaseHelper dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincronizar);

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
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
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
                        Intent intent1 = new Intent(SincronizarActivity.this, PerfilEditActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_boletim:
                        Intent intent2 = new Intent(SincronizarActivity.this, TodosBoletinsDiariosActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_denuncias:
                        Intent intent3 = new Intent(SincronizarActivity.this, DenunciasActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_sincronizar:
                        Intent intent4 = new Intent(SincronizarActivity.this, SincronizarActivity.class);
                        startActivity(intent4);
                        break;

                }
                return true;
            }
        });

        //SINCRONIZAÇÃO
        SharedPreferences dadosUsuario = getSharedPreferences("UsuarioLogado", MODE_PRIVATE);
        agenteSaude = new Usuario();
        agenteSaude.setId(dadosUsuario.getLong("id", 0));
        restAdapter = new RestAdapter.Builder()
                //.setEndpoint("http://192.168.4.97:8084/Spiaa")
                .setEndpoint("http://spiaa.herokuapp.com")
                .build();
        service = restAdapter.create(SpiaaService.class);

        //Função de sincronização RECEBER BAIRROS
        RelativeLayout receberBairros = (RelativeLayout) findViewById(R.id.receber_bairros);
        receberBairros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                service.getBairros(agenteSaude, new Callback<List<Bairro>>() {
                    @Override
                    public void success(List<Bairro> bairroList, Response response) {
                        if (bairroList != null) {
                            //Inserir bairros no Banco de dados
                            try {
                                BairroDAO dao = new BairroDAO(SincronizarActivity.this);
                                for (Bairro bairro : bairroList) {
                                    dao.insert(bairro);
                                }
                                Snackbar.make(v, "Bairros recebidos com sucesso!", Snackbar.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Log.e("SPIAA", "Erro ao inserir bairro no banco de dados", e);
                            }
                        }else{
                            Snackbar.make(v, "Nenhum bairro encontrado.", Snackbar.LENGTH_LONG).show();
                        }
                        //Testar select no banco
                        BairroDAO dao = new BairroDAO(SincronizarActivity.this);
                        Bairro bairro = new Bairro();
                        Bairro bairro2 = new Bairro();
                        bairro.setId(9L);
                        try {
                            bairro2 = dao.select(bairro);
                            Log.d("SPIAA", "Select Bairro ok!");
                            Snackbar.make(v, bairro2.getNome(), Snackbar.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Snackbar.make(v, "Erro ao receber bairros.", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });

        //Função de sincronização RECEBER DENÚNCIAS
        RelativeLayout receberDenuncias = (RelativeLayout) findViewById(R.id.receber_denuncias);
        receberDenuncias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                service.getDenuncias(agenteSaude, new Callback<List<Denuncia>>() {
                    @Override
                    public void success(List<Denuncia> denunciaList, Response response) {
                        if (denunciaList != null) {
                            //Inserir denúncias no Banco de dados
                            try {
                                DenunciaDAO dao = new DenunciaDAO(SincronizarActivity.this);
                                for (Denuncia denuncia : denunciaList) {
                                    dao.insert(denuncia);
                                }
                                Snackbar.make(v, "Denúncias recebidas com sucesso!", Snackbar.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Log.e("SPIAA", "Erro ao inserir denúncia no banco de dados", e);
                            }
                        } else {
                            Snackbar.make(v, "Nenhuma denúncia encontrada.", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Snackbar.make(v, "Erro ao receber denúncias.", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });

        //Função de sincronização ENVIAR BOLETIM DIÁRIO
        RelativeLayout enviarBoletimDiario = (RelativeLayout) findViewById(R.id.enviar_boletim_diario);
        enviarBoletimDiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bairro bairro = new Bairro();
                bairro.setId(1L);
                TratamentoAntiVetorial tratamentoAntiVetorial = new TratamentoAntiVetorial();
                tratamentoAntiVetorial.setId(1L);
                tratamentoAntiVetorial.setCategoria("Sede");
                tratamentoAntiVetorial.setData(new Date().toString());
                tratamentoAntiVetorial.setNumero("123" + 1);
                tratamentoAntiVetorial.setSemana("L" + "Tratamento Vetorial");
                tratamentoAntiVetorial.setTurma("5960/L");
                tratamentoAntiVetorial.setNumeroAtividade("1223");
                tratamentoAntiVetorial.setTipoAtividade("Atividade Tipo");


                //List<TratamentoAntiVetorial> tratamentoAntiVetorialList = new BoletimDiarioBuilder().geraBoletins(5);
                //List<Atividade> atividadeList = null;
                //List<AtividadeCriadouro>

                //for (TratamentoAntiVetorial tratamentoAntiVetorial : tratamentoAntiVetorialList
                        //) {
                    //tratamentoAntiVetorial.setAtividades(new AtividadeBuilder().geraAtividades(3));
                    //tratamentoAntiVetorial.setAtividades(atividadeList);
                    tratamentoAntiVetorial.setBairro(bairro);
                    tratamentoAntiVetorial.setUsuario(agenteSaude);
               // }

                service.setBoletim(tratamentoAntiVetorial, new Callback<String>() {
                    @Override
                    public void success(String resposta, Response response) {
                        Toast.makeText(SincronizarActivity.this, resposta, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(SincronizarActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        //Função de sincronização ENVIAR DENÚNCIAS
        RelativeLayout enviarDenuncias = (RelativeLayout) findViewById(R.id.enviar_denuncias);
        enviarDenuncias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                denuncias.get(0).setObservacao("UPDATE OBS");
                denuncias.get(0).setStatus("Fechado");
                service.setDenuncias(denuncias, new Callback<String>() {
                    @Override
                    public void success(String resposta, Response response) {
                        Toast.makeText(SincronizarActivity.this, resposta, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(SincronizarActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sincronizar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
