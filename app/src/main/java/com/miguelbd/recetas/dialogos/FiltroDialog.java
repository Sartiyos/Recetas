package com.miguelbd.recetas.dialogos;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.miguelbd.recetas.R;

public class FiltroDialog {

    private Button btnAceptar;

    //Creamos una interfaz
    public interface interfaceFiltroDialog {
        void llamarDialogoFiltro(String respuesta);
    }

    private FiltroDialog.interfaceFiltroDialog interfaz;

    public FiltroDialog(final Context context, FiltroDialog.interfaceFiltroDialog actividad) {

        interfaz = actividad;

        // Creamos una ventana de diálogo
        final Dialog dialogo = new Dialog(context);
        dialogo.getWindow();
        dialogo.setContentView(R.layout.dialog_filtro);

        btnAceptar = (Button)dialogo.findViewById(R.id.btnAceptar);

        //Creamos una escucha para el Botón Aceptar
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String r;

                RadioButton rb1 = (RadioButton) dialogo.findViewById(R.id.rb1);
                RadioButton rb2 = (RadioButton) dialogo.findViewById(R.id.rb2);

                // Comprobamos si alguno de los dos checkbox está marcado
                if(rb1.isChecked()) {
                    r = "Principal";
                    interfaz.llamarDialogoFiltro(r);
                    dialogo.dismiss();
                }
                else if(rb2.isChecked()) {
                    r = "Postre";
                    interfaz.llamarDialogoFiltro(r);
                    dialogo.dismiss();
                }
                else {
                    r = "Todas";
                    interfaz.llamarDialogoFiltro(r);
                    dialogo.dismiss();
                }
            }
        });

        // Mostramos el dialogo
        dialogo.show();
    }
}
