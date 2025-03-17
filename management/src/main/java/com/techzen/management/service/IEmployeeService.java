package com.techzen.management.service;

import com.techzen.management.dto.employee.EmployeeSearchRequest;
import com.techzen.management.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IEmployeeService {

    Page<Employee> findByAttribute(EmployeeSearchRequest employeeSearchRequest, Pageable pageable);

    Optional<Employee> findById(UUID id);

    Employee save(Employee employee);

    void delete(UUID id);
}
