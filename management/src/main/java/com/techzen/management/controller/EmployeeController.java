package com.techzen.management.controller;

import com.techzen.management.dto.employee.EmployeeRequest;
import com.techzen.management.dto.employee.EmployeeResponse;
import com.techzen.management.dto.employee.EmployeeSearchRequest;
import com.techzen.management.dto.page.PageResponse;
import com.techzen.management.exception.ApiException;
import com.techzen.management.enums.ErrorCode;
import com.techzen.management.mapper.IEmployeeMapper;
import com.techzen.management.model.Department;
import com.techzen.management.model.Employee;
import com.techzen.management.service.IDepartmentService;
import com.techzen.management.service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("${api.prefix}/employees")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    IEmployeeService employeeService;
    IEmployeeMapper employeeMapper;
    IDepartmentService departmentService;

    @GetMapping
    public ResponseEntity<?> getEmployees(EmployeeSearchRequest employeeSearchRequest, Pageable pageable) {
        return ResponseEntity.ok(new PageResponse<>(employeeService.findByAttribute(employeeSearchRequest, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable UUID id) {
        return employeeService.findById(id)
                .map(employeeMapper::employeeToEmployeeResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiException(ErrorCode.EMPLOYEE_NOT_EXIST));

    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.employeeRequestToEmployee(employeeRequest);
        Integer deptId = employeeRequest.getDepartment_id();
        Department department = departmentService.findById(deptId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_DEPARTMENT_NOT_EXIST));
        employee.setDepartment(department);
        employeeService.save(employee);
        EmployeeResponse employeeResponse = employeeMapper.employeeToEmployeeResponse(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") UUID id, @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.employeeRequestToEmployee(employeeRequest);
        Integer deptId = employeeRequest.getDepartment_id();
        Department department = departmentService.findById(deptId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_DEPARTMENT_NOT_EXIST));
        employee.setId(id);
        employee.setDepartment(department);
        employeeService.save(employee);
        EmployeeResponse employeeResponse = employeeMapper.employeeToEmployeeResponse(employee);
        return ResponseEntity.ok(employeeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") UUID id) {
        employeeService.findById(id).orElseThrow(() -> new ApiException(ErrorCode.EMPLOYEE_NOT_EXIST));
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
