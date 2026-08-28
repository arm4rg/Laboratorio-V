package com.laboratoriov.api.controller;

import com.laboratoriov.api.model.Producto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")


public class ProductoController {
  private List<Producto> productos = new ArrayList<>(
        List.of(
            new Producto(1L, "Laptop", 7500.00, "Tecnología"),
            new Producto(2L, "Mouse", 150.00, "Accesorios"),
            new Producto(3L, "Tablet", 2500.00, "Tecnología"),
            new Producto(4L, "Mousepad", 25.50, "Accesorios"),
            new Producto(5L, "Escritorio", 900.99, "Mobiliario")
        )
    );

    // GET - Obtener todos
    @GetMapping
    public ResponseEntity<?> obtenerProductos() {
        return ResponseEntity.ok(
            Map.of(
                "mensaje", "Productos obtenidos correctamente",
                "total", productos.size(),
                "datos", productos
            )
        );
    }

    // GET - Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProducto(@PathVariable Long id) {

        for (Producto producto : productos) {
            if (producto.getId().equals(id)) {
                return ResponseEntity.ok(
                    Map.of(
                        "mensaje", "Producto encontrado",
                        "datos", producto
                    )
                );
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            Map.of(
                "mensaje", "Producto no encontrado",
                "codigo", 404
            )
        );
    }

    // POST - Crear producto
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {

        // Validación simple
        if (producto.getNombre() == null || producto.getNombre().isBlank()
                || producto.getPrecio() == null || producto.getPrecio() <= 0) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                    "mensaje", "Datos inválidos",
                    "codigo", 400
                )
            );
        }

        // Validar producto duplicado
        for (Producto item : productos) {
            if (item.getNombre().equalsIgnoreCase(producto.getNombre())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of(
                        "mensaje", "Ya existe un producto con ese nombre",
                        "codigo", 409
                    )
                );
            }
        }

        producto.setId((long) productos.size() + 1);
        productos.add(producto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
            Map.of(
                "mensaje", "Producto creado correctamente",
                "codigo", 201,
                "datos", producto
            )
        );
    }


@PutMapping("/{id}")
public ResponseEntity<?> actualizarProducto(
        @PathVariable Long id,
        @RequestBody Producto productoActualizado) {

    for (Producto producto : productos) {
        if (producto.getId().equals(id)) {

            if (productoActualizado.getNombre() == null
                    || productoActualizado.getNombre().isBlank()
                    || productoActualizado.getPrecio() == null
                    || productoActualizado.getPrecio() <= 0) {

                return ResponseEntity.badRequest().body(
                    Map.of(
                        "mensaje", "Datos inválidos",
                        "codigo", 400
                    )
                );
            }

            producto.setNombre(productoActualizado.getNombre());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setCategoria(productoActualizado.getCategoria());

            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "Producto actualizado correctamente",
                    "codigo", 200,
                    "datos", producto
                )
            );
        }
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        Map.of(
            "mensaje", "Producto no encontrado",
            "codigo", 404
        )
    );
}

@PatchMapping("/{id}")
public ResponseEntity<?> actualizarParcialmente(
        @PathVariable Long id,
        @RequestBody Producto productoActualizado) {

    for (Producto producto : productos) {
        if (producto.getId().equals(id)) {

            if (productoActualizado.getNombre() != null) {

                if (productoActualizado.getNombre().isBlank()) {
                    return ResponseEntity.badRequest().body(
                        Map.of(
                            "mensaje", "El nombre no puede estar vacío",
                            "codigo", 400
                        )
                    );
                }

                producto.setNombre(productoActualizado.getNombre());
            }

            if (productoActualizado.getPrecio() != null) {

                if (productoActualizado.getPrecio() <= 0) {
                    return ResponseEntity.badRequest().body(
                        Map.of(
                            "mensaje", "El precio debe ser mayor que cero",
                            "codigo", 400
                        )
                    );
                }

                producto.setPrecio(productoActualizado.getPrecio());
            }

            if (productoActualizado.getCategoria() != null) {
                producto.setCategoria(productoActualizado.getCategoria());
            }

            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "Producto actualizado parcialmente",
                    "codigo", 200,
                    "datos", producto
                )
            );
        }
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        Map.of(
            "mensaje", "Producto no encontrado",
            "codigo", 404
        )
    );
}
@DeleteMapping("/{id}")
public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {

    for (Producto producto : productos) {
        if (producto.getId().equals(id)) {

            productos.remove(producto);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        Map.of(
            "mensaje", "Producto no encontrado",
            "codigo", 404
        )
    );
}

}
