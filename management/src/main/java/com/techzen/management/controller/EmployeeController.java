package com.techzen.management.controller;

import com.techzen.management.dto.EmployeeSearchRequest;
import com.techzen.management.exception.ApiException;
import com.techzen.management.enums.ErrorCode;
import com.techzen.management.model.Employee;
import com.techzen.management.service.IEmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/employees")
public class EmployeeController {

    IEmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getEmployees(EmployeeSearchRequest employeeSearchRequest) {
        return ResponseEntity.ok(employeeService.findByAttribute(employeeSearchRequest));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable UUID id) {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") UUID id, @RequestBody Employee employee) {
        employeeService.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.EMPLOYEE_NOT_EXIST));
        employee.setId(id);
        return ResponseEntity.ok(employeeService.save(employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") UUID id) {
        employeeService.findById(id).orElseThrow(() -> new ApiException(ErrorCode.EMPLOYEE_NOT_EXIST));
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
