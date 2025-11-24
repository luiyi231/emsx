package com.app.emsx.controllers;

import com.app.emsx.common.ApiResponse;
import com.app.emsx.dtos.cliente.ClienteRequest;
import com.app.emsx.dtos.cliente.ClienteResponse;
import com.app.emsx.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponse>> create(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Cliente creado correctamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> update(
            @PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        ClienteResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Cliente actualizado correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClienteResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de clientes", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Cliente encontrado", service.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Cliente eliminado correctamente", null));
    }
}
