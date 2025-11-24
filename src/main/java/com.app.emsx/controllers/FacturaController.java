package com.app.emsx.controllers;

import com.app.emsx.common.ApiResponse;
import com.app.emsx.dtos.factura.FacturaRequest;
import com.app.emsx.dtos.factura.FacturaResponse;
import com.app.emsx.services.FacturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService service;

    @PostMapping
    public ResponseEntity<ApiResponse<FacturaResponse>> create(@Valid @RequestBody FacturaRequest request) {
        FacturaResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Factura creada correctamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FacturaResponse>> update(
            @PathVariable Long id, @Valid @RequestBody FacturaRequest request) {
        FacturaResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Factura actualizada correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FacturaResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de facturas", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FacturaResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Factura encontrada", service.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Factura eliminada correctamente", null));
    }
}