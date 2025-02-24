package com.techzen.management.service.impl;

import com.techzen.management.model.Department;
import com.techzen.management.repository.IDepartmentRepository;
import com.techzen.management.service.IDepartmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentService implements IDepartmentService {
    IDepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.getAll();
    }

    @Override
    public Optional<Department> findById(Integer departmentId) {
        return departmentRepository.findById(departmentId);
    }

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Integer departmentId) {
        departmentRepository.deleteDepartment(departmentId);
    }
}
