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
    @jakarta.validation.constraints.Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras y espacios, sin números ni símbolos.")
    private String name;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El email debe ser válido.")
    @jakarta.validation.constraints.Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9._-]*[a-zA-Z0-9]@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El email debe tener un formato válido.")
    private String email;

    // Relación con pedidos
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private java.util.List<Pedido> pedidos = new java.util.ArrayList<>();
}
