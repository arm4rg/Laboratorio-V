package com.laboratoriov.api.controller;

import com.laboratoriov.api.controller.LibroController;
import com.laboratoriov.api.model.Libro;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final List<Libro> libros = new ArrayList<>();

    private int siguienteId = 6;

    public LibroController() {

        libros.add(new Libro(1, "Cien años de soledad",
                "Gabriel García Márquez", "Novela", 120.00));

        libros.add(new Libro(2, "1984",
                "George Orwell", "Distopía", 95.00));

        libros.add(new Libro(3, "El principito",
                "Antoine de Saint-Exupéry", "Fantasía", 80.00));

        libros.add(new Libro(4, "Don Quijote de la Mancha",
                "Miguel de Cervantes", "Novela", 150.00));

        libros.add(new Libro(5, "Fahrenheit 451",
                "Ray Bradbury", "Ciencia ficción", 110.00));
    }

    @GetMapping
    public List<Libro> obtenerLibros() {
        return libros;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerLibro(@PathVariable Integer id) {

        Optional<Libro> libro = libros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();

        if (libro.isPresent()) {
            return ResponseEntity.ok(libro.get());
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Libro no encontrado");
    }

    @PostMapping
    public ResponseEntity<Libro> crearLibro(
            @RequestBody Libro libro) {

        libro.setId(siguienteId++);
        libros.add(libro);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(libro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLibro(
            @PathVariable Integer id,
            @RequestBody Libro libroNuevo) {

        Optional<Libro> encontrado = libros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();

        if (encontrado.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Libro no encontrado");
        }

        Libro libro = encontrado.get();

        libro.setTitulo(libroNuevo.getTitulo());
        libro.setAutor(libroNuevo.getAutor());
        libro.setGenero(libroNuevo.getGenero());
        libro.setPrecio(libroNuevo.getPrecio());

        return ResponseEntity.ok(libro);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcialmente(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> datos) {

        Optional<Libro> encontrado = libros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();

        if (encontrado.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Libro no encontrado");
        }

        Libro libro = encontrado.get();

        if (datos.containsKey("titulo")) {
            libro.setTitulo(String.valueOf(datos.get("titulo")));
        }

        if (datos.containsKey("autor")) {
            libro.setAutor(String.valueOf(datos.get("autor")));
        }

        if (datos.containsKey("genero")) {
            libro.setGenero(String.valueOf(datos.get("genero")));
        }

        if (datos.containsKey("precio")) {
            libro.setPrecio(Double.parseDouble(
                    datos.get("precio").toString()));
        }

        return ResponseEntity.ok(libro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLibro(
            @PathVariable Integer id) {

        Optional<Libro> encontrado = libros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();

        if (encontrado.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Libro no encontrado");
        }

        libros.remove(encontrado.get());

        return ResponseEntity.ok(
                "Libro eliminado correctamente");
    }
}

