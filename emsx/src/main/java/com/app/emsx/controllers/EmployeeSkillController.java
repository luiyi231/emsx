package com.app.emsx.controllers;

import com.app.emsx.dtos.SkillDTO;
import com.app.emsx.services.EmployeeSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees/{employeeId}/skills")
public class EmployeeSkillController {

    private final EmployeeSkillService employeeSkillService;

    @GetMapping
    public ResponseEntity<List<SkillDTO>> list(@PathVariable Long employeeId){
        return ResponseEntity.ok(employeeSkillService.listSkills(employeeId));
    }

    @PostMapping("/{skillId}")
    public ResponseEntity<String> add(@PathVariable Long employeeId, @PathVariable Long skillId){
        employeeSkillService.addSkill(employeeId, skillId);
        return new ResponseEntity<>("Skill agregada al empleado.", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> replace(@PathVariable Long employeeId, @RequestBody List<Long> skillIds){
        employeeSkillService.replaceSkills(employeeId, skillIds);
        return ResponseEntity.ok("Skills reemplazadas.");
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<String> remove(@PathVariable Long employeeId, @PathVariable Long skillId){
        employeeSkillService.removeSkill(employeeId, skillId);
        return ResponseEntity.ok("Skill removida del empleado.");
    }
}
