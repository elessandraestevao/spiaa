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
import android.widget.Toast;

import com.spiaa.R;
import com.spiaa.adapter.BoletimListaAdapter;
import com.spiaa.api.APIManager;
import com.spiaa.api.SpiaaService;
import com.spiaa.dao.BairroDAO;
import com.spiaa.dao.CriadouroDAO;
import com.spiaa.dao.InseticidaDAO;
import com.spiaa.dao.TipoImoveisDAO;
import com.spiaa.dao.TratamentoAntiVetorialDAO;
import com.spiaa.modelo.Bairro;
import com.spiaa.modelo.Criadouro;
import com.spiaa.modelo.Inseticida;
import com.spiaa.modelo.TipoImoveis;
import com.spiaa.modelo.TratamentoAntiVetorial;
import com.spiaa.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TodosBoletinsDiariosFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, com.melnykov.fab.ScrollDirectionListener {
    public static boolean SYNC_REALIZADO = false;
    public static boolean NOVO_BOLETIM = false;
    private BoletimListaAdapter adapter;
    private ListView listaBoletins;
    private com.melnykov.fab.FloatingActionButton fabCriar;
    private ProgressDialog dialog;
    private List<TratamentoAntiVetorial> tratamentoAntiVetorialList;
    private TextView nenhumBoletim;
    private boolean sincronismoOk = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_todos_boletins_diarios, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sync_boletins:
                enviarBoletinsDiarios();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void enviarBoletinsDiarios() {
        try {
            final List<TratamentoAntiVetorial> tratamentoAntiVetorialList = new TratamentoAntiVetorialDAO(getContext()).selectAllConcluidos();
            if (!tratamentoAntiVetorialList.isEmpty()) {
                APIManager.getInstance().getService().setBoletim(tratamentoAntiVetorialList, new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT);
                        TratamentoAntiVetorialDAO dao = new TratamentoAntiVetorialDAO(getContext());
                        try {
                            for (TratamentoAntiVetorial tav : tratamentoAntiVetorialList) {
                                //Após testes do agente concluídos, deletar do banco os dados, descomentar linha abaixo
                                //dao.delete(tav.getId());
                                dao.updateStatusEnviado(tav);
                            }
                            atualizaListaDeBoletins();
                            Snackbar.make(getView().findViewById(R.id.frame_boletins), "Boletins Diários enviados com sucesso", Snackbar.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e("SPIAA", "Erro no DELETE Boletim Diário", e);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Snackbar.make(getView().findViewById(R.id.frame_boletins), "Erro ao enviar Boletins Diários", Snackbar.LENGTH_LONG).show();
                    }
                });
            }else{
                Snackbar.make(getView().findViewById(R.id.frame_boletins), "Nenhum Boletim Diário concluído", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("SPIAA", "Erro ao enviar Boletins Diários para Servidor", e);
        }
    }

    private void atualizaListaDeBoletins() {
        try {
            tratamentoAntiVetorialList = new TratamentoAntiVetorialDAO(getContext()).selectAll();
        } catch (Exception e) {
            Log.e("SPIAA", "Erro ao tentar SELECT ALL Boletins", e);
        }
        adapter.setLista(tratamentoAntiVetorialList);
        adapter.notifyDataSetChanged();

        verificaSeTemBoletim();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todos_boletins_diarios, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nenhumBoletim = (TextView) view.findViewById(R.id.nenhum_boletim);

        adapter = new BoletimListaAdapter(getContext());

        //Vincular botão de criar novo Boletim à lista
        listaBoletins = (ListView) view.findViewById(R.id.lista_boletins);
        listaBoletins.setOnItemClickListener(this);
        fabCriar = (com.melnykov.fab.FloatingActionButton) view.findViewById(R.id.fab_criar_boletim);
        fabCriar.attachToListView(listaBoletins);
        fabCriar.setOnClickListener(this);

        if (!SYNC_REALIZADO) {
            doSync();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //Preencher lista com os Boletins Diários contidos no banco de dados local
        try {
            tratamentoAntiVetorialList = new TratamentoAntiVetorialDAO(getContext()).selectAll();
            adapter.setLista(tratamentoAntiVetorialList);
        } catch (Exception e) {
            Log.e("SPIAA", "Erro ao tentar SELECT ALL Tratamento anti-vetorial", e);
        }
        listaBoletins.setAdapter(adapter);

        verificaSeTemBoletim();
    }

    private void verificaSeTemBoletim(){
        if (tratamentoAntiVetorialList != null) {
            if (tratamentoAntiVetorialList.isEmpty()) {
                nenhumBoletim.setText("Nenhum Boletim Diário");
            } else {
                nenhumBoletim.setText("");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Enviar dados do Boletim selecionado para a activity BoletimDiarioActivity
        Intent intent = new Intent(getContext(), BoletimDiarioActivity.class);
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
                Intent intent = new Intent(getContext(), BoletimDiarioActivity.class);
                startActivity(intent);
                //Finalizar esta Activity para resetar CONSTANTE NOVO_BOLETIM para False
                //finish();
        }
    }

    private void doSync() {
        /*Fazer sincronização dos dados ao logar, automaticamente*/
        showProgressDialog();

        try {
            APIManager.getInstance().getService().getBairrosQuarteiroes(getUsuarioLogado(), new Callback<List<Bairro>>() {
                @Override
                public void success(List<Bairro> bairroList, Response response) {
                    if (bairroList != null) {
                        //Inserir bairros no Banco de dados
                        try {
                            BairroDAO dao = new BairroDAO(getContext());
                            for (Bairro bairro : bairroList) {
                                if ((dao.delete(bairro.getId()) == 1) || dao.select(bairro) == null) {
                                    dao.insert(bairro);

                                }
                            }
                        } catch (Exception e) {
                            Log.e("SPIAA", "Erro ao inserir bairro no banco de dados", e);
                        }
                    } else {
                        Snackbar.make(getView().findViewById(R.id.frame_boletins), "Nenhum bairro encontrado.", Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    sincronismoOk = false;
                }
            });
        } catch (Exception e) {
            Log.e("SPIAA", "Erro na obtenção do Service API", e);
        }

        try {
            APIManager.getInstance().getService().getTiposImoveis(getUsuarioLogado(), new Callback<List<TipoImoveis>>() {
                @Override
                public void success(List<TipoImoveis> tipoImovelList, Response response) {
                    if (tipoImovelList != null) {
                        TipoImoveisDAO dao = new TipoImoveisDAO(getContext());
                        for (TipoImoveis tipoImovel : tipoImovelList) {
                            try {
                                if ((dao.delete(tipoImovel.getId()) == 1) || (dao.select(tipoImovel) == null)) {
                                    dao.insert(tipoImovel);
                                }
                            } catch (Exception e) {
                                Log.e("SPIAA", "Erro ao inserir tipo de imóvel no banco local", e);
                            }
                        }
                        hideProgressDialog();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    sincronismoOk = false;
                }
            });
        } catch (Exception e) {
            Log.e("SPIAA", "Erro na obtenção do Service API", e);
        }

        try {
            APIManager.getInstance().getService().getInseticidas(getUsuarioLogado(), new Callback<List<Inseticida>>() {
                @Override
                public void success(List<Inseticida> inseticidaList, Response response) {
                    InseticidaDAO dao = new InseticidaDAO(getContext());
                    if (inseticidaList != null) {
                        for (Inseticida inseticida : inseticidaList) {
                            try {
                                if ((dao.delete(inseticida.getId()) == 1) || (dao.select(inseticida) == null)) {
                                    dao.insert(inseticida);
                                }
                            } catch (Exception e) {
                                Log.e("SPIAA", "Erro ao inserir inseticida no banco local", e);
                            }
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    sincronismoOk = false;
                }
            });
        } catch (Exception e) {
            Log.e("SPIAA", "Erro na obtenção do Service API", e);
        }

        try {
            APIManager.getInstance().getService().getCriadouros(getUsuarioLogado(), new Callback<List<Criadouro>>() {
                @Override
                public void success(List<Criadouro> criadouroList, Response response) {
                    if (criadouroList != null) {
                        CriadouroDAO dao = new CriadouroDAO(getContext());
                        for (Criadouro criadouro : criadouroList) {
                            try {
                                if ((dao.delete(criadouro.getId()) == 1) || (dao.select(criadouro) == null)) {
                                    dao.insert(criadouro);
                                }
                            } catch (Exception e) {
                                Log.e("SPIAA", "Erro ao inserir criadouro no banco local", e);
                            }
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    sincronismoOk = false;
                }
            });
        } catch (Exception e) {
            Log.e("SPIAA", "Erro na obtenção do Service API", e);
        }
        hideProgressDialog();
        /*Final do sincronismo*/
        showMessageFinalizeSync();
        SYNC_REALIZADO = true;
    }

    private void showMessageFinalizeSync() {
        if (sincronismoOk) {
            Snackbar.make(getView().findViewById(R.id.frame_boletins), "Dados recebidos com sucesso", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(getView().findViewById(R.id.frame_boletins), "Ocorreu um erro ao receber dados", Snackbar.LENGTH_LONG).show();
        }

    }

    private void hideProgressDialog() {
        dialog.dismiss();
    }

    private void showProgressDialog() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Aguarde...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    private Usuario getUsuarioLogado() {
        //Obtendo ID do usuário logado
        SharedPreferences dadosUsuario = getContext().getSharedPreferences("UsuarioLogado", Activity.MODE_PRIVATE);
        Usuario agenteSaude = new Usuario();
        agenteSaude.setId(dadosUsuario.getLong("id", 0));
        return agenteSaude;
    }
}
