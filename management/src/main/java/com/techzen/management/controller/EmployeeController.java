package com.techzen.management.controller;

import com.techzen.management.exception.ApiException;
import com.techzen.management.exception.ErrorCode;
import com.techzen.management.model.Employee;
import com.techzen.management.dto.Gender;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final List<Employee> employees = new ArrayList<>(
            Arrays.asList(
                    new Employee(UUID.randomUUID(), "Lâm", LocalDate.of(1999, 9, 12), Gender.MALE, BigDecimal.valueOf(4500000), "0908333333", 1),
                    new Employee(UUID.randomUUID(), "Trường", LocalDate.of(2000, 11, 13), Gender.MALE, BigDecimal.valueOf(8000000), "0908333222", 2),
                    new Employee(UUID.randomUUID(), "Uyên", LocalDate.of(1992, 3, 15), Gender.FEMALE, BigDecimal.valueOf(15000000), "0908333111", 3),
                    new Employee(UUID.randomUUID(), "Luyện", LocalDate.of(1993, 2, 16), Gender.FEMALE, BigDecimal.valueOf(17000000), "0908333231", 4),
                    new Employee(UUID.randomUUID(), "Nguyên", LocalDate.of(1996, 6, 17), Gender.MALE, BigDecimal.valueOf(21000000), "0908333312", 2)
            ));

    @GetMapping
    public ResponseEntity<?> getEmployees(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "dobFrom", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dobFrom,
            @RequestParam(value = "dobTo", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dobTo,
            @RequestParam(value = "gender", required = false) Gender gender,
            @RequestParam(value = "salaryRange", required = false) String salaryRange,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "departmentId", required = false) Integer departmentId
    ) {
        List<Employee> filterEmployees = employees.stream()
                .filter(e -> (name == null || e.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(e -> (dobFrom == null || !e.getDateOfBirth().isBefore(dobFrom)))
                .filter(e -> (dobTo == null || !e.getDateOfBirth().isAfter(dobTo)))
                .filter(e -> (gender == null || e.getGender() == gender))
                .filter(e -> (phone == null || e.getPhone().contains(phone)))
                .filter(e -> (departmentId == null || Objects.equals(departmentId, e.getDepartmentId())))
                .filter(e -> {
                    if (salaryRange == null) {
                        return true;
                    }
                    return switch (salaryRange) {
                        case "lt5" ->
                                e.getSalary().compareTo(BigDecimal.valueOf(5000000)) < 0;           // value1.compareTo(value2)
                        case "5-10" ->                                                              // -1 nếu value1 < value2
                                e.getSalary().compareTo(BigDecimal.valueOf(5000000)) >= 0 &&        // 0 nếu value1 == value2
                                        e.getSalary().compareTo(BigDecimal.valueOf(10000000)) < 0;  // 1 nếu value1 > value2
                        case "10-20" -> e.getSalary().compareTo(BigDecimal.valueOf(10000000)) >= 0 &&
                                e.getSalary().compareTo(BigDecimal.valueOf(20000000)) < 0;
                        case "gt20" -> e.getSalary().compareTo(BigDecimal.valueOf(20000000)) > 0;
                        default -> false;
                    };
                })
                .toList();
        return ResponseEntity.ok(filterEmployees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable UUID id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiException(ErrorCode.EMPLOYEE_NOT_EXIST));
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
                .orElseThrow(() -> new ApiException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable UUID id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> {
                    employees.remove(e);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }
}
