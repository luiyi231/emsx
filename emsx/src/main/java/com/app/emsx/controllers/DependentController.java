package com.app.emsx.controllers;

import com.app.emsx.dtos.DependentDTO;
import com.app.emsx.services.DependentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.List;

@RestController
@RequestMapping("/api/employees/{employeeId}/dependents")
@RequiredArgsConstructor
public class DependentController {

    private final DependentService dependentService;

    @GetMapping
    public ResponseEntity<List<DependentDTO>> list(@PathVariable Long employeeId) {
        return ResponseEntity.ok(dependentService.listByEmployee(employeeId));
    }

    @PostMapping
    public ResponseEntity<DependentDTO> add(@PathVariable Long employeeId,
                                            @RequestBody DependentDTO dto) {
        DependentDTO created = dependentService.addToEmployee(employeeId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("{dependentId}")
    public ResponseEntity<DependentDTO> update(@PathVariable Long employeeId,
                                               @PathVariable Long dependentId,
                                               @RequestBody DependentDTO dto) {
        return ResponseEntity.ok(dependentService.update(employeeId, dependentId, dto));
    }

    @DeleteMapping("{dependentId}")
    public ResponseEntity<String> remove(@PathVariable Long employeeId,
                                         @PathVariable Long dependentId) {
        dependentService.remove(employeeId, dependentId);
        return ResponseEntity.ok("Dependent deleted successfully!");
    }
}
