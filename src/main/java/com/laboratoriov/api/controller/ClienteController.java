package com.laboratoriov.api.controller;

import com.laboratoriov.api.controller.ClienteController;
import com.laboratoriov.api.model.Cliente;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final List<Cliente> clientes = new ArrayList<>(List.of(
        new Cliente(1, "Juan", "Pérez", "juan@gmail.com", "5555-1001"),
        new Cliente(2, "Ana", "López", "ana@gmail.com", "5555-1002"),
        new Cliente(3, "Carlos", "Gómez", "carlos@gmail.com", "5555-1003"),
        new Cliente(4, "María", "Rodríguez", "maria@gmail.com", "5555-1004"),
        new Cliente(5, "Luis", "Hernández", "luis@gmail.com", "5555-1005")
    ));

    private int siguienteId = 6;

    @GetMapping
    public List<Cliente> obtenerTodos() {
        return clientes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<Cliente> encontrado = clientes.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isPresent()) return ResponseEntity.ok(encontrado.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody Cliente nuevo) {
        nuevo.setId(siguienteId++);
        clientes.add(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Cliente nuevo) {
        Optional<Cliente> encontrado = clientes.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        Cliente entidad = encontrado.get();
        entidad.setNombre(nuevo.getNombre());
        entidad.setApellido(nuevo.getApellido());
        entidad.setCorreo(nuevo.getCorreo());
        entidad.setTelefono(nuevo.getTelefono());
        return ResponseEntity.ok(entidad);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> datos) {
        Optional<Cliente> encontrado = clientes.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        Cliente entidad = encontrado.get();
        if (datos.containsKey("nombre")) entidad.setNombre(String.valueOf(datos.get("nombre")));
        if (datos.containsKey("apellido")) entidad.setApellido(String.valueOf(datos.get("apellido")));
        if (datos.containsKey("correo")) entidad.setCorreo(String.valueOf(datos.get("correo")));
        if (datos.containsKey("telefono")) entidad.setTelefono(String.valueOf(datos.get("telefono")));
        return ResponseEntity.ok(entidad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<Cliente> encontrado = clientes.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        clientes.remove(encontrado.get());
        return ResponseEntity.ok("Eliminado correctamente");
    }
}