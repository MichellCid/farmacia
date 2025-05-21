package com.example.posfarmacia.controllers;

import java.time.LocalDate;

public class producto {

    private int id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precioCompra;
    private double precioVenta;
    private int stock;
    private String categoria;
    private String ubicacion;
    private LocalDate fechaCaducidad;
    private boolean estado;

        // Constructor, getters y setters...


    public producto(){}

    public producto(int id, String codigo, String nombre,
                    String descripcion, double precioCompra, double precioVenta,
                    int stock, String categoria, String ubicacion, LocalDate fechaCaducidad, boolean estado) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
        this.fechaCaducidad = fechaCaducidad;
        this.estado = estado;
    }

    public producto(String codigo, String nombre,
                    String descripcion, double precioCompra, double precioVenta,
                    int stock, String categoria, String ubicacion, LocalDate fechaCaducidad) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
        this.fechaCaducidad = fechaCaducidad;
    }

    public producto(String codigo, String nombre, String descripcion, double precioVenta, int stock, String ubicacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.ubicacion = ubicacion;
    }

    public producto(int id, String codigo, String nombre, String descripcion, double precioVenta, int stock, String ubicacion) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.ubicacion = ubicacion;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
