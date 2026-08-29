package com.laboratoriov.api.controller;

import com.laboratoriov.api.controller.EmpleadoController;
import com.laboratoriov.api.model.Empleado;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final List<Empleado> empleados = new ArrayList<>(List.of(
        new Empleado(1, "Pedro Gómez", "Desarrollador", 6500.00, "TI"),
        new Empleado(2, "Laura Méndez", "Contadora", 5500.00, "Finanzas"),
        new Empleado(3, "Jorge Castillo", "Gerente", 9000.00, "Administración"),
        new Empleado(4, "Sofía Morales", "Diseñadora", 6000.00, "Diseño"),
        new Empleado(5, "Diego Herrera", "Soporte técnico", 4800.00, "TI")
    ));

    private int siguienteId = 6;

    @GetMapping
    public List<Empleado> obtenerTodos() {
        return empleados;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<Empleado> encontrado = empleados.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isPresent()) return ResponseEntity.ok(encontrado.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
    }

    @PostMapping
    public ResponseEntity<Empleado> crear(@RequestBody Empleado nuevo) {
        nuevo.setId(siguienteId++);
        empleados.add(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Empleado nuevo) {
        Optional<Empleado> encontrado = empleados.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        Empleado entidad = encontrado.get();
        entidad.setNombre(nuevo.getNombre());
        entidad.setPuesto(nuevo.getPuesto());
        entidad.setSalario(nuevo.getSalario());
        entidad.setDepartamento(nuevo.getDepartamento());
        return ResponseEntity.ok(entidad);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> datos) {
        Optional<Empleado> encontrado = empleados.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        Empleado entidad = encontrado.get();
        if (datos.containsKey("nombre")) entidad.setNombre(String.valueOf(datos.get("nombre")));
        if (datos.containsKey("puesto")) entidad.setPuesto(String.valueOf(datos.get("puesto")));
        if (datos.containsKey("salario")) entidad.setSalario(Double.parseDouble(datos.get("salario").toString()));
        if (datos.containsKey("departamento")) entidad.setDepartamento(String.valueOf(datos.get("departamento")));
        return ResponseEntity.ok(entidad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<Empleado> encontrado = empleados.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        empleados.remove(encontrado.get());
        return ResponseEntity.ok("Eliminado correctamente");
    }
}