package com.techzen.management.repository;

import com.techzen.management.model.Department;

import java.util.List;
import java.util.Optional;

public interface IDepartmentRepository {

    List<Department> getAll();

    Optional<Department> findById(Integer departmentId);

    Department save(Department department);

    void deleteDepartment(Integer departmentId);

}
