package com.app.emsx.dtos.producto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponse {

    private Long id;
    private String name;
    private Double price;

    private List<Long> pedidosIds;
}
