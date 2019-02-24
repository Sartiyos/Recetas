package com.miguelbd.recetas.clases;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.miguelbd.recetas.R;

public class InfoWindow implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;

    public InfoWindow(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public View getInfoContents(final Marker m) {
        //Carga layout personalizado.
        View v = inflater.inflate(R.layout.infowindow_layout, null);

        ImageView img = (ImageView) v.findViewById(R.id.iw_foto);
        TextView name = (TextView) v.findViewById(R.id.iw_nombre);
        TextView mail = (TextView) v.findViewById(R.id.iw_email);

        name.setText(m.getTitle());
        mail.setText(m.getSnippet());

        return v;
    }

    @Override
    public View getInfoWindow(Marker m) {
        return null;
    }
}
