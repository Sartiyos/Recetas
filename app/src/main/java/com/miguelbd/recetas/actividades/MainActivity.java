package com.miguelbd.recetas.actividades;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.miguelbd.recetas.clases.Usuario;
import com.miguelbd.recetas.fragmentos.PerfilFragment;
import com.miguelbd.recetas.R;
import com.miguelbd.recetas.fragmentos.CocinerosFragment;
import com.miguelbd.recetas.fragmentos.RecetasFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // Objetos con los que identificaremos a los componentes del XML
    private BottomNavigationView navigation;
    private Toolbar toolbar;
    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recibimos el nombre del usuario logeado
        usuario = getIntent().getStringExtra("usuario");

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //toolbar    = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //Creamos una escucha para comprobar si se ha pulsado sobre él
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Bundle args = new Bundle();
                args.putString("user", usuario);
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.nav_recetas:
                        fragment = new RecetasFragment();
                        break;
                    case R.id.nav_perfil:
                        fragment = new PerfilFragment();
                        fragment.setArguments(args);
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
