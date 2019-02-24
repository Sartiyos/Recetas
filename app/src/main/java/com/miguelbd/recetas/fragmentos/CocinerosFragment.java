package com.miguelbd.recetas.fragmentos;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miguelbd.recetas.R;
import com.miguelbd.recetas.clases.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CocinerosFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    private double longitude = 0.0;
    private double latitude = 0.0;

    private String nombreUsuario;

    // Atributos para la conexión del web service
    private RequestQueue      request;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Ocultamos el toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        // Inflamos la vista del fragment
        view = inflater.inflate(R.layout.fragment_cocineros, container, false);

        request = Volley.newRequestQueue(getContext());

        // Instanciamos el mapa
        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);

        //Cargamos el mapa en el fragment map
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

        nombreUsuario = getArguments().getString("user");

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Comprobamos si no tenemos permisos para acceder al GPS
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            // Obtenemos nuestra ubicación a través de la longitud y la latitud
            LocationManager locationManager = (LocationManager) ((AppCompatActivity) getActivity()).getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(bestProvider);
            longitude = location.getLongitude();
            latitude  = location.getLatitude();

            // Acercamos la camara a la posición del usuario
            LatLng ubi = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(ubi).title("Chef " + nombreUsuario));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubi, 15)); // Zoom del mapa

            // Actualizamos los datos del usuario en la base de datos externa
            actualizarDatosGPS(nombreUsuario);

        }
    }

    // Método para actualizar los datos del usuario en la base de datos externa
    public void actualizarDatosGPS(String nombre) {

        // Creamos un string con el url del servidor con los datos usuario para actualizarlo
        String url = "http://192.168.1.52/recetas/api.php?username=" + nombre +
                "&latitud="  + latitude +
                "&longitud=" + longitude;

        // Reemplazamos los espacios por el %20
        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    String respuesta = response.getString("respuesta");
                    switch (respuesta) {
                        case "1": {
                            Log.i("GPS", "Datos actualizados");
                            break;
                        }
                        case "0": {
                            Log.i("GPS", "Los datos no se han actualizado");
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("GPS", "Error en el servidor");
            }
        });
        request.add(jsonObjectRequest);
    }
}