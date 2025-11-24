package com.app.emsx.mappers;

import com.app.emsx.dtos.factura.FacturaRequest;
import com.app.emsx.dtos.factura.FacturaResponse;
import com.app.emsx.entities.Factura;
import com.app.emsx.entities.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FacturaMapper {

    // ✅ DTO → Entity
    default Factura toEntity(FacturaRequest dto, Pedido pedidoEntity) {
        if (dto == null) return null;
        Factura entity = new Factura();
        entity.setFecha(dto.getFecha());
        entity.setTotal(dto.getTotal());
        entity.setPedido(pedidoEntity);
        return entity;
    }

    // ✅ Entity → DTO
    default FacturaResponse toResponse(Factura entity) {
        if (entity == null) return null;
        FacturaResponse dto = new FacturaResponse();
        dto.setId(entity.getId());
        dto.setFecha(entity.getFecha());
        dto.setTotal(entity.getTotal());
        dto.setPedidoId(entity.getPedido() != null ? entity.getPedido().getId() : null);
        return dto;
    }

    // ✅ Lista de conversiones
    default List<FacturaResponse> toResponseList(List<Factura> entities) {
        if (entities == null) return new ArrayList<>();
        List<FacturaResponse> list = new ArrayList<>();
        for (Factura f : entities) {
            list.add(toResponse(f));
        }
        return list;
    }

    // ✅ Actualización parcial desde request
    default void updateEntityFromRequest(FacturaRequest dto, Factura entity, Pedido pedidoEntity) {
        if (dto == null || entity == null) return;
        if (dto.getFecha() != null) entity.setFecha(dto.getFecha());
        if (dto.getTotal() != null) entity.setTotal(dto.getTotal());
        if (pedidoEntity != null) entity.setPedido(pedidoEntity);
    }
}