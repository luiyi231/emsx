package com.app.emsx.repositories;

import com.app.emsx.entities.Dependent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DependentRepository extends JpaRepository<Dependent, Long> {
    List<Dependent> findByEmployeeId(Long employeeId);
    boolean existsByDocumentNumber(String documentNumber);
}
