package com.app.emsx.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "factura")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PastOrPresent(message = "La fecha no puede ser del futuro.")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @PositiveOrZero(message = "El total no puede ser negativo.")
    private Double total;

    @NotNull(message = "La factura debe estar asociada a un pedido.")
    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private Pedido pedido;
}
