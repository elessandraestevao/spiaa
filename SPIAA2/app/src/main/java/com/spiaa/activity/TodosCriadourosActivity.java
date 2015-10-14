package com.spiaa.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.spiaa.R;
import com.spiaa.adapter.CriadouroListaAdapter;
import com.spiaa.dao.CriadouroDAO;
import com.spiaa.modelo.Criadouro;

public class TodosCriadourosActivity extends AppCompatActivity {
    CriadouroListaAdapter adapter = new CriadouroListaAdapter(this);
    ListView listaCriadouros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_criadouros);

        listaCriadouros = (ListView) findViewById(R.id.lista_criadouros);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            adapter.setLista(new CriadouroDAO(TodosCriadourosActivity.this).selectAll());
        } catch (Exception e) {
            Log.e("SPIAA", "Erro no SELECT ALL criadouros", e);
        }
        listaCriadouros.setAdapter(adapter);
    }
}
