package com.spiaa.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.spiaa.R;
import com.spiaa.adapter.AtividadeListaAdapter;
import com.spiaa.builder.AtividadeBuilder;
import com.spiaa.modelo.Atividade;
import com.spiaa.modelo.IsXLargeScreen;

public class TodasAtividadesActivity extends AppCompatActivity {
    AtividadeListaAdapter adapter = new AtividadeListaAdapter(this);
    ListView listaAtividades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todas_atividades);

        //definição da orientação das telas da aplicação
        if (!new IsXLargeScreen().isXLargeScreen(getApplicationContext())) {
            //set phones to portrait;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //Tablets como Landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        listaAtividades = (ListView) findViewById(R.id.lista_atividades);

        com.melnykov.fab.FloatingActionButton fabCriar = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.botao_criar_atividade);
        fabCriar.attachToListView(listaAtividades);

        adapter.setLista(new AtividadeBuilder().geraAtividades(10));
        listaAtividades.setAdapter(adapter);
        listaAtividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Intent intent = new Intent(TodasAtividadesActivity.this, AtividadeActivity.class);
                Bundle dados = new Bundle();
                Atividade atividade = (Atividade) parent.getItemAtPosition(position);
                dados.putString("atividade", "Atividade " + (position + 1));
                dados.putString("numero_quarteirao", atividade.getNumero_quarteirao());
                dados.putString("endereco", atividade.getEndereco());
                dados.putString("tipo_unidade", atividade.getTipo_unidade());
                dados.putString("observacoes", atividade.getObservacoes());
                dados.putString("criadouro_a1", atividade.getCriadouroA1());
                dados.putString("criadouro_a2", atividade.getCriadouroA2());
                dados.putString("criadouro_b", atividade.getCriadouroB());
                dados.putString("criadouro_c", atividade.getCriadouroC());
                dados.putString("criadouro_d1", atividade.getCriadouroD1());
                dados.putString("criadouro_d2", atividade.getCriadouroD2());
                dados.putString("criadouro_e", atividade.getCriadouroE());
                intent.putExtras(dados);
                startActivity(intent);*/
            }
        });

        fabCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TodasAtividadesActivity.this, AtividadeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todas_atividades, menu);
        return true;
    }

    /*public void onBackPressed() {
        Intent intent = new Intent(TodasAtividadesActivity.this, BoletimDiarioActivity.class);
        startActivity(intent);
        return;
    }*/
}
