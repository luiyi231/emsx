package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.cliente.ClienteRequest;
import com.app.emsx.dtos.cliente.ClienteResponse;
import com.app.emsx.entities.Cliente;
import com.app.emsx.exceptions.BusinessRuleException;
import com.app.emsx.exceptions.ResourceNotFoundException;
import com.app.emsx.mappers.ClienteMapper;
import com.app.emsx.repositories.ClienteRepository;
import com.app.emsx.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public ClienteResponse create(ClienteRequest request) {
        // Validar que no exista un cliente con el mismo email
        if (clienteRepository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Ya existe un cliente con el email: " + request.getEmail());
        }

        Cliente cliente = clienteMapper.toEntity(request);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponse update(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));

        // Validar email único si se actualiza
        if (request.getEmail() != null &&
                !request.getEmail().equals(cliente.getEmail()) &&
                clienteRepository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Ya existe otro cliente con el email: " + request.getEmail());
        }

        clienteMapper.updateEntityFromRequest(request, cliente);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    public List<ClienteResponse> findAll() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toResponse)
                .toList();
    }

    @Override
    public ClienteResponse findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        return clienteMapper.toResponse(cliente);
    }

    @Override
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        clienteRepository.delete(cliente);
    }
}
