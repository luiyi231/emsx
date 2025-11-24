package com.app.emsx.controllers;

import com.app.emsx.dtos.EmployeeDTO;
import com.app.emsx.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
   @Autowired
   private EmployeeService employeeService;

   //localhost:8080/api/employees
   @PostMapping
   public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO){
       EmployeeDTO savedEmployeeDTO = employeeService.createEmployee(employeeDTO);
       return new ResponseEntity<>(savedEmployeeDTO,  HttpStatus.CREATED);
   }

   //localhost:8080/api/employees
   @GetMapping
   public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
       List<EmployeeDTO> employees = employeeService.getEmployees();
       return ResponseEntity.ok(employees);
   }

    //localhost:8080/api/employees/1
    //localhost:8080/api/employees/5
    //localhost:8080/api/employees/10
   @GetMapping("{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id){
       EmployeeDTO employeeDTO = employeeService.getEmployee(id);
       return ResponseEntity.ok(employeeDTO);
   }

    //localhost:8080/api/employees/1
    //localhost:8080/api/employees/5
    //localhost:8080/api/employees/10
   @PutMapping("{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id,
                                                      @RequestBody EmployeeDTO employeeDTO){
       EmployeeDTO updatedEmployeeDTO = employeeService.updateEmployee(id, employeeDTO);
       return ResponseEntity.ok(updatedEmployeeDTO);
   }

   @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
       employeeService.deleteEmployee(id);
       return ResponseEntity.ok("Employee deleted successfully !!!.");
   }

}
