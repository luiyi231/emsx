package com.app.emsx.servicesimpls;

import com.app.emsx.dtos.DependentDTO;
import com.app.emsx.entities.Dependent;
import com.app.emsx.entities.Employee;
import com.app.emsx.mapperdtos.DependentMapper;
import com.app.emsx.repositories.DependentRepository;
import com.app.emsx.repositories.EmployeeRepository;
import com.app.emsx.services.DependentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class DependentServiceImpl implements DependentService {

    private final EmployeeRepository employeeRepository;
    private final DependentRepository dependentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DependentDTO> listByEmployee(Long employeeId) {
        verifyEmployee(employeeId);
        return dependentRepository.findByEmployeeId(employeeId)
                .stream()
                .map(DependentMapper::toDTO)
                .toList();
    }

    @Override
    public DependentDTO addToEmployee(Long employeeId, DependentDTO dto) {
        Employee employee = verifyEmployee(employeeId);

        if (dto.getDocumentNumber() != null &&
                dependentRepository.existsByDocumentNumber(dto.getDocumentNumber())) {
            throw new IllegalArgumentException("El documento ya existe para otro dependiente.");
        }

        Dependent dep = DependentMapper.toEntity(dto);
        employee.addDependent(dep);
        employeeRepository.save(employee);

        return DependentMapper.toDTO(dep);
    }

    @Override
    public DependentDTO update(Long employeeId, Long dependentId, DependentDTO dto) {
        Employee employee = verifyEmployee(employeeId);
        Dependent dep = dependentRepository.findById(dependentId)
                .orElseThrow(() -> new RuntimeException("Dependent no encontrado con id=" + dependentId));

        if (!dep.getEmployee().getId().equals(employee.getId())) {
            throw new IllegalArgumentException("El dependiente no pertenece a este empleado.");
        }

        dep.setFullName(dto.getFullName());
        dep.setRelationship(dto.getRelationship());
        dep.setBirthDate(dto.getBirthDate());
        dep.setDocumentNumber(dto.getDocumentNumber());
        dep.setIsStudent(dto.getIsStudent() != null ? dto.getIsStudent() : false);
        dep.setCoveragePercentage(dto.getCoveragePercentage() != null ? dto.getCoveragePercentage() : 0);

        return DependentMapper.toDTO(dep);
    }

    @Override
    public void remove(Long employeeId, Long dependentId) {
        Employee employee = verifyEmployee(employeeId);
        Dependent dep = dependentRepository.findById(dependentId)
                .orElseThrow(() -> new RuntimeException("Dependent no encontrado con id=" + dependentId));

        if (!dep.getEmployee().getId().equals(employee.getId())) {
            throw new IllegalArgumentException("El dependiente no pertenece a este empleado.");
        }

        employee.removeDependent(dep);
        employeeRepository.save(employee);
    }

    private Employee verifyEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee no encontrado con id=" + employeeId));
    }
}
