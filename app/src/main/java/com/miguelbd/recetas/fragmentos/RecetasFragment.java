package com.miguelbd.recetas.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.miguelbd.recetas.R;
import com.miguelbd.recetas.actividades.PdfActivity;
import com.miguelbd.recetas.adaptadores.Adaptador;
import com.miguelbd.recetas.basededatos.DatabaseAccess;
import com.miguelbd.recetas.clases.Receta;
import com.miguelbd.recetas.dialogos.FiltroDialog;

import java.util.ArrayList;

public class RecetasFragment extends Fragment implements FiltroDialog.interfaceFiltroDialog {

    //Propiedades de la clase
    private View rootView;
    private ArrayList<Receta> listadoPlatos;
    private Adaptador adaptadorPrincipal;
    private RecyclerView viewPlatos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Mostramos el toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        // Activamos el menu del Toolbar
        setHasOptionsMenu(true);

        //Inflamos la Vista rootView para Visualizar el Adaptador personalizado
        rootView = inflater.inflate(R.layout.fragment_recetas, container, false);

        //Vinculamos los objetos del XML
        viewPlatos = (RecyclerView) rootView.findViewById(R.id.lisvPlatos);
        viewPlatos.setHasFixedSize(true);
        viewPlatos.setLayoutManager(new LinearLayoutManager(getContext()));

        //Llamamos a la función cargarAdaptador para mostrar los datos en el RecyclerView
        cargarAdaptador();

        //Devolvemos la Vista
        return rootView;
    }

    private void cargarAdaptador() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();

        //Obtenemos todas las recetas de los platos principales
        listadoPlatos = databaseAccess.obtenerReceta();

        //Cerramos la conexión con la Base de Datos
        databaseAccess.close();

        adaptadorPrincipal = new Adaptador(listadoPlatos);
        adaptadorPrincipal.setOnClicListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PdfActivity.class);
                intent.putExtra("receta",
                        listadoPlatos.get(viewPlatos.getChildAdapterPosition(view)).getPdfReceta());
                startActivity(intent);
            }
        });

        //Hacemos que el adaptador se cargue en el RecyclerView
        viewPlatos.setAdapter(adaptadorPrincipal);
    }

    //Método para cargar el menú en el Toolbar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.filtro_recetas, menu);
    }

    //Método para comprobar si hemos pulsado algún elemento del Menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.mFiltro: //Botón del Menú: Filtro
                new FiltroDialog(getContext(), RecetasFragment.this);
                break;
        }
        return true;
    }

    @Override
    public void llamarDialogoFiltro(String r) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();

        //Obtenemos todas las recetas de los platos principales
        listadoPlatos = databaseAccess.obtenerRecetaFiltro(r);

        //Cerramos la conexión con la Base de Datos
        databaseAccess.close();

        adaptadorPrincipal = new Adaptador(listadoPlatos);
        adaptadorPrincipal.setOnClicListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PdfActivity.class);
                intent.putExtra("receta",
                        listadoPlatos.get(viewPlatos.getChildAdapterPosition(view)).getPdfReceta());
                startActivity(intent);
            }
        });

        //Hacemos que el adaptador se cargue en el RecyclerView
        viewPlatos.setAdapter(adaptadorPrincipal);
    }
}