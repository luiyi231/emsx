package com.app.emsx.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto no puede estar vacío.")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres.")
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @jakarta.validation.constraints.Positive(message = "El precio debe ser mayor a 0.")
    @jakarta.validation.constraints.Max(value = 99999, message = "El precio no puede superar los 99999.")
    private Double price;

    @OneToMany(mappedBy = "producto")
    private List<PedidoProducto> pedidos = new ArrayList<>();
}
