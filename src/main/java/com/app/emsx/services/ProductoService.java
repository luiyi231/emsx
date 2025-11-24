package com.app.emsx.services;

import com.app.emsx.dtos.producto.ProductoRequest;
import com.app.emsx.dtos.producto.ProductoResponse;

import java.util.List;

public interface ProductoService {

    ProductoResponse create(ProductoRequest request);

    ProductoResponse update(Long id, ProductoRequest request);

    void delete(Long id);

    ProductoResponse findById(Long id);

    List<ProductoResponse> findAll();
}
