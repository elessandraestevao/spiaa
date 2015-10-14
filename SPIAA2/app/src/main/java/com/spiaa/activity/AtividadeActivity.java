package com.spiaa.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.melnykov.fab.ObservableScrollView;
import com.spiaa.R;
import com.spiaa.adapter.CriadouroListaAdapter;
import com.spiaa.dao.BairroDAO;
import com.spiaa.dao.CriadouroDAO;
import com.spiaa.dao.QuarteiraoDAO;
import com.spiaa.modelo.Atividade;
import com.spiaa.modelo.Bairro;
import com.spiaa.modelo.IsXLargeScreen;
import com.spiaa.modelo.Quarteirao;
import com.spiaa.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class AtividadeActivity extends AppCompatActivity implements View.OnClickListener {
    private Atividade atividade = null;
    private Spinner spinnerQuarteiroes;
    private EditText endereco;
    private Dialog dialog;
    CriadouroListaAdapter adapter = new CriadouroListaAdapter(this);
    ListView listaCriadouros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);

        setOrientationOfScreen();
        hideKeyboard();

        endereco = (EditText) findViewById(R.id.endereco_atividade);
        spinnerQuarteiroes = (Spinner) findViewById(R.id.dropdown_quarteiroes);
        Button botaoCriadouros = (Button) findViewById(R.id.botao_criadouros);
        botaoCriadouros.setOnClickListener(this);

        //Criadouros
        dialog = new Dialog(AtividadeActivity.this);
        dialog.setContentView(R.layout.activity_todos_criadouros);
        ListView lv = (ListView ) dialog.findViewById(R.id.lista_criadouros);

        try {
            adapter.setLista(new CriadouroDAO(this).selectAll());
        } catch (Exception e) {
            Log.e("SPIAA", "Erro no SELECT ALL criadouros", e);
        }
        //listaCriadouros.setAdapter(adapter);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        lv.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (recuperarAtividadeSelecionada() != null) {
            alterarTitulo();

            //EditText numeroQuarteirao = (EditText) findViewById(R.id.numero_quarteirao_atividade);
            //numeroQuarteirao.setText(dados.get("numero_quarteirao").toString());


            endereco.setText(atividade.getEndereco());
        }

        preencherListaDeQuarteiroes();
    }

    private void alterarTitulo(){
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(atividade.getTitulo());
        }
    }


    private void preencherListaDeQuarteiroes() {
        //Preencher dropdown com quarteirões relacionados ao bairro
        List<Quarteirao> quarteiraoList = new ArrayList<>();
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

    private Atividade recuperarAtividadeSelecionada() {
        Bundle dados = getIntent().getExtras();
        if(dados != null){
            atividade = (Atividade) dados.getSerializable("Atividade");
        }
        return atividade;
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
        switch (v.getId()){
            case R.id.botao_criadouros:
                showDialogCriadouros();
        }
    }

    private void showDialogCriadouros(){
        dialog.setCancelable(true);
        dialog.setTitle("Criadouros encontrados");
        dialog.show();
    }
}