package com.example.davidvillena.carta_electronica;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario implements Serializable {

    @Expose
    @SerializedName("idUsuario")
    private String idUsuario ;

    @Expose
    @SerializedName("nombre")
    private String nombre ;

    @Expose
    @SerializedName("apellidos")
    private String apellidos ;

    @Expose
    @SerializedName("telefono")
    private String telefono ;

    @Expose
    @SerializedName("email")
    private String email ;

    /** Constructor*/
    public Usuario() { }
    public Usuario(String idUsuario, String nombre,
                   String apellidos, String telefono, String email) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
    }

    public String getIdUsuario() {
        return idUsuario;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
