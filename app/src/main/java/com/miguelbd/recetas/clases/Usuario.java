package com.miguelbd.recetas.clases;

public class Usuario {

    // Atributos de la clase Usuario

    private String usuario;
    private String password;
    private String nombre;
    private String apellidos;
    private String fecha;
    private String email;
    private String telefono;
    private String foto;
    private double longitud;
    private double latitud;

    // Constructor de la clase

    public Usuario() {

    }

    public Usuario(String usuario, Double longitud, Double latitud, String email, String telefono) {
        this.usuario = usuario;
        this.longitud = longitud;
        this.latitud = latitud;
        this.email = email;
        this.telefono = telefono;
    }

    public Usuario(String usuario, String password, String nombre, String apellidos, String fecha, String email, String telefono, String foto) {
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fecha = fecha;
        this.email = email;
        this.telefono = telefono;
        this.foto = foto;
    }

    // Métodos Getters y Setters

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
}
