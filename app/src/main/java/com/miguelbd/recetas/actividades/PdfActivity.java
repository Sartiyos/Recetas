package com.miguelbd.recetas.actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.miguelbd.recetas.R;

public class PdfActivity extends AppCompatActivity {

    private PDFView receta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        receta = (PDFView) findViewById(R.id.pdfView);

        Intent intent = getIntent();
        String uriReceta = intent.getStringExtra("receta");
        receta.fromAsset(uriReceta).load();

    }
}
