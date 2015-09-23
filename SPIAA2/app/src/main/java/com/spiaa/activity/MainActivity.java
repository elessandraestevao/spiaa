package com.spiaa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import com.spiaa.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout perfil = (RelativeLayout) findViewById(R.id.perfil);
        perfil.setOnClickListener(this);

        RelativeLayout boletimDiario = (RelativeLayout) findViewById(R.id.boletim_diario);
        boletimDiario.setOnClickListener(this);

        RelativeLayout denuncias = (RelativeLayout) findViewById(R.id.denuncias);
        denuncias.setOnClickListener(this);

        RelativeLayout sincronizar = (RelativeLayout) findViewById(R.id.sincronizar);
        sincronizar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.perfil:
                Intent intent1 = new Intent(MainActivity.this, PerfilEditActivity.class);
                startActivity(intent1);
                break;
            case R.id.boletim_diario:
                Intent intent2 = new Intent(MainActivity.this, TodosBoletinsDiariosActivity.class);
                startActivity(intent2);
                break;
            case R.id.denuncias:
                Intent intent3 = new Intent(MainActivity.this, DenunciasActivity.class);
                startActivity(intent3);
                break;
            case R.id.sincronizar:
                Intent intent4 = new Intent(MainActivity.this, SincronizarActivity.class);
                startActivity(intent4);
                break;
        }

    }
}
