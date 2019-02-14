package com.miguelbd.recetas.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.miguelbd.recetas.R;
import com.miguelbd.recetas.clases.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

public class PerfilFragment extends Fragment {

    private TextView txtUsuario;

    // Atributos para la conexión del web service
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        request = Volley.newRequestQueue(getContext());

        String usuario = getArguments().getString("user");

        obtenerDatosUsuario(usuario);

        txtUsuario = (TextView) view.findViewById(R.id.txtUser);
        txtUsuario.setText(usuario.toString());


        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }


    // Método para obtener todos los datos del usuario
    private void obtenerDatosUsuario(String user) {

        String url = "http://192.168.1.141/recetas/datos.php?usuario=" + user;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String usuario = response.getString("usuario");
                    String password = response.getString("password");
                    String nombre = response.getString("nombre");
                    String apellidos = response.getString("apellidos");
                    String fecha = response.getString("fNacimiento");
                    String email = response.getString("email");
                    String telefono = response.getString("telefono");
                    String foto = response.getString("foto");

                    Usuario user = new Usuario(usuario, password, nombre, apellidos, fecha, email, telefono, foto);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }}, new Response.ErrorListener() {
            // Método que nos muestra en caso de error
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.add(jsonObjectRequest);
    }

}
