package com.app.emsx.servicesimpls;

import com.app.emsx.dtos.EmployeeDTO;
import com.app.emsx.entities.Department;
import com.app.emsx.entities.Employee;
import com.app.emsx.exceptions.ResourceNotFoundException;
import com.app.emsx.mapperdtos.DependentMapper;
import com.app.emsx.mapperdtos.EmployeeMapper;
import com.app.emsx.repositories.DepartmentRepository;
import com.app.emsx.repositories.EmployeeRepository;
import com.app.emsx.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.mapEmployeeDTOToEmployee(employeeDTO);

        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department is not exists with id: " + employeeDTO.getDepartmentId()));

        employee.setDepartment(department);


//        Employee savedEmployee = employeeRepository.save(employee);
//        return EmployeeMapper.mapEmployeeToEmployeeDTO(savedEmployee);

        return EmployeeMapper.mapEmployeeToEmployeeDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO updateEmployee(Long employeeID, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(employeeID).orElseThrow(
                ()-> new ResourceNotFoundException("Employee not found with id " + employeeID)
        );
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhone(employeeDTO.getPhone());
        employee.setAddress(employeeDTO.getAddress());

        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department is not exists with id: " + employeeDTO.getDepartmentId()));

        employee.setDepartment(department);

        // Reemplazar todos los dependents por los del DTO
        employee.getDependents().forEach(d -> d.setEmployee(null));
        employee.getDependents().clear();

        if (employeeDTO.getDependents() != null) {
            employeeDTO.getDependents().forEach(depDto ->
                    employee.addDependent(DependentMapper.toEntity(depDto))
            );
        }


        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapEmployeeToEmployeeDTO(updatedEmployee);

        //return EmployeeMapper.mapEmployeeToEmployeeDTO(employeeRepository.save(employee));
        //La linea 43 y 44 puede ser reemplazada por la linea 46. Son lo mismo.
    }

    @Override
    public String deleteEmployee(Long employeeID) {
        Employee employee = employeeRepository.findById(employeeID).orElseThrow(
                ()-> new ResourceNotFoundException("Employee not found with id " + employeeID)
        );
        employeeRepository.delete(employee);
        return "Employee has been deleted";
    }

    @Override
    public EmployeeDTO getEmployee(Long employeeID) {
        Employee employee = employeeRepository.findById(employeeID).orElseThrow(
                ()-> new ResourceNotFoundException("Employee not found with id " + employeeID)
        );
        return EmployeeMapper.mapEmployeeToEmployeeDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(
                (EmployeeMapper::mapEmployeeToEmployeeDTO
                )).collect(Collectors.toList());
    }
}
