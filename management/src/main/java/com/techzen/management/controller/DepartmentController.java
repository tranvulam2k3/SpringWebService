package com.techzen.management.controller;

import com.techzen.management.exception.ApiException;
import com.techzen.management.exception.ErrorCode;
import com.techzen.management.model.Department;
import com.techzen.management.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final List<Department> departments = new ArrayList<Department>(
            Arrays.asList(
                    new Department(1, "Quản Lý"),
                    new Department(2, "Kế Toán"),
                    new Department(3, "Sale-Marketing"),
                    new Department(4, "Sản xuất")
            )
    );

    @GetMapping
    public ResponseEntity<List<Department>> getDepartments() {
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int id) {
        return departments.stream()
                .filter(d -> d.getDepartmentId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiException(ErrorCode.DEPARTMENT_NOT_EXIST));
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        department.setDepartmentId((int) (Math.random() * 1000000));
        departments.add(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable int id, @RequestBody Department department) {
        return departments.stream()
                .filter(d -> d.getDepartmentId().equals(id))
                .findFirst()
                .map(d -> {
                    d.setDepartmentName(department.getDepartmentName());
                    return ResponseEntity.ok(d);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable int id) {
        return departments.stream()
                .filter(d -> d.getDepartmentId().equals(id))
                .findFirst()
                .map(d -> {
                    departments.remove(d);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }
}
