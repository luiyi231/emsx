package com.app.emsx.controllers;

import com.app.emsx.common.ApiResponse;
import com.app.emsx.dtos.pedido.PedidoRequest;
import com.app.emsx.dtos.pedido.PedidoResponse;
import com.app.emsx.services.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @PostMapping
    public ResponseEntity<ApiResponse<PedidoResponse>> create(@Valid @RequestBody PedidoRequest request) {
        PedidoResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Pedido creado correctamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoResponse>> update(
            @PathVariable Long id, @Valid @RequestBody PedidoRequest request) {
        PedidoResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Pedido actualizado correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PedidoResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de pedidos", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Pedido encontrado", service.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Pedido eliminado correctamente", null));
    }
}
