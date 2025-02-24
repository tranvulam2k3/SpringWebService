package com.techzen.management.repository;

import com.techzen.management.dto.EmployeeSearchRequest;
import com.techzen.management.model.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEmployeeRepository {
    List<Employee> findByAttribute(EmployeeSearchRequest employeeSearchRequest);
    Optional<Employee> findById(UUID id);
    Employee save(Employee employee);
    void delete(UUID id);

}
