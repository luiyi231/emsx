package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.producto.ProductoRequest;
import com.app.emsx.dtos.producto.ProductoResponse;
import com.app.emsx.entities.Producto;
import com.app.emsx.exceptions.BusinessRuleException;
import com.app.emsx.exceptions.ResourceNotFoundException;
import com.app.emsx.mappers.ProductoMapper;
import com.app.emsx.repositories.ProductoRepository;
import com.app.emsx.services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    public ProductoResponse create(ProductoRequest request) {
        // Validar que no exista un producto con el mismo nombre
        if (productoRepository.existsByName(request.getName())) {
            throw new BusinessRuleException("Ya existe un producto con el nombre: " + request.getName());
        }

        Producto producto = productoMapper.toEntity(request);
        return productoMapper.toResponse(productoRepository.save(producto));
    }

    @Override
    public ProductoResponse update(Long id, ProductoRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        // Validar nombre único si se actualiza
        if (request.getName() != null &&
                !request.getName().equals(producto.getName()) &&
                productoRepository.existsByName(request.getName())) {
            throw new BusinessRuleException("Ya existe otro producto con el nombre: " + request.getName());
        }

        productoMapper.updateEntityFromRequest(request, producto);
        return productoMapper.toResponse(productoRepository.save(producto));
    }

    @Override
    public List<ProductoResponse> findAll() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    @Override
    public ProductoResponse findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        return productoMapper.toResponse(producto);
    }

    @Override
    public void delete(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        productoRepository.delete(producto);
    }
}
