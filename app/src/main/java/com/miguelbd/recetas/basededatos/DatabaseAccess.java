package com.miguelbd.recetas.basededatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.miguelbd.recetas.clases.Receta;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class DatabaseAccess {

    // Atributos de la clase
    private SQLiteAssetHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    // Constructor de la clase
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    // Método para obtener la estancia
    public static DatabaseAccess getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    // Método para abrir la conexión con la Base de Datos
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    // Método para cerrar la conexión con la Base de Datos
    public void close() {
        if(database != null) {
            this.database.close();
        }
    }

    // METODOS PROPIOS------------------------------------------------------------------------------

    // Método que nos devuelve todas las recetas
    public ArrayList<Receta> obtenerReceta() {
        Cursor c;
        ArrayList<Receta> listadoRecetas = new ArrayList<>();

        // Consultamos a la Base de Datos y guardamos el resultado en el Cursor
        c = database.rawQuery("SELECT * FROM Recetas", null);

        if (c.moveToFirst()) { // Nos colocamos al principio del Cursor

            do { // Vamos añadiendo al ArrayList las Recetas con sus valores
                listadoRecetas.add(new Receta(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5)));
            } while (c.moveToNext());
        }

        return listadoRecetas;
    }

}
