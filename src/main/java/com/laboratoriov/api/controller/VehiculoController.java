package com.laboratoriov.api.controller;


import com.laboratoriov.api.controller.VehiculoController;
import com.laboratoriov.api.model.Vehiculo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final List<Vehiculo> vehiculos = new ArrayList<>(List.of(
        new Vehiculo(1, "Toyota", "Corolla", 2022, 145000.00),
        new Vehiculo(2, "Honda", "Civic", 2023, 175000.00),
        new Vehiculo(3, "Mazda", "CX-5", 2021, 190000.00),
        new Vehiculo(4, "Ford", "Ranger", 2024, 285000.00),
        new Vehiculo(5, "Nissan", "Versa", 2022, 125000.00)
    ));

    private int siguienteId = 6;

    @GetMapping
    public List<Vehiculo> obtenerTodos() {
        return vehiculos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<Vehiculo> encontrado = vehiculos.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isPresent()) return ResponseEntity.ok(encontrado.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
    }

    @PostMapping
    public ResponseEntity<Vehiculo> crear(@RequestBody Vehiculo nuevo) {
        nuevo.setId(siguienteId++);
        vehiculos.add(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Vehiculo nuevo) {
        Optional<Vehiculo> encontrado = vehiculos.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        Vehiculo entidad = encontrado.get();
        entidad.setMarca(nuevo.getMarca());
        entidad.setModelo(nuevo.getModelo());
        entidad.setAnio(nuevo.getAnio());
        entidad.setPrecio(nuevo.getPrecio());
        return ResponseEntity.ok(entidad);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> datos) {
        Optional<Vehiculo> encontrado = vehiculos.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        Vehiculo entidad = encontrado.get();
        if (datos.containsKey("marca")) entidad.setMarca(String.valueOf(datos.get("marca")));
        if (datos.containsKey("modelo")) entidad.setModelo(String.valueOf(datos.get("modelo")));
        if (datos.containsKey("anio")) entidad.setAnio(Integer.parseInt(datos.get("anio").toString()));
        if (datos.containsKey("precio")) entidad.setPrecio(Double.parseDouble(datos.get("precio").toString()));
        return ResponseEntity.ok(entidad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<Vehiculo> encontrado = vehiculos.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        vehiculos.remove(encontrado.get());
        return ResponseEntity.ok("Eliminado correctamente");
    }
}