
package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.pedido.PedidoRequest;
import com.app.emsx.dtos.pedido.PedidoResponse;
import com.app.emsx.entities.Cliente;
import com.app.emsx.entities.Pedido;
import com.app.emsx.entities.Producto;
import com.app.emsx.exceptions.BusinessRuleException;
import com.app.emsx.exceptions.ResourceNotFoundException;
import com.app.emsx.mappers.PedidoMapper;
import com.app.emsx.repositories.ClienteRepository;
import com.app.emsx.repositories.PedidoRepository;
import com.app.emsx.repositories.ProductoRepository;
import com.app.emsx.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@org.springframework.transaction.annotation.Transactional
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoMapper pedidoMapper;

    @Override
    public PedidoResponse create(PedidoRequest request) {
        // Validar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente no encontrado con ID: " + request.getClienteId()));

        // Validar y obtener productos
        List<Long> productoIds = request.getProductos().stream()
                .peek(item -> {
                    if (item.getCantidad() > 999) {
                        throw new BusinessRuleException(
                                "No puedes pedir más de 999 unidades del producto ID: " + item.getProductoId());
                    }
                })
                .map(PedidoRequest.PedidoProductoItem::getProductoId)
                .collect(Collectors.toList());

        List<Producto> productos = productoRepository.findAllById(productoIds);

        if (productos.size() != productoIds.size()) {
            throw new BusinessRuleException("Algunos productos no existen en la base de datos.");
        }

        // Mapear request a entidad
        Pedido pedido = pedidoMapper.toEntity(request, productos);
        pedido.setCliente(cliente);

        // Calcular total automáticamente
        double total = pedido.getProductos().stream()
                .mapToDouble(pp -> pp.getCantidad() * pp.getProducto().getPrice())
                .sum();
        pedido.setTotal(total);

        return pedidoMapper.toResponse(pedidoRepository.save(pedido));
    }

    @Override
    public PedidoResponse update(Long id, PedidoRequest request) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));

        // Validar cliente si se actualiza
        Cliente cliente = null;
        if (request.getClienteId() != null) {
            cliente = clienteRepository.findById(request.getClienteId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Cliente no encontrado con ID: " + request.getClienteId()));
        }

        // Validar y obtener productos
        List<Producto> productos = new ArrayList<>();
        if (request.getProductos() != null && !request.getProductos().isEmpty()) {
            List<Long> productoIds = request.getProductos().stream()
                    .map(PedidoRequest.PedidoProductoItem::getProductoId)
                    .collect(Collectors.toList());
            productos = productoRepository.findAllById(productoIds);
            if (productos.size() != productoIds.size()) {
                throw new BusinessRuleException("Algunos productos no existen en la base de datos.");
            }
        }

        // Actualizar entidad
        pedidoMapper.updateEntityFromRequest(request, pedido, productos);

        if (cliente != null) {
            pedido.setCliente(cliente);
        }

        // Recalcular total
        double total = pedido.getProductos().stream()
                .mapToDouble(pp -> pp.getCantidad() * pp.getProducto().getPrice())
                .sum();
        pedido.setTotal(total);

        return pedidoMapper.toResponse(pedidoRepository.save(pedido));
    }

    @Override
    public List<PedidoResponse> findAll() {
        return pedidoRepository.findAll().stream()
                .map(pedidoMapper::toResponse)
                .toList();
    }

    @Override
    public PedidoResponse findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    public void delete(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));
        pedidoRepository.delete(pedido);
    }
}
