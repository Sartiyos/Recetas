package com.miguelbd.recetas.dialogos;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.miguelbd.recetas.R;

public class PasswordDialog {

    //Creamos una interfaz
    public interface interfacePasswordDialog {
        void llamarDialogoCliente(String oldPass, String newPass, String newPass2);
    }

    private interfacePasswordDialog interfaz;

    public PasswordDialog(final Context context, interfacePasswordDialog actividad) {

        interfaz = actividad;

        //Creamos una ventana de diálogo
        final Dialog dialogo = new Dialog(context);
        dialogo.getWindow();
        dialogo.setContentView(R.layout.dialog_password);

        //Objetos con los que identificamos a los componentes del XML
        final EditText edtOldPass   = (EditText)dialogo.findViewById(R.id.edtOldPass);
        final EditText edtNewPass   = (EditText)dialogo.findViewById(R.id.edtNewPass);
        final EditText edtNewPass2  = (EditText)dialogo.findViewById(R.id.edtNewPass2);

        Button btnCambiar = (Button)dialogo.findViewById(R.id.btnCambiar);

        //Creamos una escucha para el Botón Cambiar
        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass  = edtOldPass.getText().toString();
                String newPass  = edtNewPass.getText().toString();
                String newPass2 = edtNewPass2.getText().toString();

                //En caso de estar los campos vacíos nos saltará un aviso
                if(oldPass.isEmpty() || newPass.isEmpty() || newPass2.isEmpty()) {
                    Snackbar.make(view, "Debe rellenar todos los campos", Snackbar.LENGTH_LONG)
                            .show();
                }
                else {
                    interfaz.llamarDialogoCliente(oldPass, newPass, newPass2);
                    dialogo.dismiss();
                }
            }
        });

        dialogo.show();
    }
}
