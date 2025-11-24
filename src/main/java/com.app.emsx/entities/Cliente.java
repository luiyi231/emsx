package com.app.emsx.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El nombre del cliente no puede estar vacío.")
    private String name;

    @Column(nullable = false, unique = true)
    @Email(message = "El email debe ser válido.")
    @NotBlank(message = "El email no puede estar vacío.")
    private String email;

    // Relación con pedidos
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private java.util.List<Pedido> pedidos = new java.util.ArrayList<>();
}
