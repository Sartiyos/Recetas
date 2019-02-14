package com.miguelbd.recetas.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miguelbd.recetas.R;
import com.miguelbd.recetas.actividades.PdfActivity;
import com.miguelbd.recetas.adaptadores.Adaptador;
import com.miguelbd.recetas.basededatos.DatabaseAccess;
import com.miguelbd.recetas.clases.Receta;

import java.util.ArrayList;

public class RecetasFragment extends Fragment {

    //Propiedades de la clase
    private View rootView;
    private ArrayList<Receta> listadoPlatos;
    private Adaptador adaptadorPrincipal;
    private RecyclerView viewPlatos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
}