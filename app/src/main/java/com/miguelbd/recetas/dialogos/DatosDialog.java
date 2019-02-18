package com.miguelbd.recetas.dialogos;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miguelbd.recetas.R;
import com.miguelbd.recetas.clases.Usuario;

public class DatosDialog {

    private Button btnModificar;

    //Creamos una interfaz
    public interface interfaceDatosDialog {
        void llamarDialogoDatos(String nombre, String apellidos, String email, String fecha, String telefono);
    }

    private DatosDialog.interfaceDatosDialog interfaz;

    public DatosDialog(final Context context, DatosDialog.interfaceDatosDialog actividad, final Usuario usuario) {

        interfaz = actividad;

        // Creamos una ventana de diálogo
        final Dialog dialogo = new Dialog(context);
        dialogo.getWindow();
        dialogo.setContentView(R.layout.dialog_datos);

        // Objetos con los que identificamos a los componentes del XML
        final EditText edtNewNombre     = (EditText)dialogo.findViewById(R.id.edtNewNombre);
        final EditText edtNewApellidos  = (EditText)dialogo.findViewById(R.id.edtNewApellidos);
        final EditText edtNewEmail      = (EditText)dialogo.findViewById(R.id.edtNewEmail);
        final EditText edtNewFecha      = (EditText)dialogo.findViewById(R.id.edtNewFecha);
        final EditText edtNewTelefono   = (EditText)dialogo.findViewById(R.id.edtNewTelefono);

        // Cargamos los datos dle usuario en los campos correspondientes
        edtNewNombre.setText(usuario.getNombre());
        edtNewApellidos.setText(usuario.getApellidos());
        edtNewEmail.setText(usuario.getEmail());
        edtNewFecha.setText(usuario.getFecha());
        edtNewTelefono.setText(usuario.getTelefono());

        btnModificar = (Button)dialogo.findViewById(R.id.btnModificar);

        //Creamos una escucha para el Botón Cambiar
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre    = edtNewNombre.getText().toString();
                String apellidos = edtNewApellidos.getText().toString();
                String email     = edtNewEmail.getText().toString();
                String fecha     = edtNewFecha.getText().toString();
                String telefono  = edtNewTelefono.getText().toString();

                //En caso de estar los campos vacíos nos saltará un aviso
                if(nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty() || fecha.isEmpty() || telefono.isEmpty()) {
                    Toast.makeText(view.getContext(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                }
                else {
                    interfaz.llamarDialogoDatos(nombre, apellidos, email, fecha, telefono);
                    dialogo.dismiss();
                }
            }
        });

        dialogo.show();
    }
}