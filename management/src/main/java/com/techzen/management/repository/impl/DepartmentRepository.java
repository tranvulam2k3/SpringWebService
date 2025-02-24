package com.techzen.management.repository.impl;

import com.techzen.management.model.Department;
import com.techzen.management.repository.IDepartmentRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentRepository implements IDepartmentRepository {
    List<Department> departments = new ArrayList<Department>(
            Arrays.asList(
                    new Department(1, "Quản Lý"),
                    new Department(2, "Kế Toán"),
                    new Department(3, "Sale-Marketing"),
                    new Department(4, "Sản xuất")
            )
    );

    @Override
    public List<Department> getAll() {
        return departments;
    }

    @Override
    public Optional<Department> findById(Integer departmentId) {
        return departments.stream()
                .filter(d -> d.getDepartmentId().equals(departmentId))
                .findFirst();
    }

    @Override
    public Department save(Department department) {
        return findById(department.getDepartmentId())
                .map(
                        d -> {
                            d.setDepartmentName(department.getDepartmentName());
                            return d;
                        }
                )
                .orElseGet(() -> {
                    department.setDepartmentId((int) (Math.random() * 1000000));
                    departments.add(department);
                    return department;
                });
    }

    @Override
    public void deleteDepartment(Integer departmentId) {
        findById(departmentId).ifPresent(departments::remove);
    }
}
