package com.miguelbd.recetas.fragmentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.miguelbd.recetas.R;
import com.miguelbd.recetas.clases.Usuario;
import com.miguelbd.recetas.dialogos.DatosDialog;
import com.miguelbd.recetas.dialogos.PasswordDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PerfilFragment extends Fragment implements PasswordDialog.interfacePasswordDialog, DatosDialog.interfaceDatosDialog {

    // Objetos necesarios para el XML
    private ImageView imgImagen;
    private TextView  txtUser;
    private TextView  txtCmbPass;
    private TextView  txtCmbDatos;

    // Objetos necesarios
    private View    view;
    private Usuario usuario;

    // Atributos para la conexión del web service
    private RequestQueue      request;
    private JsonArrayRequest  jsonArrayRequest;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflamos la vista
        view = inflater.inflate(R.layout.fragment_perfil, container, false);

        request = Volley.newRequestQueue(getContext());

        // Identificamos los objetos del XML
        imgImagen   = (ImageView) view.findViewById(R.id.imgImagen);
        txtUser     = (TextView)  view.findViewById(R.id.txtUser);
        txtCmbPass  = (TextView)  view.findViewById(R.id.txtCmbPass);
        txtCmbDatos = (TextView)  view.findViewById(R.id.txtCmbDatos);

        // Obtenemos el nombre del usuario desde la actividad principal
        String nombreUsuario = getArguments().getString("user");
        txtUser.setText(nombreUsuario);

        txtCmbPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PasswordDialog(getContext(), PerfilFragment.this, usuario);
            }
        });

        txtCmbDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatosDialog(getContext(), PerfilFragment.this, usuario);
            }
        });

        // Pedimos a la base de datos los datos de usuario pasandole su nombre de usuario
        obtenerDatos(nombreUsuario);

        return view;
    }

    private void obtenerDatos(String nombreUsuario) {
        // Creamos un string con el url del servidor con los datos usuario
        String url = "http://192.168.1.52/recetas/api.php?username=" + nombreUsuario;

        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            // Método que nos muestra en caso de que la respuesta este correcta
            @Override
            public void onResponse(JSONArray response) {

                try {

                    // Convertimos el JSONArray en un JSONObjet
                    JSONObject objeto = response.getJSONObject(0);

                    // Obtenemos todos los datos del usuario
                    String user = objeto.getString("username");
                    String pass = objeto.getString("password");
                    String name = objeto.getString("nombre");
                    String last = objeto.getString("apellidos");
                    String date = objeto.getString("fNacimiento");
                    String mail = objeto.getString("email");
                    String telf = objeto.getString("telefono");
                    String foto = objeto.getString("foto");

                    usuario = new Usuario(user, pass, name, last, date, mail, telf, foto);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            // Método que nos muestra en caso de error
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "ERROR AL LEER LOS DATOS", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonArrayRequest);
    }

    @Override
    public void llamarDialogoPassword(String newPass) {
        usuario.setPassword(newPass);
        actualizarDatos();
    }

    @Override
    public void llamarDialogoDatos(String n, String a, String e, String f, String t) {
        usuario.setNombre(n);
        usuario.setApellidos(a);
        usuario.setEmail(e);
        usuario.setFecha(f);
        usuario.setTelefono(t);
        actualizarDatos();
    }

    // Método para actualizar los datos del usuario en la base de datos externa
    public void actualizarDatos() {

        // Creamos un string con el url del servidor con los datos usuario para actualizarlo
        String url = "http://192.168.1.52/recetas/api.php?username=" + usuario.getUsuario() +
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

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String respuesta = response.getString("respuesta");
                            switch (respuesta) {
                                case "1": {
                                    Toast.makeText(getContext(), "Datos actualizado", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                case "0": {
                                    Toast.makeText(getContext(), "Ha ocurrido un error al actualizar los datos", Toast.LENGTH_SHORT).show();
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
                        Snackbar.make(view, "Error al actualizar", Snackbar.LENGTH_LONG).show();
                    }
                });
            request.add(jsonObjectRequest);
        }

    // Método para eliminar la cuenta
    public void eliminarCuenta() {

        // Creamos un string con el url del servidor con los datos usuario para actualizarlo
        String url = "http://192.168.1.52/recetas/api.php?username=" + usuario.getUsuario();

        // Reemplazamos los espacios por el %20
        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    String respuesta = response.getString("respuesta");
                    switch (respuesta) {
                        case "1": {
                            Toast.makeText(getContext(), "Datos actualizado", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case "0": {
                            Toast.makeText(getContext(), "Ha ocurrido un error al actualizar los datos", Toast.LENGTH_SHORT).show();
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
                Snackbar.make(view, "Error al actualizar", Snackbar.LENGTH_LONG).show();
            }
        });
        request.add(jsonObjectRequest);
    }

}
