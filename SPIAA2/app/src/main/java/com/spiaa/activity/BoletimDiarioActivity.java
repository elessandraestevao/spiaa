package com.spiaa.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.spiaa.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoletimDiarioActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletim_diario);

        //esconder teclado ao entrar nesta activity
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Preencher dropdown com bairros destinados ao Agente de Saúde
        Spinner spinner = (Spinner) findViewById(R.id.dropdown_bairros);
        String[] bairros = new String[]{"Fernandes", "Centro", "Maristela"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bairros);
        spinner.setAdapter(adapter);

        //Preencher campo data com a data atuala do sistema
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        EditText dataAtual = (EditText) findViewById(R.id.data_atual);
        dataAtual.setText(dateFormat.format(date));

        //Botão de ATIVIDADES fica invisible inicialmente
        final Button botaoAtividades = (Button) findViewById(R.id.atividades);
        botaoAtividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BoletimDiarioActivity.this, AtividadeActivity.class);
                startActivity(intent1);
            }
        });

        final FloatingActionButton criarBoletim = (FloatingActionButton) findViewById(R.id.fab_criar_boletim);
        criarBoletim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarBoletim.setVisibility(View.INVISIBLE);
                botaoAtividades.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_boletim_diario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_nova_atividade:
                Intent intent1 = new Intent(BoletimDiarioActivity.this, AtividadeActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_todas_atividades:
                Intent intent2 = new Intent(BoletimDiarioActivity.this, TodasAtividadesActivity.class);
                startActivity(intent2);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }

}
