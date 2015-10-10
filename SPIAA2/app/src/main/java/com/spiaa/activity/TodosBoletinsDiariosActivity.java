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

import com.spiaa.R;
import com.spiaa.adapter.BoletimListaAdapter;
import com.spiaa.builder.BoletimDiarioBuilder;
import com.spiaa.dao.TratamentoAntiVetorialDAO;
import com.spiaa.modelo.TratamentoAntiVetorial;
import com.spiaa.modelo.IsXLargeScreen;

public class TodosBoletinsDiariosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, com.melnykov.fab.ScrollDirectionListener {

    BoletimListaAdapter adapter = new BoletimListaAdapter(this);
    ListView listaBoletins;
    com.melnykov.fab.FloatingActionButton fabCriar;
    public static boolean NOVO_BOLETIM = false;

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

        listaBoletins = (ListView) findViewById(R.id.lista_boletins);
        fabCriar = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fab_criar_boletim);
        fabCriar.attachToListView(listaBoletins);

        try {
            adapter.setLista(new TratamentoAntiVetorialDAO(TodosBoletinsDiariosActivity.this).selectAll());
        } catch (Exception e) {
            Log.e("SPIAA", "Erro ao tentar SELECT ALL Tratamento anti-vetorial", e);
        }
        listaBoletins.setAdapter(adapter);
        listaBoletins.setOnItemClickListener(this);

        fabCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NOVO_BOLETIM = true;
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
}
