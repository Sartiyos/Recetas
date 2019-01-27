package com.miguelbd.recetas.actividades;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.miguelbd.recetas.R;
import com.miguelbd.recetas.fragmentos.BuscarFragment;
import com.miguelbd.recetas.fragmentos.CocinerosFragment;
import com.miguelbd.recetas.fragmentos.FavoritoFragment;
import com.miguelbd.recetas.fragmentos.RecetasFragment;

public class MainActivity extends AppCompatActivity {

    //Objetos con los que identificaremos a los componentes del XML
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        //Creamos una escucha para comprobar si se ha pulsado sobre él
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.nav_recetas:
                        fragment = new RecetasFragment();
                        break;

                    case R.id.nav_buscar:
                        fragment = new BuscarFragment();
                        break;
                    case R.id.nav_fav:
                        fragment = new FavoritoFragment();
                        break;
                    case R.id.nav_ubi:
                        fragment = new CocinerosFragment();
                        break;
                }
                replaceFragment(fragment);
                return true;
            }
        });

        //Llamamos al método para iniciar el primer fragmento, en este caso el NuevoFragment
        setInitialFragment();
    }

    //Método para iniciar los Fragmentos, en este caso cargará NuevoFragment
    private void setInitialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.contenedor, new RecetasFragment());
        fragmentTransaction.commit();
    }

    //Método que cambiará el Fragmento
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedor, fragment);
        fragmentTransaction.commit();
    }

}
