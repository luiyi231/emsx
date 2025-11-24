package com.app.emsx.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;

    private Long departmentId;

    //permitir dependents embebidos en el request
    private List<DependentDTO> dependents;

    private java.util.List<Long> skillIds;

}
