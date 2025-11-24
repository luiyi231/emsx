package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.factura.FacturaRequest;
import com.app.emsx.dtos.factura.FacturaResponse;
import com.app.emsx.entities.Factura;
import com.app.emsx.entities.Pedido;
import com.app.emsx.exceptions.BusinessRuleException;
import com.app.emsx.exceptions.ResourceNotFoundException;
import com.app.emsx.mappers.FacturaMapper;
import com.app.emsx.repositories.FacturaRepository;
import com.app.emsx.repositories.PedidoRepository;
import com.app.emsx.services.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;
    private final PedidoRepository pedidoRepository;
    private final FacturaMapper facturaMapper;

    @Override
    public FacturaResponse create(FacturaRequest request) {
        // Validar que el pedido exista
        Pedido pedido = pedidoRepository.findById(request.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pedido no encontrado con ID: " + request.getPedidoId()));

        // Validar que no exista ya una factura para este pedido (única por pedido)
        if (facturaRepository.existsByPedidoId(pedido.getId())) {
            throw new BusinessRuleException("Ya existe una factura asociada a este pedido (ID: " + pedido.getId() + ")");
        }

        Factura factura = facturaMapper.toEntity(request, pedido);
        return facturaMapper.toResponse(facturaRepository.save(factura));
    }

    @Override
    public FacturaResponse update(Long id, FacturaRequest request) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con ID: " + id));

        // Validar pedido nuevo si se quiere actualizar
        Pedido pedido = null;
        if (request.getPedidoId() != null) {
            pedido = pedidoRepository.findById(request.getPedidoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Pedido no encontrado con ID: " + request.getPedidoId()));

            // Validar que no exista otra factura con ese pedido
            if (!factura.getPedido().getId().equals(pedido.getId()) &&
                    facturaRepository.existsByPedidoId(pedido.getId())) {
                throw new BusinessRuleException("Ya existe otra factura asociada a este pedido (ID: " + pedido.getId() + ")");
            }
        }

        facturaMapper.updateEntityFromRequest(request, factura, pedido);
        return facturaMapper.toResponse(facturaRepository.save(factura));
    }

    @Override
    public List<FacturaResponse> findAll() {
        return facturaRepository.findAll().stream()
                .map(facturaMapper::toResponse)
                .toList();
    }

    @Override
    public FacturaResponse findById(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con ID: " + id));
        return facturaMapper.toResponse(factura);
    }

    @Override
    public void delete(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con ID: " + id));
        facturaRepository.delete(factura);
    }
}
