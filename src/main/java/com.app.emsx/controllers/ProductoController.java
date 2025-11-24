package com.app.emsx.controllers;

import com.app.emsx.common.ApiResponse;
import com.app.emsx.dtos.producto.ProductoRequest;
import com.app.emsx.dtos.producto.ProductoResponse;
import com.app.emsx.services.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductoResponse>> create(@Valid @RequestBody ProductoRequest request) {
        ProductoResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Producto creado correctamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoResponse>> update(
            @PathVariable Long id, @Valid @RequestBody ProductoRequest request) {
        ProductoResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Producto actualizado correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de productos", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Producto encontrado", service.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Producto eliminado correctamente", null));
    }
}
