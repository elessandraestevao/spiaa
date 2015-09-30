package com.spiaa.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.spiaa.R;
import com.spiaa.adapter.DenunciaListaAdapter;
import com.spiaa.builder.DenunciaBuilder;
import com.spiaa.modelo.Denuncia;
import com.spiaa.modelo.IsXLargeScreen;

public class DenunciasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    DenunciaListaAdapter adapter = new DenunciaListaAdapter(this);
    ListView listaDenuncias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias);

        //definição da orientação das telas da aplicação
        if (!new IsXLargeScreen().isXLargeScreen(getApplicationContext())) {
            //set phones to portrait;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //Tablets como Landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        listaDenuncias = (ListView) findViewById(R.id.lista_denuncias);
        adapter.setLista(new DenunciaBuilder().geraDenuncias(10));
        listaDenuncias.setAdapter(adapter);
        listaDenuncias.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_denuncias, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(DenunciasActivity.this, DenunciaActivity.class);
        Bundle dados = new Bundle();
        Denuncia denuncia = (Denuncia) parent.getItemAtPosition(position);
        dados.putString("denuncia", "Denúncia " + (position + 1));
        dados.putString("bairro", denuncia.getBairro());
        dados.putString("endereco", denuncia.getEndereco());
        dados.putInt("numero", denuncia.getNumero());
        dados.putString("irregularidade", denuncia.getTipo_irregularidades());
        dados.putString("status", denuncia.getStatus());
        dados.putString("observacao", denuncia.getObservacao());
        intent.putExtras(dados);
        startActivity(intent);
    }
}
