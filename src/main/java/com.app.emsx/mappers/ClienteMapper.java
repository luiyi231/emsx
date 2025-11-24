package com.app.emsx.mappers;

import com.app.emsx.dtos.cliente.ClienteRequest;
import com.app.emsx.dtos.cliente.ClienteResponse;
import com.app.emsx.entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {

    // ✅ DTO → Entity
    default Cliente toEntity(ClienteRequest dto) {
        if (dto == null) return null;
        Cliente entity = new Cliente();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        return entity;
    }

    // ✅ Entity → DTO
    default ClienteResponse toResponse(Cliente entity) {
        if (entity == null) return null;
        ClienteResponse dto = new ClienteResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());

        if (entity.getPedidos() != null) {
            dto.setPedidosIds(
                    entity.getPedidos()
                            .stream()
                            .map(p -> p.getId())
                            .collect(Collectors.toList())
            );
        } else {
            dto.setPedidosIds(new ArrayList<>());
        }

        return dto;
    }

    // ✅ Lista de conversiones
    default List<ClienteResponse> toResponseList(List<Cliente> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ✅ Actualización parcial desde request
    default void updateEntityFromRequest(ClienteRequest dto, Cliente entity) {
        if (dto == null || entity == null) return;
        if (dto.getName() != null && !dto.getName().isBlank()) entity.setName(dto.getName());
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) entity.setEmail(dto.getEmail());
    }
}
