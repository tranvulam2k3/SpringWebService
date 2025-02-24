package com.techzen.management.service.impl;

import com.techzen.management.dto.EmployeeSearchRequest;
import com.techzen.management.model.Employee;
import com.techzen.management.repository.IEmployeeRepository;
import com.techzen.management.service.IEmployeeService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService implements IEmployeeService {

    IEmployeeRepository employeeRepository;

    @Override
    public List<Employee> findByAttribute(EmployeeSearchRequest employeeSearchRequest) {
        return employeeRepository.findByAttribute(employeeSearchRequest);
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(UUID id) {
        employeeRepository.delete(id);
    }
}
