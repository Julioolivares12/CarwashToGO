package com.julio.carwashtogo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.julio.carwashtogo.common.Constantes;

public class PrincipalActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Bundle datos;
    private NavController navController;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_tools, R.id.nav_share, R.id.nav_send).setDrawerLayout(drawer).build();

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_listar_empresas,
                R.id.nav_crear_empresas,
                R.id.nav_agregar_promociones,
                R.id.nav_lista_encargados,
                R.id.nav_nuevo_encargado,
                R.id.nav_perfilFragment
        ).setDrawerLayout(drawer).build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        datos = getIntent().getExtras();
        if (datos != null){
            //hacer cambio de usuaio
            String rol = datos.getString(Constantes.ROL_USER);
            assert rol != null;
            hideOptionMenu(rol);
        }
    }

    private void hideOptionMenu(String tipoUser){
        Menu menu = navigationView.getMenu();
        switch (tipoUser){
            case "cliente":
                //administrador desactivados
                menu.findItem(R.id.nav_agregar_promociones).setVisible(false);
                menu.findItem(R.id.nav_crear_empresas).setVisible(false);
                menu.findItem(R.id.nav_lista_encargados).setVisible(false);
                menu.findItem(R.id.nav_nuevo_encargado).setVisible(false);

                //cliente activados
                menu.findItem(R.id.nav_cuponera).setVisible(true);
                menu.findItem(R.id.nav_add_vehiculo).setVisible(true);
                menu.findItem(R.id.nav_catalogo_productos).setVisible(true);
                break;
            case "administrador":
                //cliente desactivados
                menu.findItem(R.id.nav_cuponera).setVisible(false);
                menu.findItem(R.id.nav_add_vehiculo).setVisible(false);
                menu.findItem(R.id.nav_catalogo_productos).setVisible(false);

                //administrador
                menu.findItem(R.id.nav_agregar_promociones).setVisible(true);
                menu.findItem(R.id.nav_crear_empresas).setVisible(true);
                menu.findItem(R.id.nav_lista_encargados).setVisible(true);
                menu.findItem(R.id.nav_nuevo_encargado).setVisible(true);
                break;
            case "encargado":
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
