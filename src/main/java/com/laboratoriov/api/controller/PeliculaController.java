package com.laboratoriov.api.controller;

import com.laboratoriov.api.controller.PeliculaController;
import com.laboratoriov.api.model.Pelicula;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {

    private final List<Pelicula> peliculas = new ArrayList<>(List.of(
        new Pelicula(1, "Inception", "Christopher Nolan", "Ciencia ficción", 2010),
        new Pelicula(2, "The Matrix", "Lana y Lilly Wachowski", "Ciencia ficción", 1999),
        new Pelicula(3, "Titanic", "James Cameron", "Romance", 1997),
        new Pelicula(4, "Gladiator", "Ridley Scott", "Acción", 2000),
        new Pelicula(5, "Interstellar", "Christopher Nolan", "Ciencia ficción", 2014)
    ));

    private int siguienteId = 6;

    @GetMapping
    public List<Pelicula> obtenerTodos() {
        return peliculas;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<Pelicula> encontrado = peliculas.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isPresent()) return ResponseEntity.ok(encontrado.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
    }

    @PostMapping
    public ResponseEntity<Pelicula> crear(@RequestBody Pelicula nuevo) {
        nuevo.setId(siguienteId++);
        peliculas.add(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Pelicula nuevo) {
        Optional<Pelicula> encontrado = peliculas.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        Pelicula entidad = encontrado.get();
        entidad.setTitulo(nuevo.getTitulo());
        entidad.setDirector(nuevo.getDirector());
        entidad.setGenero(nuevo.getGenero());
        entidad.setAnio(nuevo.getAnio());
        return ResponseEntity.ok(entidad);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> datos) {
        Optional<Pelicula> encontrado = peliculas.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        Pelicula entidad = encontrado.get();
        if (datos.containsKey("titulo")) entidad.setTitulo(String.valueOf(datos.get("titulo")));
        if (datos.containsKey("director")) entidad.setDirector(String.valueOf(datos.get("director")));
        if (datos.containsKey("genero")) entidad.setGenero(String.valueOf(datos.get("genero")));
        if (datos.containsKey("anio")) entidad.setAnio(Integer.parseInt(datos.get("anio").toString()));
        return ResponseEntity.ok(entidad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<Pelicula> encontrado = peliculas.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        peliculas.remove(encontrado.get());
        return ResponseEntity.ok("Eliminado correctamente");
    }
}