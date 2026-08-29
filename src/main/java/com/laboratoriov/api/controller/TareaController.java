package com.laboratoriov.api.controller;


import com.laboratoriov.api.controller.TareaController;
import com.laboratoriov.api.model.Tarea;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final List<Tarea> tareas = new ArrayList<>(List.of(
        new Tarea(1, "Estudiar Java", "Repasar REST", "ALTA", false),
        new Tarea(2, "Hacer laboratorio", "Completar APIs", "ALTA", false),
        new Tarea(3, "Leer capítulo", "Leer lógica secuencial", "MEDIA", true),
        new Tarea(4, "Practicar Git", "Subir cambios", "MEDIA", false),
        new Tarea(5, "Preparar exposición", "Crear diapositivas", "BAJA", false)
    ));

    private int siguienteId = 6;

    @GetMapping
    public List<Tarea> obtenerTodos() {
        return tareas;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<Tarea> encontrado = tareas.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isPresent()) return ResponseEntity.ok(encontrado.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
    }

    @PostMapping
    public ResponseEntity<Tarea> crear(@RequestBody Tarea nuevo) {
        nuevo.setId(siguienteId++);
        tareas.add(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Tarea nuevo) {
        Optional<Tarea> encontrado = tareas.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        Tarea entidad = encontrado.get();
        entidad.setTitulo(nuevo.getTitulo());
        entidad.setDescripcion(nuevo.getDescripcion());
        entidad.setPrioridad(nuevo.getPrioridad());
        entidad.setCompletada(nuevo.isCompletada());
        return ResponseEntity.ok(entidad);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> datos) {
        Optional<Tarea> encontrado = tareas.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        Tarea entidad = encontrado.get();
        if (datos.containsKey("titulo")) entidad.setTitulo(String.valueOf(datos.get("titulo")));
        if (datos.containsKey("descripcion")) entidad.setDescripcion(String.valueOf(datos.get("descripcion")));
        if (datos.containsKey("prioridad")) entidad.setPrioridad(String.valueOf(datos.get("prioridad")));
        if (datos.containsKey("completada")) entidad.setCompletada(Boolean.parseBoolean(datos.get("completada").toString()));
        return ResponseEntity.ok(entidad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<Tarea> encontrado = tareas.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        tareas.remove(encontrado.get());
        return ResponseEntity.ok("Eliminado correctamente");
    }
}