package com.laboratoriov.api.controller;

import com.laboratoriov.api.controller.PedidoController;
import com.laboratoriov.api.model.Pedido;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final List<Pedido> pedidos = new ArrayList<>(List.of(
        new Pedido(1, "Juan Pérez", "Laptop", 1, 4500.00, "PENDIENTE"),
        new Pedido(2, "Ana López", "Mouse", 2, 300.00, "ENVIADO"),
        new Pedido(3, "Carlos Gómez", "Monitor", 1, 1800.00, "PAGADO"),
        new Pedido(4, "María Rodríguez", "Teclado", 1, 250.00, "PENDIENTE"),
        new Pedido(5, "Luis Hernández", "Audífonos", 2, 700.00, "ENTREGADO")
    ));

    private int siguienteId = 6;

    @GetMapping
    public List<Pedido> obtenerTodos() {
        return pedidos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<Pedido> encontrado = pedidos.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isPresent()) return ResponseEntity.ok(encontrado.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
    }

    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido nuevo) {
        nuevo.setId(siguienteId++);
        pedidos.add(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Pedido nuevo) {
        Optional<Pedido> encontrado = pedidos.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        Pedido entidad = encontrado.get();
        entidad.setCliente(nuevo.getCliente());
        entidad.setProducto(nuevo.getProducto());
        entidad.setCantidad(nuevo.getCantidad());
        entidad.setTotal(nuevo.getTotal());
        entidad.setEstado(nuevo.getEstado());
        return ResponseEntity.ok(entidad);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> datos) {
        Optional<Pedido> encontrado = pedidos.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        Pedido entidad = encontrado.get();
        if (datos.containsKey("cliente")) entidad.setCliente(String.valueOf(datos.get("cliente")));
        if (datos.containsKey("producto")) entidad.setProducto(String.valueOf(datos.get("producto")));
        if (datos.containsKey("cantidad")) entidad.setCantidad(Integer.parseInt(datos.get("cantidad").toString()));
        if (datos.containsKey("total")) entidad.setTotal(Double.parseDouble(datos.get("total").toString()));
        if (datos.containsKey("estado")) entidad.setEstado(String.valueOf(datos.get("estado")));
        return ResponseEntity.ok(entidad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<Pedido> encontrado = pedidos.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (encontrado.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        pedidos.remove(encontrado.get());
        return ResponseEntity.ok("Eliminado correctamente");
    }
}