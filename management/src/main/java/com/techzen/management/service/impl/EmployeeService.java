package com.techzen.management.service.impl;

import com.techzen.management.dto.employee.EmployeeSearchRequest;
import com.techzen.management.model.Employee;
import com.techzen.management.repository.EmployeeSpecification;
import com.techzen.management.repository.IEmployeeRepository;
import com.techzen.management.service.IEmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService implements IEmployeeService {

    IEmployeeRepository employeeRepository;

    @Override
    public Page<Employee> findByAttribute(EmployeeSearchRequest employeeSearchRequest, Pageable pageable) {
        Specification<Employee> specification = Specification.where(EmployeeSpecification.hasName(employeeSearchRequest.getName()))
                .and(EmployeeSpecification.hasDobFrom(employeeSearchRequest.getDobFrom()))
                .and(EmployeeSpecification.hasDobTo(employeeSearchRequest.getDobTo()))
                .and(EmployeeSpecification.hasGender(employeeSearchRequest.getGender()))
                .and(EmployeeSpecification.hasPhone(employeeSearchRequest.getPhone()))
                .and(EmployeeSpecification.hasDepartmentId(employeeSearchRequest.getDepartmentId()))
                .and(EmployeeSpecification.hasSalaryInRange(employeeSearchRequest.getSalaryRange()));

        return employeeRepository.findAll(specification, pageable);
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
        employeeRepository.deleteById(id);
    }
}
