package com.laboratoriov.api.controller;

import com.laboratoriov.api.controller.EstudianteController;
import com.laboratoriov.api.model.Estudiante;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")

public class EstudianteController {
  // Lista donde se almacenan los estudiantes
  private final List<Estudiante> estudiantes = new ArrayList<>();

  // ID para los nuevos estudiantes
  private int siguienteId = 6;

  // Constructor con datos iniciales
  public EstudianteController() {

    estudiantes.add(new Estudiante(
        1,
        "Carlos",
        "López",
        "Ingeniería en Sistemas",
        20));

    estudiantes.add(new Estudiante(
        2,
        "Ana",
        "García",
        "Administración",
        22));

    estudiantes.add(new Estudiante(
        3,
        "Luis",
        "Martínez",
        "Electrónica",
        21));

    estudiantes.add(new Estudiante(
        4,
        "María",
        "Pérez",
        "Contaduría",
        23));

    estudiantes.add(new Estudiante(
        5,
        "José",
        "Ramírez",
        "Informática",
        19));
  }

  // ==========================================
  // GET /api/estudiantes
  // Obtener todos los estudiantes
  // ==========================================

  @GetMapping
  public List<Estudiante> obtenerEstudiantes() {
    return estudiantes;
  }

  // ==========================================
  // GET /api/estudiantes/{id}
  // Obtener estudiante por ID
  // ==========================================

  @GetMapping("/{id}")
  public ResponseEntity<?> obtenerEstudiante(
      @PathVariable Integer id) {

    Optional<Estudiante> estudiante = estudiantes.stream()
        .filter(e -> e.getId().equals(id))
        .findFirst();

    if (estudiante.isPresent()) {
      return ResponseEntity.ok(estudiante.get());
    }

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Estudiante no encontrado");
  }

  // ==========================================
  // POST /api/estudiantes
  // Crear estudiante
  // ==========================================

  @PostMapping
  public ResponseEntity<Estudiante> crearEstudiante(
      @RequestBody Estudiante estudiante) {

    estudiante.setId(siguienteId++);

    estudiantes.add(estudiante);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(estudiante);
  }

  // ==========================================
  // PUT /api/estudiantes/{id}
  // Reemplazar estudiante
  // ==========================================

  @PutMapping("/{id}")
  public ResponseEntity<?> actualizarEstudiante(
      @PathVariable Integer id,
      @RequestBody Estudiante estudianteNuevo) {

    Optional<Estudiante> estudianteEncontrado = estudiantes.stream()
        .filter(e -> e.getId().equals(id))
        .findFirst();

    if (estudianteEncontrado.isEmpty()) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body("Estudiante no encontrado");
    }

    Estudiante estudiante = estudianteEncontrado.get();

    estudiante.setNombre(estudianteNuevo.getNombre());
    estudiante.setApellido(estudianteNuevo.getApellido());
    estudiante.setCarrera(estudianteNuevo.getCarrera());
    estudiante.setEdad(estudianteNuevo.getEdad());

    return ResponseEntity.ok(estudiante);
  }

  // ==========================================
  // PATCH /api/estudiantes/{id}
  // Actualizar parcialmente
  // ==========================================

  @PatchMapping("/{id}")
  public ResponseEntity<?> actualizarParcialmente(
      @PathVariable Integer id,
      @RequestBody Map<String, Object> datos) {

    Optional<Estudiante> estudianteEncontrado = estudiantes.stream()
        .filter(e -> e.getId().equals(id))
        .findFirst();

    if (estudianteEncontrado.isEmpty()) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body("Estudiante no encontrado");
    }

    Estudiante estudiante = estudianteEncontrado.get();

    if (datos.containsKey("nombre")) {
      estudiante.setNombre(
          String.valueOf(datos.get("nombre")));
    }

    if (datos.containsKey("apellido")) {
      estudiante.setApellido(
          String.valueOf(datos.get("apellido")));
    }

    if (datos.containsKey("carrera")) {
      estudiante.setCarrera(
          String.valueOf(datos.get("carrera")));
    }

    if (datos.containsKey("edad")) {
      estudiante.setEdad(
          Integer.parseInt(
              datos.get("edad").toString()));
    }

    return ResponseEntity.ok(estudiante);
  }

  // ==========================================
  // DELETE /api/estudiantes/{id}
  // Eliminar estudiante
  // ==========================================

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarEstudiante(
      @PathVariable Integer id) {

    Optional<Estudiante> estudianteEncontrado = estudiantes.stream()
        .filter(e -> e.getId().equals(id))
        .findFirst();

    if (estudianteEncontrado.isEmpty()) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body("Estudiante no encontrado");
    }

    estudiantes.remove(estudianteEncontrado.get());

    return ResponseEntity.ok(
        "Estudiante eliminado correctamente");
  }

}