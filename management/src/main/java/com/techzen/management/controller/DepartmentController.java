package com.techzen.management.controller;

import com.techzen.management.exception.ApiException;
import com.techzen.management.enums.ErrorCode;
import com.techzen.management.model.Department;
import com.techzen.management.service.IDepartmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/departments")
public class DepartmentController {

    IDepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int id) {
        return departmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiException(ErrorCode.DEPARTMENT_NOT_EXIST));
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody Department department) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.save(department));
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<Department> updateDepartment(@PathVariable("departmentId") int departmentId, @RequestBody Department department) {
        departmentService.findById(departmentId).orElseThrow(() -> new ApiException(ErrorCode.DEPARTMENT_NOT_EXIST));
        department.setDepartment_id(departmentId);
        return ResponseEntity.ok(departmentService.save(department));
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<?> deleteDepartment(@PathVariable("departmentId") int departmentId) {
        departmentService.findById(departmentId).orElseThrow(() -> new ApiException(ErrorCode.DEPARTMENT_NOT_EXIST));
        departmentService.delete(departmentId);
        return ResponseEntity.noContent().build();
    }

}
