package com.miguelbd.recetas.actividades;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    // Atributos de la clase
    private EditText edtUsuario;
    private EditText edtPassword;
    private TextView txtRegistrarse;
    private Button btnLogin;

    // Atributos para la conexión del web service
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Obtenemos el control del teclado
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);

        // Relacionamos los atributos con los elementos del XML
        edtUsuario     = (EditText) findViewById(R.id.edtUser);
        edtPassword    = (EditText) findViewById(R.id.edtPasswd);
        btnLogin       = (Button)   findViewById(R.id.btnLogin);
        txtRegistrarse = (TextView) findViewById(R.id.txtRegistrar);

        request = Volley.newRequestQueue(this);

        // Creamos una escucha para el botón Iniciar sesión
        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Ocultamos el teclado cuando pulsemos el botón Login
                inputMethodManager.hideSoftInputFromWindow(btnLogin.getWindowToken(), 0);

                // Cargamos el WebService
                cargarWebService(v);
            }
        });

        // Creamos una escucha para el texto Registrárse
        txtRegistrarse.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Ocultamos el teclado cuando pulsemos el botón Login
                inputMethodManager.hideSoftInputFromWindow(btnLogin.getWindowToken(), 0);

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // Método para comprobar en la base de datos el usuario y su contraseña
    private void cargarWebService(View v) {
        final View view = v;

        final String usuario  = edtUsuario.getText().toString();
        String password       = edtPassword.getText().toString();

        // Comprobamos que los campos no están vacíos
        if(usuario.isEmpty() || password.isEmpty()) {
            Snackbar.make(view, "Los campos no pueden estar vacíos", Snackbar.LENGTH_LONG).show();
        }
        else {

            // Creamos un string con el url del servidor con los datos usuario
            String url = "http://192.168.1.52/recetas/api.php?username=" + usuario + "&password=" + password;

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                // Método que nos muestra en caso de que la respuesta este correcta
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        // Guardamos la respuesta recibida
                        String respuesta = response.getString("respuesta");

                        // Comparamos el valor de la respuesta
                        switch (respuesta) {
                            case "OK": {
                                edtUsuario.setText("");
                                edtPassword.setText("");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("usuario", usuario);
                                startActivity(intent);
                                break;
                            }
                            case "ERROR": {
                                Snackbar.make(view, "Error al iniciar sesión", Snackbar.LENGTH_LONG).show();
                                break;
                            }
                            case "ERROR404": {
                                Snackbar.make(view, "No existe el usuario", Snackbar.LENGTH_LONG).show();
                                break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                }, new Response.ErrorListener() {
                    // Método que nos muestra en caso de error
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(view, "Error al iniciar sesión", Snackbar.LENGTH_LONG).show();
                    }
            });
            request.add(jsonObjectRequest);
        }
    }
}
