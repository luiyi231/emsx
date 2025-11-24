package com.app.emsx.services;

import com.app.emsx.dtos.factura.FacturaRequest;
import com.app.emsx.dtos.factura.FacturaResponse;

import java.util.List;

public interface FacturaService {

    FacturaResponse create(FacturaRequest request);

    FacturaResponse update(Long id, FacturaRequest request);

    void delete(Long id);

    FacturaResponse findById(Long id);

    List<FacturaResponse> findAll();
}