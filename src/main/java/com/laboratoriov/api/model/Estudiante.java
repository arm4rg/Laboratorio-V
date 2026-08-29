package com.laboratoriov.api.model;

public class Estudiante {

    private Integer id;
    private String nombre;
    private String apellido;
    private String carrera;
    private int edad;

    // Constructor vacío
    public Estudiante() {
    }

    // Constructor con todos los atributos
    public Estudiante(Integer id, String nombre, String apellido,
                      String carrera, int edad) {

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.carrera = carrera;
        this.edad = edad;
    }

    // Getters y Setters

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
