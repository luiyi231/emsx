package com.app.emsx.mapperdtos;

import com.app.emsx.dtos.DependentDTO;
import com.app.emsx.entities.Dependent;
import com.app.emsx.entities.Employee;

public class DependentMapper {

    // Convierte de entidad a DTO
    public static DependentDTO toDTO(Dependent d) {
        if (d == null) return null;
        return new DependentDTO(
                d.getId(),
                d.getFullName(),
                d.getRelationship(),
                d.getBirthDate(),
                d.getDocumentNumber(),
                d.getIsStudent(),
                d.getCoveragePercentage(),
                d.getEmployee() != null ? d.getEmployee().getId() : null
        );
    }

    // Convierte de DTO a entidad
    public static Dependent toEntity(DependentDTO dto) {
        if (dto == null) return null;
        Dependent d = new Dependent();
        d.setId(dto.getId());
        d.setFullName(dto.getFullName());
        d.setRelationship(dto.getRelationship());
        d.setBirthDate(dto.getBirthDate());
        d.setDocumentNumber(dto.getDocumentNumber());
        d.setIsStudent(dto.getIsStudent() != null ? dto.getIsStudent() : false);
        d.setCoveragePercentage(dto.getCoveragePercentage() != null ? dto.getCoveragePercentage() : 0);

        // Si se tiene employeeId, se puede crear un Employee “ligero” para asignar
        if (dto.getEmployeeId() != null) {
            Employee e = new Employee();
            e.setId(dto.getEmployeeId());
            d.setEmployee(e);
        }

        return d;
    }
}
