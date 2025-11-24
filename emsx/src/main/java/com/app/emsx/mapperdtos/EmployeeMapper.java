package com.app.emsx.mapperdtos;

import com.app.emsx.dtos.DependentDTO;
import com.app.emsx.dtos.EmployeeDTO;
import com.app.emsx.entities.Employee;
import com.app.emsx.entities.Skill;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public static EmployeeDTO mapEmployeeToEmployeeDTO(Employee employee) {
        if (employee == null) return null;

        // --- Dependents -> DTOs (null-safe)
        List<DependentDTO> dependentDTOs = null;
        if (employee.getDependents() != null) {
            dependentDTOs = employee.getDependents()
                    .stream()
                    .map(DependentMapper::toDTO)
                    .collect(Collectors.toList());
        }

        // --- Skills -> IDs (solo si usas ManyToMany Skill)
        List<Long> skillIds = null;
        try {
            if (employee.getSkills() != null) {
                skillIds = employee.getSkills().stream()
                        .map(Skill::getId)
                        .collect(Collectors.toList());
            }
        } catch (NoSuchMethodError | NullPointerException ignored) {
            // Si aún no tienes el campo skills en Employee, ignora.
        }

        // Usar constructor vacío + setters para evitar mismatch
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setAddress(employee.getAddress());
        dto.setPhone(employee.getPhone());
        dto.setDepartmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null);
        dto.setDependents(dependentDTOs);

        // Si tu EmployeeDTO tiene get/setSkillIds, se setea; si no, puedes borrar esta línea
        try { dto.getClass().getMethod("setSkillIds", List.class).invoke(dto, skillIds); } catch (Exception ignored) {}

        return dto;
    }

    public static Employee mapEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) return null;

        Employee e = new Employee();
        e.setId(employeeDTO.getId());
        e.setFirstName(employeeDTO.getFirstName());
        e.setLastName(employeeDTO.getLastName());
        e.setEmail(employeeDTO.getEmail());
        e.setAddress(employeeDTO.getAddress());
        e.setPhone(employeeDTO.getPhone());
        // Nota: el Department se resuelve en el Service usando departmentId.

        // DTO -> Dependents (mantener bidireccionalidad con addDependent)
        if (employeeDTO.getDependents() != null) {
            employeeDTO.getDependents().stream()
                    .filter(Objects::nonNull)
                    .map(DependentMapper::toEntity)
                    .forEach(e::addDependent);
        }

        // Skills: por buena práctica, resolver en Service a partir de dto.getSkillIds()
        // (no seteamos aquí para no acoplar el mapper con repositorios)

        return e;
    }
}
