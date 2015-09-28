package com.spiaa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.spiaa.R;
import com.spiaa.adapter.AtividadeListaAdapter;
import com.spiaa.builder.AtividadeBuilder;

public class TodasAtividadesActivity extends AppCompatActivity {
    AtividadeListaAdapter adapter = new AtividadeListaAdapter(this);
    ListView listaAtividades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todas_atividades);

        listaAtividades = (ListView) findViewById(R.id.lista_atividades);

        com.melnykov.fab.FloatingActionButton fabCriar = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.botao_criar_atividade);
        fabCriar.attachToListView(listaAtividades);

        adapter.setLista(new AtividadeBuilder().geraAtividades(10));
        listaAtividades.setAdapter(adapter);
        //listaAtividades.setOnItemClickListener(this);

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
