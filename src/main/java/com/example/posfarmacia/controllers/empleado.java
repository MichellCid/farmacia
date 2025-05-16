package com.example.posfarmacia.controllers;

import java.util.Date;

public class empleado {
    private String id;
    private String nombre;
    private String nomUsuario;
    private String contraseña;
    private String rol;
    private boolean status;
    private String direccion;
    private String telefono;
    private double sueldo;
    private Date fechaIn;

    public empleado() {
    }

    public empleado(String nombre, String nomUsuario, String contraseña, boolean status, String direccion, String telefono, double sueldo, Date fechaIn) {
        this.nombre = nombre;
        this.nomUsuario = nomUsuario;
        this.contraseña = contraseña;
        this.status = status;
        this.direccion = direccion;
        this.telefono = telefono;
        this.sueldo = sueldo;
        this.fechaIn = fechaIn;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public Date getFechaIn() {
        return fechaIn;
    }

    public void setFechaIn(Date fechaIn) {
        this.fechaIn = fechaIn;
    }
}
