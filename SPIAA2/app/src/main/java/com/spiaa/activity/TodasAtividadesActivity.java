package com.spiaa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.spiaa.R;

public class TodasAtividadesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todas_atividades);
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
