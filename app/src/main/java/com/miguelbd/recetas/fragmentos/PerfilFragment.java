package com.miguelbd.recetas.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.miguelbd.recetas.R;
import com.miguelbd.recetas.actividades.LoginActivity;
import com.miguelbd.recetas.actividades.MainActivity;
import com.miguelbd.recetas.clases.Usuario;
import com.miguelbd.recetas.dialogos.PasswordDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class PerfilFragment extends Fragment implements PasswordDialog.interfacePasswordDialog {

    private ImageView imgImagen;
    private TextView  txtCmbPass;
    private TextView  txtCmbDatos;
    private TextView  txtUser;

    private Usuario usuario;

    // Atributos para la conexión del web service
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        request = Volley.newRequestQueue(getContext());
        imgImagen   = (ImageView) view.findViewById(R.id.imgImagen);
        txtUser     = (TextView)  view.findViewById(R.id.txtUser);
        txtCmbPass  = (TextView)  view.findViewById(R.id.txtCmbPass);
        txtCmbDatos = (TextView)  view.findViewById(R.id.txtCmbDatos);

        // Colocamos el nombre del usuario
        String usuario = getArguments().getString("user");
        txtUser.setText(usuario);

        txtCmbPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PasswordDialog(getContext(), PerfilFragment.this);
            }
        });


        obtenerDatos();

        return view;
    }

    private void obtenerDatos() {
        // Creamos un string con el url del servidor con los datos usuario
        String url = "http://192.168.1.100/recetas/api.php?username=" + txtUser;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            // Método que nos muestra en caso de que la respuesta este correcta
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String user = response.getString("username");
                    String pass = response.getString("password");
                    String name = response.getString("nombre");
                    String last = response.getString("apellidos");
                    String date = response.getString("fNacimiento");
                    String mail = response.getString("email");
                    String telf = response.getString("telefono");
                    String foto = response.getString("foto");

                    usuario = new Usuario(user, pass, name, last, date, mail, telf, foto);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            // Método que nos muestra en caso de error
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    @Override
    public void llamarDialogoCliente(String oldPass, String newPass, String newPass2) {

        if (oldPass == usuario.getPassword()) {
            if (newPass == newPass2) {
                usuario.setPassword(newPass);
                actualizarDatos();
            }
            else{
                    Toast.makeText(getContext(), "La nueva contraseña no coincide", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(getContext(), "La contraseña antigua no es esa", Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizarDatos() {

        // Creamos un string con el url del servidor con los datos usuario para actualizarlo
        String url = "http://192.168.1.151/recetas/api.php?username=" + usuario.getUsuario() +
                "&password="    + usuario.getPassword() +
                "&nombre="      + usuario.getNombre() +
                "&apellidos="   + usuario.getApellidos() +
                "&fNacimiento=" + usuario.getFecha() +
                "&email="       + usuario.getEmail() +
                "&telefono="    + usuario.getTelefono() +
                "&foto="        + usuario.getFoto();

        // Reemplazamos los espacios por el %20
        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                // Método que nos muestra en caso de que la respuesta este correcta
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        String user = response.getString("username");
                        String pass = response.getString("password");
                        String name = response.getString("nombre");
                        String last = response.getString("apellidos");
                        String date = response.getString("fNacimiento");
                        String mail = response.getString("email");
                        String telf = response.getString("telefono");
                        String foto = response.getString("foto");

                        usuario = new Usuario(user, pass, name, last, date, mail, telf, foto);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }, new Response.ErrorListener() {
                // Método que nos muestra en caso de error
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            });
            request.add(jsonObjectRequest);
        }
}
