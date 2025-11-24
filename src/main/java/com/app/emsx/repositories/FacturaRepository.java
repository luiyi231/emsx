package com.app.emsx.repositories;

import com.app.emsx.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    boolean existsByPedidoId(Long pedidoId);
}
