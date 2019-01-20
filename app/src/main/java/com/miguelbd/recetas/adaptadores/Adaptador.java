package com.miguelbd.recetas.adaptadores;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miguelbd.recetas.R;
import com.miguelbd.recetas.clases.Receta;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>
        implements View.OnClickListener {

    // Atributos de la clase
    private ArrayList<Receta> listadoRecetas;
    private View.OnClickListener listener;

    // Constructor de la clase
    public Adaptador(ArrayList<Receta> listadoRecetas) {
        this.listadoRecetas = listadoRecetas;
    }

    // El layout manager invoca este método
    // para renderizar cada elemento del RecyclerView
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.items_adaptador, viewGroup, false);

        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    // Este método reemplaza el contenido de cada view,
    // para cada elemento de la lista
    @Override
    public void onBindViewHolder(Adaptador.ViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageURI(Uri.parse(listadoRecetas.get(i).getUriReceta()));
        viewHolder.nombreReceta.setText(listadoRecetas.get(i).getNombreReceta());
        viewHolder.dificultadReceta.setText(listadoRecetas.get(i).getDificultadReceta());
    }

    // Método que define la cantidad de elementos del RecyclerView
    // Puede ser más complejo (por ejemplo si implementamos filtros o búsquedas)
    @Override
    public int getItemCount() {
        return listadoRecetas.size();
    }

    // Método que se encarga de escuchar ese elemento
    public void setOnClicListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null) {
            listener.onClick(view);
        }
    }

    // Obtener referencias de los componentes visuales para cada elemento
    // Es decir, referencias de los EditText, TextViews, Buttons
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView nombreReceta;
        TextView dificultadReceta;

        //Aquí hacemos referencia a los objetos del XML
        public ViewHolder(View itemView) {
            super(itemView);

            imagen = (ImageView) itemView.findViewById(R.id.imgReceta);
            nombreReceta = (TextView) itemView.findViewById(R.id.txvNombreReceta);
            dificultadReceta = (TextView) itemView.findViewById(R.id.txvDificultadReceta);
        }
    }
}
