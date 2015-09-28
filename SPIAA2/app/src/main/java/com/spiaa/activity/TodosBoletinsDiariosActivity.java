package com.spiaa.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.spiaa.R;
import com.spiaa.adapter.BoletimListaAdapter;
import com.spiaa.builder.BoletimDiarioBuilder;

public class TodosBoletinsDiariosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, com.melnykov.fab.ScrollDirectionListener {

    BoletimListaAdapter adapter = new BoletimListaAdapter(this);
    ListView listaBoletins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_boletins_diarios);

        listaBoletins = (ListView) findViewById(R.id.lista_boletins);
        com.melnykov.fab.FloatingActionButton fabCriar = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.botao_criar_boletim);
        fabCriar.attachToListView(listaBoletins);

        adapter.setLista(new BoletimDiarioBuilder().geraBoletins(10));
        listaBoletins.setAdapter(adapter);
        listaBoletins.setOnItemClickListener(this);

        fabCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TodosBoletinsDiariosActivity.this, BoletimDiarioActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todos_boletins_diarios, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onScrollDown() {

    }

    @Override
    public void onScrollUp() {

    }
}
