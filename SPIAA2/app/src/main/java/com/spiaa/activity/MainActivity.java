package com.spiaa.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.spiaa.R;
import com.spiaa.modelo.IsXLargeScreen;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //definição da orientação das telas da aplicação
        if (!new IsXLargeScreen().isXLargeScreen(getApplicationContext())) {
            //set phones to portrait;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //Tablets como Landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        //Criar ícone de Menu na Toolbar
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //Fragment Inicial
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new TodosBoletinsDiariosFragment()).commit();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_perfil:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new PerfilFragment()).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        mudarTituloDaActionBar(R.string.title_activity_perfil);
                        break;
                    case R.id.nav_boletim:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new TodosBoletinsDiariosFragment()).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        mudarTituloDaActionBar(R.string.title_activity_todos_boletins_diarios);
                        break;
                    case R.id.nav_denuncias:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new TodasDenunciasFragment()).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        mudarTituloDaActionBar(R.string.title_activity_denuncias);
                        break;
                }
                return true;
            }
        });
    }

    private void mudarTituloDaActionBar(int title) {
        android.support.v7.app.ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }
    }

}
