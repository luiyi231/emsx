package com.app.emsx.services;

import com.app.emsx.dtos.pedido.PedidoRequest;
import com.app.emsx.dtos.pedido.PedidoResponse;

import java.util.List;

public interface PedidoService {

    PedidoResponse create(PedidoRequest request);

    PedidoResponse update(Long id, PedidoRequest request);

    void delete(Long id);

    PedidoResponse findById(Long id);

    List<PedidoResponse> findAll();
}
