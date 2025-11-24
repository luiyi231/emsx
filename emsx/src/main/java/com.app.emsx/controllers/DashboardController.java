package com.app.emsx.controllers;

import com.app.emsx.repositories.ClienteRepository;
import com.app.emsx.repositories.FacturaRepository;
import com.app.emsx.repositories.PedidoRepository;
import com.app.emsx.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite acceso desde el frontend
public class DashboardController {

    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final FacturaRepository facturaRepository;

    @GetMapping("/stats")
    public ResponseEntity<?> getDashboardStats() {
        try {
            // Totales generales
            long totalClientes = clienteRepository.count();
            long totalPedidos = pedidoRepository.count();
            long totalFacturas = facturaRepository.count();
            long totalProductos = productoRepository.count();

            // Pedidos por cliente
            List<Map<String, Object>> pedidosPorCliente = clienteRepository.findAll().stream()
                    .map(c -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("cliente", c.getName());
                        m.put("totalPedidos", c.getPedidos().size());
                        return m;
                    })
                    .collect(Collectors.toList());

            // Productos más vendidos (sumando cantidades de PedidoProducto)
            List<Map<String, Object>> topProductos = productoRepository.findAll().stream()
                    .map(p -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("name", p.getName());
                        m.put("vendidos", p.getPedidos().stream()
                                .mapToInt(pp -> pp.getCantidad())
                                .sum());
                        return m;
                    })
                    .sorted((a, b) -> ((Integer) b.get("vendidos")).compareTo((Integer) a.get("vendidos")))
                    .limit(5)
                    .collect(Collectors.toList());

            // Facturación por mes
            Map<String, Double> facturasPorMes = new TreeMap<>();
            facturaRepository.findAll().forEach(f -> {
                Calendar cal = Calendar.getInstance();
                cal.setTime(f.getFecha());
                String mes = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH) + " " + cal.get(Calendar.YEAR);
                facturasPorMes.put(mes, facturasPorMes.getOrDefault(mes, 0.0) + f.getTotal());
            });

            List<Map<String, Object>> facturasPorMesList = facturasPorMes.entrySet().stream()
                    .map(e -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("mes", e.getKey());
                        m.put("total", e.getValue());
                        return m;
                    })
                    .collect(Collectors.toList());

            // Construir respuesta final
            Map<String, Object> response = new HashMap<>();
            response.put("totalClientes", totalClientes);
            response.put("totalPedidos", totalPedidos);
            response.put("totalFacturas", totalFacturas);
            response.put("totalProductos", totalProductos);
            response.put("pedidosPorCliente", pedidosPorCliente);
            response.put("topProductos", topProductos);
            response.put("facturasPorMes", facturasPorMesList);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error cargando dashboard: " + e.getMessage()));
        }
    }
}
