package com.techzen.management.service;

import com.techzen.management.model.Department;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {

    List<Department> getAllDepartments();

    Optional<Department> findById(Integer departmentId);

    Department save(Department department);

    void delete(Integer departmentId);

}
