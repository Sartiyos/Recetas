package com.miguelbd.recetas.clases;

public class Receta {

    // Atributos de la clase
    private int idReceta;
    private String nombreReceta;
    private String categoriaReceta;
    private float dificultadReceta;
    private String uriReceta;
    private String pdfReceta;

    // Constructor de la clase
    public Receta(int id, String nombre, String categ, String loc, float dificul, String pdf) {
        this.idReceta = id;
        this.nombreReceta = nombre;
        this.categoriaReceta = categ;
        this.uriReceta = loc;
        this.dificultadReceta = dificul;
        this.pdfReceta = pdf;
    }

    // Métodos GETTER y SETTER

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public String getNombreReceta() {
        return nombreReceta;
    }

    public void setNombreReceta(String nombreReceta) {
        this.nombreReceta = nombreReceta;
    }

    public String getCategoriaReceta() {
        return categoriaReceta;
    }

    public void setCategoriaReceta(String categoriaReceta) {
        this.categoriaReceta = categoriaReceta;
    }

    public float getDificultadReceta() {
        return dificultadReceta;
    }

    public void setDificultadReceta(float dificultadReceta) {
        this.dificultadReceta = dificultadReceta;
    }

    public String getUriReceta() {
        return uriReceta;
    }

    public void setUriReceta(String urlReceta) {
        this.uriReceta = urlReceta;
    }

    public String getPdfReceta() {
        return pdfReceta;
    }

    public void setPdfReceta(String pdfReceta) {
        this.pdfReceta = pdfReceta;
    }



}
