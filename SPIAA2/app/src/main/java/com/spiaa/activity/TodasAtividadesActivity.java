package com.spiaa.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.spiaa.R;
import com.spiaa.adapter.AtividadeListaAdapter;
import com.spiaa.builder.AtividadeBuilder;
import com.spiaa.dao.AtividadeDAO;
import com.spiaa.modelo.Atividade;
import com.spiaa.modelo.IsXLargeScreen;
import com.spiaa.modelo.TratamentoAntiVetorial;

import java.util.Date;
import java.util.List;

public class TodasAtividadesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Long IdBoletimDiario = TratamentoAntiVetorial.ID_BOLETIM;
    private AtividadeListaAdapter adapter = new AtividadeListaAdapter(this);
    private ListView listaAtividades;
    private List<Atividade> atividadeList;
    private TextView nenhumaAtividade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todas_atividades);

        setOrientationOfScreen();

        nenhumaAtividade = (TextView) findViewById(R.id.nenhuma_atividade);
        listaAtividades = (ListView) findViewById(R.id.lista_atividades);

        com.melnykov.fab.FloatingActionButton fabCriar = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.botao_criar_atividade);
        fabCriar.attachToListView(listaAtividades);

        fabCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TodasAtividadesActivity.this, AtividadeActivity.class);
                startActivity(intent);
                AtividadeActivity.DATA_INICIAL = new Date();
            }
        });
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
        try {
            atividadeList = new AtividadeDAO(TodasAtividadesActivity.this).selectAllDoBoletim(IdBoletimDiario);
            adapter.setLista(atividadeList);
        } catch (Exception e) {
            Log.e("SPIAA", "Erro no SELECT ALL Atividades", e);
        }
        listaAtividades.setAdapter(adapter);
        listaAtividades.setOnItemClickListener(this);

        if(atividadeList != null){
            if(atividadeList.isEmpty()){
                nenhumaAtividade.setText("Nenhuma atividade");
            }else {
                nenhumaAtividade.setText("");
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todas_atividades, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(TodasAtividadesActivity.this, AtividadeActivity.class);
        Atividade atividade = (Atividade) parent.getItemAtPosition(position);
        atividade.setTitulo("Atividade " + (position + 1));
        intent.putExtra("Atividade", atividade);
        startActivity(intent);
    }
}
