package com.techzen.management.repository.impl;

import com.techzen.management.dto.EmployeeSearchRequest;
import com.techzen.management.enums.Gender;
import com.techzen.management.model.Employee;
import com.techzen.management.repository.IEmployeeRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeRepository implements IEmployeeRepository {

    List<Employee> employees = new ArrayList<>(
            Arrays.asList(
                    new Employee(UUID.randomUUID(), "Lâm", LocalDate.of(1999, 9, 12), Gender.MALE, BigDecimal.valueOf(4500000), "0908333333", 1),
                    new Employee(UUID.randomUUID(), "Trường", LocalDate.of(2000, 11, 13), Gender.MALE, BigDecimal.valueOf(8000000), "0908333222", 2),
                    new Employee(UUID.randomUUID(), "Uyên", LocalDate.of(1992, 3, 15), Gender.FEMALE, BigDecimal.valueOf(15000000), "0908333111", 3),
                    new Employee(UUID.randomUUID(), "Luyện", LocalDate.of(1993, 2, 16), Gender.FEMALE, BigDecimal.valueOf(17000000), "0908333231", 4),
                    new Employee(UUID.randomUUID(), "Nguyên", LocalDate.of(1996, 6, 17), Gender.MALE, BigDecimal.valueOf(21000000), "0908333312", 2)
            )
    );


    @Override
    public List<Employee> findByAttribute(EmployeeSearchRequest employeeSearchRequest) {
        List<Employee> filterEmployees = employees.stream()
                .filter(e -> (employeeSearchRequest.getName() == null || e.getName().toLowerCase().contains(employeeSearchRequest.getName().toLowerCase())))
                .filter(e -> (employeeSearchRequest.getDobFrom() == null || !e.getDateOfBirth().isBefore(employeeSearchRequest.getDobFrom())))
                .filter(e -> (employeeSearchRequest.getDobTo() == null || !e.getDateOfBirth().isAfter(employeeSearchRequest.getDobTo())))
                .filter(e -> (employeeSearchRequest.getGender() == null || e.getGender() == employeeSearchRequest.getGender()))
                .filter(e -> (employeeSearchRequest.getPhone() == null || e.getPhone().contains(employeeSearchRequest.getPhone())))
                .filter(e -> (employeeSearchRequest.getSalaryRange() == null || Objects.equals(employeeSearchRequest.getSalaryRange(), e.getDepartmentId())))
                .filter(e -> {
                    if (employeeSearchRequest.getSalaryRange() == null) {
                        return true;
                    }
                    return switch (employeeSearchRequest.getSalaryRange()) {
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
        return filterEmployees;
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    @Override
    public Employee save(Employee employee) {
        return findById(employee.getId())
                .map(
                        e -> {
                            e.setName(employee.getName());
                            e.setDateOfBirth(employee.getDateOfBirth());
                            e.setSalary(employee.getSalary());
                            e.setGender(employee.getGender());
                            e.setPhone(employee.getPhone());
                            return e;
                        }
                )
                .orElseGet(() -> {
                    employee.setId(UUID.randomUUID());
                    employees.add(employee);
                    return employee;
                });
    }

    @Override
    public void delete(UUID id) {
        findById(id).ifPresent(employees::remove);
    }
}
