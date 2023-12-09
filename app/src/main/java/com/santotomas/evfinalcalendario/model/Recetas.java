package com.santotomas.evfinalcalendario.model;

public class Recetas {
    private String uid;
    private String Nombre;
    private String Ingredientes;
    private String Descripcion;

    public Recetas() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getIngredientes() {
        return Ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        Ingredientes = ingredientes;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String toString() {
        return Nombre; // Suponiendo que 'nombre' es el atributo que deseas mostrar en la lista.
    }
}
