package com.miguelbd.recetas.basededatos;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {

    // Nombre de la Base de Datos con la que vamos a trabajar
    private static final String DATABASE_NAME = "recetas.db";

    // Versión de la Base de Datos
    private static final int DATABASE_VERSION = 1;

    // Constructor de la clase
    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
