package com.app.emsx.services;

import com.app.emsx.dtos.cliente.ClienteRequest;
import com.app.emsx.dtos.cliente.ClienteResponse;

import java.util.List;

public interface ClienteService {

    ClienteResponse create(ClienteRequest request);

    ClienteResponse update(Long id, ClienteRequest request);

    void delete(Long id);

    ClienteResponse findById(Long id);

    List<ClienteResponse> findAll();
}
