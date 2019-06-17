package com.example.davidvillena.carta_electronica;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Plato  implements Serializable {

        // Tendría que crear una propiedad para guardar el ID del plato

        @Expose
        @SerializedName("Nombre")
        private String nombre ;

        @Expose
        @SerializedName("Precio")
        private String precio ;

        @Expose
        @SerializedName("Foto")
        private String foto ;

        @Expose
        @SerializedName("Ingredientes")
        private String ingredientes ;





    public Plato(String nombre, String precio,String ingredientes,String foto) {
        this.nombre = nombre;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.foto = foto;
    }

    public Plato() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
