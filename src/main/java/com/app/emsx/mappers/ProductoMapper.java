package com.app.emsx.mappers;

import com.app.emsx.dtos.producto.ProductoRequest;
import com.app.emsx.dtos.producto.ProductoResponse;
import com.app.emsx.entities.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductoMapper {

    // ✅ DTO → Entity
    default Producto toEntity(ProductoRequest dto) {
        if (dto == null) return null;
        Producto entity = new Producto();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    // ✅ Entity → DTO
    default ProductoResponse toResponse(Producto entity) {
        if (entity == null) return null;
        ProductoResponse dto = new ProductoResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());

        if (entity.getPedidos() != null) {
            dto.setPedidosIds(
                    entity.getPedidos()
                            .stream()
                            .map(pp -> pp.getPedido().getId())
                            .collect(Collectors.toList())
            );
        } else {
            dto.setPedidosIds(new ArrayList<>());
        }

        return dto;
    }

    // ✅ Lista de conversiones
    default List<ProductoResponse> toResponseList(List<Producto> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ✅ Actualización parcial desde request
    default void updateEntityFromRequest(ProductoRequest dto, Producto entity) {
        if (dto == null || entity == null) return;
        if (dto.getName() != null && !dto.getName().isBlank()) entity.setName(dto.getName());
        if (dto.getPrice() != null) entity.setPrice(dto.getPrice());
    }
}
