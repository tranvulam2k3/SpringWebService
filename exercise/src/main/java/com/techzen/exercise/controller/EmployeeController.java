package com.techzen.exercise.controller;

import com.techzen.exercise.model.Employee;
import com.techzen.exercise.model.Gender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final List<Employee> employees = new ArrayList<>(
            Arrays.asList(
                    new Employee(UUID.randomUUID(), "Văn A", LocalDate.of(1999, 9, 12), Gender.MALE, BigDecimal.valueOf(15000000), "0908333333"),
                    new Employee(UUID.randomUUID(), "Văn B", LocalDate.of(2000, 11, 13), Gender.MALE, BigDecimal.valueOf(13000000), "0908333222"),
                    new Employee(UUID.randomUUID(), "Văn c", LocalDate.of(1992, 3, 15), Gender.FEMALE, BigDecimal.valueOf(15000000), "0908333111"),
                    new Employee(UUID.randomUUID(), "Văn D", LocalDate.of(1993, 2, 16), Gender.FEMALE, BigDecimal.valueOf(15000000), "0908333231"),
                    new Employee(UUID.randomUUID(), "Văn E", LocalDate.of(1996, 6, 17), Gender.MALE, BigDecimal.valueOf(15000000), "0908333312")
            ));

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable UUID id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        employee.setId(UUID.randomUUID());
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable UUID id, @RequestBody Employee employee) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> {
                    e.setName(employee.getName());
                    e.setDateOfBirth(employee.getDateOfBirth());
                    e.setSalary(employee.getSalary());
                    e.setGender(employee.getGender());
                    e.setPhone(employee.getPhone());
                    return ResponseEntity.ok(e);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable UUID id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> {
                    employees.remove(e);
                    return ResponseEntity.ok(e);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
