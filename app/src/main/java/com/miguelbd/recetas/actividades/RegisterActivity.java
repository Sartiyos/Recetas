package com.miguelbd.recetas.actividades;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.miguelbd.recetas.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUsuario;
    private EditText edtPassword;
    private EditText edtNombre;
    private EditText edtApellidos;
    private EditText edtFNacimiento;
    private EditText edtEmail;
    private EditText edtTelefono;
    private Button   btnRegistrarse;

    // Atributos para la conexión del web service
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Relacionamos los atributos con los elementos del XML
        edtUsuario     = (EditText) findViewById(R.id.edtUsuario);
        edtPassword    = (EditText) findViewById(R.id.edtPassword);
        edtNombre      = (EditText) findViewById(R.id.edtNombre);
        edtApellidos   = (EditText) findViewById(R.id.edtApellido);
        edtFNacimiento = (EditText) findViewById(R.id.edtFecha);
        edtEmail       = (EditText) findViewById(R.id.edtEmail);
        edtTelefono    = (EditText) findViewById(R.id.edtTelefono);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrar);

        request = Volley.newRequestQueue(this);

        // Creamos una escucha para el botón Registrárse
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService(v);
            }
        });

    }

    private void cargarWebService(View v) {
        final View view = v;

        String usuario   = edtUsuario.getText().toString();
        String password  = edtPassword.getText().toString();
        String nombre    = edtNombre.getText().toString();
        String apellidos = edtApellidos.getText().toString();
        String fecha     = edtFNacimiento.getText().toString();
        String email     = edtEmail.getText().toString();
        String telefono  = edtTelefono.getText().toString();

        if(usuario.isEmpty() || password.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() ||
            fecha.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            Snackbar.make(view, "Debe de rellenar todos los campos", Snackbar.LENGTH_LONG).show();
        }
        else {

            int telf = Integer.parseInt(telefono);

            // Creamos un string con el url del servidor con los datos del nuevo usuario
            String url = "http://192.168.1.142/recetas/registro.php?usuario=" + usuario +
                    "&clave=" + password + "&nombre=" + nombre + "&apellidos=" + apellidos +
                    "&fNacimiento=" + fecha + "&email=" + email + "&telefono=" + telf +
                    "&foto=null";

            // Reemplazamos los espacios por el %20
            url = url.replace(" ", "%20");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), "Registro completado", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Snackbar.make(view, "Error al registrarse", Snackbar.LENGTH_LONG).show();
                        }
                    });
            request.add(jsonObjectRequest);
        }
    }
}
