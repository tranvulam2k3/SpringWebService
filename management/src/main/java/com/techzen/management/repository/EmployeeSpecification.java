package com.techzen.management.repository;

import com.techzen.management.model.Employee;
import com.techzen.management.enums.Gender;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeSpecification {

    public static Specification<Employee> hasName(String name) {
        return (root, query, cb) ->
                (name == null || name.isEmpty()) ? null
                        : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Employee> hasDobFrom(LocalDate dobFrom) {
        return (root, query, cb) ->
                (dobFrom == null) ? null
                        : cb.greaterThanOrEqualTo(root.get("dob"), dobFrom);
    }

    public static Specification<Employee> hasDobTo(LocalDate dobTo) {
        return (root, query, cb) ->
                (dobTo == null) ? null
                        : cb.lessThanOrEqualTo(root.get("dob"), dobTo);
    }

    public static Specification<Employee> hasGender(Gender gender) {
        return (root, query, cb) ->
                (gender == null) ? null
                        : cb.equal(root.get("gender"), gender);
    }

    public static Specification<Employee> hasPhone(String phone) {
        return (root, query, cb) ->
                (phone == null || phone.isEmpty()) ? null
                        : cb.like(root.get("phone"), "%" + phone + "%");
    }

    public static Specification<Employee> hasDepartmentId(Integer departmentId) {
        return (root, query, cb) ->
                (departmentId == null) ? null
                        : cb.equal(root.join("department", JoinType.LEFT).get("id"), departmentId);
    }

    public static Specification<Employee> hasSalaryInRange(String salaryRange) {
        return (root, query, cb) -> {
            if (salaryRange == null) return null;

            BigDecimal fiveMillion = new BigDecimal("5000000");
            BigDecimal tenMillion = new BigDecimal("10000000");
            BigDecimal twentyMillion = new BigDecimal("20000000");

            return switch (salaryRange) {
                case "lt5" -> cb.lessThan(root.get("salary"), fiveMillion);
                case "5-10" -> cb.between(root.get("salary"), fiveMillion, tenMillion);
                case "10-20" -> cb.between(root.get("salary"), tenMillion, twentyMillion);
                case "gt20" -> cb.greaterThan(root.get("salary"), twentyMillion);
                default -> null;
            };
        };
    }
}
