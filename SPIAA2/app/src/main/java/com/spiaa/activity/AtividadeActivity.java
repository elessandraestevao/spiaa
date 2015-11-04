package com.spiaa.activity;


import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.spiaa.R;
import com.spiaa.dao.AtividadeDAO;
import com.spiaa.dao.CriadouroDAO;
import com.spiaa.dao.InseticidaDAO;
import com.spiaa.dao.QuarteiraoDAO;
import com.spiaa.dao.TipoImoveisDAO;
import com.spiaa.modelo.Atividade;
import com.spiaa.modelo.AtividadeCriadouro;
import com.spiaa.modelo.AtividadeInseticida;
import com.spiaa.modelo.Criadouro;
import com.spiaa.modelo.Inseticida;
import com.spiaa.modelo.IsXLargeScreen;
import com.spiaa.modelo.Quarteirao;
import com.spiaa.modelo.TipoImoveis;
import com.spiaa.modelo.TratamentoAntiVetorial;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AtividadeActivity extends AppCompatActivity implements View.OnClickListener {
    private Long IdBoletimDiario = TratamentoAntiVetorial.ID_BOLETIM;
    public static Date DATA_INICIAL;
    private Atividade atividade = new Atividade();
    private Spinner spinnerQuarteiroes;
    private Spinner spinnerTiposImoveis;
    private EditText endereco;
    private EditText numeroEndereco;
    private RadioGroup radioGroupObservacoes;
    private RadioButton recebido;
    private RadioButton fechado;
    private RadioButton resgatado;
    private List<Criadouro> criadouroList;
    private List<Quarteirao> quarteiraoList;
    private List<TipoImoveis> tipoImoveisList;
    private List<Inseticida> inseticidaList;
    private LinearLayout linearLayoutCriadouros;
    private LinearLayout linearLayoutInseticidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);

        setOrientationOfScreen();
        hideKeyboard();

        endereco = (EditText) findViewById(R.id.endereco_atividade);
        numeroEndereco = (EditText) findViewById(R.id.numero_endereco_atividade);

        radioGroupObservacoes = (RadioGroup) findViewById(R.id.radio_group_observacoes);
        recebido = (RadioButton) findViewById(R.id.radio_recebido);
        fechado = (RadioButton) findViewById(R.id.radio_fechado);
        resgatado = (RadioButton) findViewById(R.id.radio_resgatado);

        spinnerQuarteiroes = (Spinner) findViewById(R.id.dropdown_quarteiroes);

        Button botaoCriadouros = (Button) findViewById(R.id.botao_criadouros);
        botaoCriadouros.setOnClickListener(this);

        spinnerTiposImoveis = (Spinner) findViewById(R.id.dropdown_tipos_de_unidade);

        Button botaoInseticidas = (Button) findViewById(R.id.botao_inseticidas);
        botaoInseticidas.setOnClickListener(this);

        Button botaoConcluirAtividade = (Button) findViewById(R.id.botao_salvar_atividade);
        botaoConcluirAtividade.setOnClickListener(this);

        Button botaoExcluirAtividade = (Button) findViewById(R.id.botao_excluir_atividade);
        botaoExcluirAtividade.setOnClickListener(this);

    }

    private void setOrientationOfScreen() {
        if (!new IsXLargeScreen().isXLargeScreen(getApplicationContext())) {
            //set phones to portrait
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //Tablets como Landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        preencherListaDeQuarteiroes();
        preencherListaDeTiposImoveis();

        if (recuperarAtividadeSelecionada() != null && atividade.getId() != null) {
            alterarTitulo();
            endereco.setText(atividade.getEndereco());
            numeroEndereco.setText(atividade.getNumero());
            setQuarteiraoSelecionado();
            setObservacaoSelecionada();
            setTipoImovelSelecionado();
        }

    }

    private void setTipoImovelSelecionado() {
        for (int i = 0; i < spinnerTiposImoveis.getAdapter().getCount(); i++) {
            for (int j = 0; j < tipoImoveisList.size(); j++) {
                if (spinnerTiposImoveis.getItemAtPosition(i).toString().equals(atividade.getTipoImoveis().getDescricao())) {
                    spinnerTiposImoveis.setSelection(i);
                    break;
                }
            }
        }
    }

    private void setObservacaoSelecionada() {
        if (atividade.getObservacao().equals(recebido.getText())) {
            recebido.setChecked(true);
        } else if (atividade.getObservacao().equals(fechado.getText())) {
            fechado.setChecked(true);
        } else if (atividade.getObservacao().equals(resgatado.getText())) {
            resgatado.setChecked(true);
        }
    }

    private void setQuarteiraoSelecionado() {
        for (int i = 0; i < spinnerQuarteiroes.getAdapter().getCount(); i++) {
            for (int j = 0; j < quarteiraoList.size(); j++) {
                if (spinnerQuarteiroes.getItemAtPosition(i).toString().equals(atividade.getQuarteirao().getDescricao())) {
                    spinnerQuarteiroes.setSelection(i);
                    break;
                }
            }
        }
    }

    private void alterarTitulo() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(atividade.getTitulo());
        }
    }

    private void preencherListaDeQuarteiroes() {
        //Preencher dropdown com quarteirões relacionados ao bairro
        quarteiraoList = new ArrayList<>();
        try {
            quarteiraoList = new QuarteiraoDAO(AtividadeActivity.this).selectAllDoBairro(BoletimDiarioActivity.BAIRRO_ID);
        } catch (Exception e) {
            Log.e("SPIAA", "Erro ao tentar SELECT ALL Quarteirões", e);
        }
        String[] quarteiroes = new String[quarteiraoList.size()];
        int i = 0;
        for (Quarteirao quarteirao : quarteiraoList) {
            quarteiroes[i] = quarteirao.getDescricao();
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quarteiroes);
        spinnerQuarteiroes.setAdapter(adapter);
    }

    private void preencherListaDeTiposImoveis() {
        //Preencher dropdown com tipos de imóveis
        tipoImoveisList = new ArrayList<>();
        try {
            tipoImoveisList = new TipoImoveisDAO(AtividadeActivity.this).selectAll();
        } catch (Exception e) {
            Log.e("SPIAA", "Erro ao tentar SELECT ALL Tipos Imóveis", e);
        }
        String[] tiposImoveis = new String[tipoImoveisList.size()];
        int i = 0;
        for (TipoImoveis ti : tipoImoveisList) {
            tiposImoveis[i] = ti.getDescricao();
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposImoveis);
        spinnerTiposImoveis.setAdapter(adapter);
    }

    private Atividade recuperarAtividadeSelecionada() {
        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            atividade = (Atividade) dados.getSerializable("Atividade");
        }
        return atividade;
    }

    private void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_atividade, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.botao_criadouros:
                preencheListaDeCriadouros();
                break;
            case R.id.botao_inseticidas:
                preencheListaDeInseticidas();
                break;
            case R.id.botao_salvar_atividade:
                salvarAtividade();
                break;
            case R.id.botao_excluir_atividade:
                excluirAtividade();
                break;
        }
    }

    private void excluirAtividade() {
        if (atividade.getTitulo() != null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(AtividadeActivity.this);
            dialog.setMessage("Tem certeza de que deseja excluir a " + this.atividade.getTitulo() + "?");

            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        new AtividadeDAO(AtividadeActivity.this).delete(atividade.getId());
                        Toast.makeText(AtividadeActivity.this, "Atividade excluída", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } catch (Exception e) {
                        Log.e("SPIAA", "Erro ao deletar Atividade", e);
                        Toast.makeText(AtividadeActivity.this, "Erro ao tentar excluir Atividade", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
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
    }

    private void salvarAtividade() {
        if (endereco.getText().toString().isEmpty() || endereco.getText().toString().trim().equals("")) {
            Snackbar.make(findViewById(R.id.linear_atividade), "Preencha o campo Rua/Avenida/Travessa", Snackbar.LENGTH_LONG).show();
        } else {
            if (numeroEndereco.getText().toString().isEmpty() || numeroEndereco.getText().toString().trim().equals("")) {
                Snackbar.make(findViewById(R.id.linear_atividade), "Preencha o campo Número", Snackbar.LENGTH_LONG).show();
            } else {
                if (obterObservacaoSelecionada().isEmpty()) {
                    Snackbar.make(findViewById(R.id.linear_atividade), "Selecione Recebido, Fechado ou Resgatado", Snackbar.LENGTH_LONG).show();
                } else {
                    atividade.setQuarteirao(obterQuarteiraoSelecionado());
                    atividade.setTipoImoveis(obterTipoImovelSelecionado());
                    atividade.setEndereco(endereco.getText().toString());
                    atividade.setNumero(numeroEndereco.getText().toString());
                    atividade.setObservacao(obterObservacaoSelecionada());
                    TratamentoAntiVetorial tav = new TratamentoAntiVetorial();
                    tav.setId(IdBoletimDiario);
                    atividade.setBoletimDiario(tav);
                    atividade.setDataInicial(DATA_INICIAL);
                    atividade.setDataFinal(new Date());

                    if (atividade.getId() == null) {
                        //Nova atividade
                        try {
                            new AtividadeDAO(this).insert(atividade);
                            Toast.makeText(AtividadeActivity.this, "Atividade salva com sucesso", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (Exception e) {
                            Log.e("SPIAA", "Erro no INSERT de Atividade", e);
                        }
                    }else{
                        //Update da atividade
                        try {
                            new AtividadeDAO(this).update(atividade);
                            Toast.makeText(AtividadeActivity.this, "Atividade atualizada com sucesso", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (Exception e) {
                            Log.e("SPIAA", "Erro no UPDATE de Atividade", e);
                        }
                    }
                }
            }
        }
    }

    private String obterObservacaoSelecionada() {
        String observacao = "";

        int id = radioGroupObservacoes.getCheckedRadioButtonId();

        if (id == R.id.radio_recebido) {
            observacao = "RECEBIDO";
        } else if (id == R.id.radio_fechado) {
            observacao = "FECHADO";
        } else if (id == R.id.radio_resgatado) {
            observacao = "RESGATADO";
        }

        return observacao;
    }

    private TipoImoveis obterTipoImovelSelecionado() {
        TipoImoveis tipoImovel = null;
        for (TipoImoveis ti : tipoImoveisList) {
            if (spinnerTiposImoveis.getSelectedItem().toString().equals(ti.getDescricao())) {
                tipoImovel = ti;
                break;
            }
        }
        return tipoImovel;
    }

    private Quarteirao obterQuarteiraoSelecionado() {
        Quarteirao quarteirao = null;
        for (Quarteirao q : quarteiraoList) {
            if (spinnerQuarteiroes.getSelectedItem().toString().equals(q.getDescricao())) {
                quarteirao = q;
                break;
            }
        }
        return quarteirao;
    }

    private void preencheListaDeInseticidas() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AtividadeActivity.this);
        inseticidaList = new ArrayList<>();
        try {
            inseticidaList = new InseticidaDAO(this).selectAll();

        } catch (Exception e) {
            Log.e("SPIAA", "Erro no SELECT ALL inseticidas", e);
        }

        View viewInseticidas = getLayoutInflater().inflate(R.layout.inseticida_container, null);
        int position = 0;
        for (Inseticida inseticida : inseticidaList) {
            linearLayoutInseticidas = (LinearLayout) viewInseticidas.findViewById(R.id.linear_inseticidas);
            View item = getLayoutInflater().inflate(R.layout.inseticida_item_list, linearLayoutInseticidas, false);
            TextInputLayout textInputLayout = (TextInputLayout) item.findViewById(R.id.message_qtde_inseticida);
            textInputLayout.setHint("Quantidade de " + inseticida.getNome());
            if (atividade.getAtividadeInseticidasList() != null) {
                EditText qtdeInseticida = (EditText) item.findViewById(R.id.qtde_inseticida);
                if (atividade.getAtividadeInseticidasList().get(position).getQuantidadeInseticida() != null) {
                    qtdeInseticida.setText(String.valueOf(atividade.getAtividadeInseticidasList().get(position).getQuantidadeInseticida()));
                }
                position++;
            }
            linearLayoutInseticidas.addView(item);

        }
        dialog.setView(viewInseticidas);

        dialog.setCancelable(true);
        dialog.setTitle("Inseticidas utilizados");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                View view;
                List<AtividadeInseticida> atividadeInseticidaList = new ArrayList<>();
                int i = 0;
                for (Inseticida inseticida : inseticidaList) {
                    view = linearLayoutInseticidas.getChildAt(i);
                    EditText editText = (EditText) view.findViewById(R.id.qtde_inseticida);

                    AtividadeInseticida ai = new AtividadeInseticida();
                    ai.setInseticida(inseticida);
                    if (!editText.getText().toString().equals("")) {
                        ai.setQuantidadeInseticida(Integer.parseInt(editText.getText().toString()));
                    }

                    atividadeInseticidaList.add(ai);
                    i++;
                }
                atividade.setAtividadeInseticidasList(atividadeInseticidaList);
            }
        });
        AlertDialog alertDialog = dialog.show();
        mudaCorTextoBotaoDialog(alertDialog);
    }

    private void preencheListaDeCriadouros() {
        //Criadouros
        AlertDialog.Builder dialog = new AlertDialog.Builder(AtividadeActivity.this);
        try {
            criadouroList = new CriadouroDAO(this).selectAll();

        } catch (Exception e) {
            Log.e("SPIAA", "Erro no SELECT ALL criadouros", e);
        }

        View viewCriadouro = getLayoutInflater().inflate(R.layout.criadouro_container, null);
        int position = 0;
        for (Criadouro criadouro : criadouroList) {
            linearLayoutCriadouros = (LinearLayout) viewCriadouro.findViewById(R.id.linear_criadouros);
            View item = getLayoutInflater().inflate(R.layout.criadouro_item_list, linearLayoutCriadouros, false);
            TextInputLayout textInputLayout = (TextInputLayout) item.findViewById(R.id.message_qtde_criadouro);
            textInputLayout.setHint("Quantidade de " + criadouro.getGrupo());

            if (atividade.getAtividadeCriadouroList() != null) {
                EditText qtdeCriadouro = (EditText) item.findViewById(R.id.qtde_criadouro);
                if (atividade.getAtividadeCriadouroList().get(position).getQuantidadeCriadouro() != null) {
                    qtdeCriadouro.setText(String.valueOf(atividade.getAtividadeCriadouroList().get(position).getQuantidadeCriadouro()));
                }
                position++;
            }

            linearLayoutCriadouros.addView(item);
        }
        dialog.setView(viewCriadouro);

        dialog.setCancelable(true);
        dialog.setTitle("Criadouros encontrados");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                View view;
                List<AtividadeCriadouro> atividadeCriadouroList = new ArrayList<>();
                int i = 0;
                for (Criadouro criadouro : criadouroList) {
                    view = linearLayoutCriadouros.getChildAt(i);
                    EditText editText = (EditText) view.findViewById(R.id.qtde_criadouro);

                    AtividadeCriadouro ac = new AtividadeCriadouro();
                    ac.setCriadouro(criadouro);
                    if (!editText.getText().toString().equals("")) {
                        ac.setQuantidadeCriadouro(Integer.parseInt(editText.getText().toString()));
                    }
                    atividadeCriadouroList.add(ac);
                    i++;
                }
                atividade.setAtividadeCriadouroList(atividadeCriadouroList);

            }
        });
        AlertDialog alertDialog = dialog.show();
        mudaCorTextoBotaoDialog(alertDialog);
    }

    private void mudaCorTextoBotaoDialog(AlertDialog alertDialog) {
        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(getResources().getColor(R.color.red_padrao));

        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(getResources().getColor(R.color.red_padrao));
    }

}