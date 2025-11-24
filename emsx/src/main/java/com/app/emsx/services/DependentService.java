package com.app.emsx.services;

import com.app.emsx.dtos.DependentDTO;
import java.util.List;

public interface DependentService {
    List<DependentDTO> listByEmployee(Long employeeId);
    DependentDTO addToEmployee(Long employeeId, DependentDTO dto);
    DependentDTO update(Long employeeId, Long dependentId, DependentDTO dto);
    void remove(Long employeeId, Long dependentId);
}
