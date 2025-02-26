package com.techzen.management.repository.impl;

import com.techzen.management.model.Department;
import com.techzen.management.repository.BaseRepository;
import com.techzen.management.repository.IDepartmentRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentRepository implements IDepartmentRepository {

    @Override
    public List<Department> getAll() {
        List<Department> departmentsList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement("select * from Department ");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                departmentsList.add(Department.builder()
                        .departmentId(resultSet.getInt("departmentId"))
                        .departmentName(resultSet.getString("departmentName"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return departmentsList;
    }

    @Override
    public Optional<Department> findById(Integer departmentId) {
        try {
            PreparedStatement preparedStatement = BaseRepository.getConnection()
                    .prepareStatement("SELECT * FROM Department WHERE departmentId = ?");
            preparedStatement.setInt(1, departmentId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Department department = Department.builder()
                        .departmentId(resultSet.getInt("departmentId"))
                        .departmentName(resultSet.getString("departmentName"))
                        .build();

                return Optional.of(department);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Department save(Department department) {
        try {
            boolean exists = department.getDepartmentId() != null && findById(department.getDepartmentId()).isPresent();

            if (!exists) {
                String sql = "INSERT INTO department(departmentName) VALUES (?)";
                try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, department.getDepartmentName());
                    preparedStatement.executeUpdate();

                    try (ResultSet res = preparedStatement.getGeneratedKeys()) {
                        if (res.next()) {
                            department.setDepartmentId(res.getInt(1)); // Lấy ID mới
                        }
                    }
                }
            } else {
                String sql = "UPDATE department SET departmentName=? WHERE departmentId=?";
                try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(sql)) {
                    preparedStatement.setString(1, department.getDepartmentName());
                    preparedStatement.setInt(2, department.getDepartmentId());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return department;
    }

    @Override
    public void deleteDepartment(Integer departmentId) {
        try {
            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement("delete from Department where departmentId = ?");
            preparedStatement.setInt(1, departmentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
