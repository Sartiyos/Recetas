package com.miguelbd.recetas.dialogos;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miguelbd.recetas.R;
import com.miguelbd.recetas.clases.Usuario;

public class PasswordDialog {

    private Button btnCambiar;

    //Creamos una interfaz
    public interface interfacePasswordDialog {
        void llamarDialogoPassword(String newPass);
    }

    private interfacePasswordDialog interfaz;

    public PasswordDialog(final Context context, interfacePasswordDialog actividad, final Usuario usuario) {

        interfaz = actividad;

        //Creamos una ventana de diálogo
        final Dialog dialogo = new Dialog(context);
        dialogo.getWindow();
        dialogo.setContentView(R.layout.dialog_password);

        //Objetos con los que identificamos a los componentes del XML
        final EditText edtOldPass   = (EditText)dialogo.findViewById(R.id.edtOldPass);
        final EditText edtNewPass   = (EditText)dialogo.findViewById(R.id.edtNewPass);
        final EditText edtNewPass2  = (EditText)dialogo.findViewById(R.id.edtNewPass2);

        btnCambiar = (Button)dialogo.findViewById(R.id.btnCambiar);

        //Creamos una escucha para el Botón Cambiar
        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass  = edtOldPass.getText().toString();
                String newPass  = edtNewPass.getText().toString();
                String newPass2 = edtNewPass2.getText().toString();

                //En caso de estar los campos vacíos nos saltará un aviso
                if(oldPass.isEmpty() || newPass.isEmpty() || newPass2.isEmpty()) {
                    Toast.makeText(view.getContext(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                } else {
                    if(oldPass.equals(usuario.getPassword())) {
                        if(newPass.equals(newPass2)) {
                            interfaz.llamarDialogoPassword(newPass);
                            dialogo.dismiss();
                        } else {
                            Toast.makeText(view.getContext(), "La nueva contraseña no coincide", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(view.getContext(), "La antigua contraseña no es esa", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        dialogo.show();
    }
}
