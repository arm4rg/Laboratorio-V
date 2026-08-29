package com.laboratoriov.api.controller;

import com.laboratoriov.api.controller.CursoController;
import com.laboratoriov.api.model.Curso;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final List<Curso> cursos = new ArrayList<>(List.of(
        new Curso(1, "Programación Java", "Fundamentos de Java", 4, "Presencial"),
        new Curso(2, "Bases de Datos", "SQL y diseño de bases de datos", 4, "Virtual"),
        new Curso(3, "Electrónica Digital", "Lógica combinacional y secuencial", 3, "Presencial"),
        new Curso(4, "Redes", "Fundamentos de redes", 4, "Híbrida"),
        new Curso(5, "Desarrollo Web", "HTML, CSS y JavaScript", 3, "Virtual")
    ));

    private int siguienteId = 6;

    @GetMapping
    public List<Curso> obtenerTodos() {
        return cursos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<Curso> encontrado = cursos.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isPresent()) return ResponseEntity.ok(encontrado.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
    }

    @PostMapping
    public ResponseEntity<Curso> crear(@RequestBody Curso nuevo) {
        nuevo.setId(siguienteId++);
        cursos.add(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Curso nuevo) {
        Optional<Curso> encontrado = cursos.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        Curso entidad = encontrado.get();
        entidad.setNombre(nuevo.getNombre());
        entidad.setDescripcion(nuevo.getDescripcion());
        entidad.setCreditos(nuevo.getCreditos());
        entidad.setModalidad(nuevo.getModalidad());
        return ResponseEntity.ok(entidad);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> datos) {
        Optional<Curso> encontrado = cursos.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        Curso entidad = encontrado.get();
        if (datos.containsKey("nombre")) entidad.setNombre(String.valueOf(datos.get("nombre")));
        if (datos.containsKey("descripcion")) entidad.setDescripcion(String.valueOf(datos.get("descripcion")));
        if (datos.containsKey("creditos")) entidad.setCreditos(Integer.parseInt(datos.get("creditos").toString()));
        if (datos.containsKey("modalidad")) entidad.setModalidad(String.valueOf(datos.get("modalidad")));
        return ResponseEntity.ok(entidad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<Curso> encontrado = cursos.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        cursos.remove(encontrado.get());
        return ResponseEntity.ok("Eliminado correctamente");
    }
}