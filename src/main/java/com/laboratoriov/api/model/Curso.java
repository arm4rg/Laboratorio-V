package com.laboratoriov.api.model;

public class Curso {

    private Integer id;
    private String nombre;
    private String descripcion;
    private int creditos;
    private String modalidad;

    public Curso() {
    }

    public Curso(Integer id, String nombre, String descripcion, int creditos, String modalidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
        this.modalidad = modalidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

}