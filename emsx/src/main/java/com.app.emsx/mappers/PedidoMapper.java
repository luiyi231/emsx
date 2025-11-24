package com.app.emsx.mappers;

import com.app.emsx.dtos.pedido.PedidoRequest;
import com.app.emsx.dtos.pedido.PedidoResponse;
import com.app.emsx.entities.Pedido;
import com.app.emsx.entities.PedidoProducto;
import com.app.emsx.entities.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoMapper {

    // ✅ DTO → Entity
    default Pedido toEntity(PedidoRequest dto, List<Producto> productosEntityList) {
        if (dto == null) return null;

        Pedido entity = new Pedido();
        entity.setFecha(dto.getFecha());
        entity.setTotal(dto.getTotal());

        // Mapear productos
        if (dto.getProductos() != null && productosEntityList != null) {
            List<PedidoProducto> pedidoProductos = new ArrayList<>();
            for (int i = 0; i < dto.getProductos().size(); i++) {
                PedidoRequest.PedidoProductoItem item = dto.getProductos().get(i);
                Producto producto = productosEntityList.stream()
                        .filter(p -> p.getId().equals(item.getProductoId()))
                        .findFirst()
                        .orElse(null);
                if (producto != null) {
                    PedidoProducto pp = new PedidoProducto();
                    pp.setProducto(producto);
                    pp.setPedido(entity);
                    pp.setCantidad(item.getCantidad());
                    pedidoProductos.add(pp);
                }
            }
            entity.setProductos(pedidoProductos);
        } else {
            entity.setProductos(new ArrayList<>());
        }

        return entity;
    }

    // ✅ Entity → DTO
    default PedidoResponse toResponse(Pedido entity) {
        if (entity == null) return null;

        PedidoResponse dto = new PedidoResponse();
        dto.setId(entity.getId());
        dto.setFecha(entity.getFecha());
        dto.setTotal(entity.getTotal());
        dto.setClienteId(entity.getCliente() != null ? entity.getCliente().getId() : null);

        // Mapear productos
        if (entity.getProductos() != null) {
            List<PedidoResponse.ProductoItemResponse> productos = entity.getProductos()
                    .stream()
                    .map(pp -> PedidoResponse.ProductoItemResponse.builder()
                            .productoId(pp.getProducto().getId())
                            .cantidad(pp.getCantidad())
                            .subtotal((double) pp.getCantidad() * pp.getProducto().getPrice())
                            .build())
                    .collect(Collectors.toList());
            dto.setProductos(productos);
        } else {
            dto.setProductos(new ArrayList<>());
        }

        return dto;
    }

    // ✅ Lista de conversiones
    default List<PedidoResponse> toResponseList(List<Pedido> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ✅ Actualización parcial desde request
    default void updateEntityFromRequest(PedidoRequest dto, Pedido entity, List<Producto> productosEntityList) {
        if (dto == null || entity == null) return;
        if (dto.getFecha() != null) entity.setFecha(dto.getFecha());
        if (dto.getTotal() != null) entity.setTotal(dto.getTotal());

        if (dto.getProductos() != null && productosEntityList != null) {
            // Limpiar lista anterior y crear nueva
            entity.getProductos().clear();
            List<PedidoProducto> pedidoProductos = new ArrayList<>();
            for (int i = 0; i < dto.getProductos().size(); i++) {
                PedidoRequest.PedidoProductoItem item = dto.getProductos().get(i);
                Producto producto = productosEntityList.stream()
                        .filter(p -> p.getId().equals(item.getProductoId()))
                        .findFirst()
                        .orElse(null);
                if (producto != null) {
                    PedidoProducto pp = new PedidoProducto();
                    pp.setProducto(producto);
                    pp.setPedido(entity);
                    pp.setCantidad(item.getCantidad());
                    pedidoProductos.add(pp);
                }
            }
            entity.setProductos(pedidoProductos);
        }
    }
}
