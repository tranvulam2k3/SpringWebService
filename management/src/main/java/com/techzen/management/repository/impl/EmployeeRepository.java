package com.techzen.management.repository.impl;

import com.techzen.management.dto.EmployeeSearchRequest;
import com.techzen.management.enums.Gender;
import com.techzen.management.model.Employee;
import com.techzen.management.repository.BaseRepository;
import com.techzen.management.repository.IEmployeeRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeRepository implements IEmployeeRepository {

    @Override
    public List<Employee> findByAttribute(EmployeeSearchRequest employeeSearchRequest) {

        List<Employee> employeeList = new ArrayList<>();
        try {
            String query = "select * from Employee where 1=1";

            List<Object> parameters = new ArrayList<>();
            if (employeeSearchRequest.getName() != null) {
                query += " and lower(name) like ?";
                parameters.add("%" + employeeSearchRequest.getName().toLowerCase() + "%");
            }
            if (employeeSearchRequest.getGender() != null) {
                query += " and lower(gender) like ?";
                parameters.add("%" + employeeSearchRequest.getGender() + "%");
            }
            if (employeeSearchRequest.getPhone() != null) {
                query += " and lower(phone) like ?";
                parameters.add("%" + employeeSearchRequest.getPhone() + "%");
            }
            if (employeeSearchRequest.getDepartmentId() != null) {
                query += " and departmentId = ?";
                parameters.add(employeeSearchRequest.getDepartmentId());
            }
            if (employeeSearchRequest.getDobFrom() != null) {
                query += " and dateOfBirth >= ?";
                parameters.add(employeeSearchRequest.getDobFrom());
            }
            if (employeeSearchRequest.getDobTo() != null) {
                query += " and dateOfBirth <= ?";
                parameters.add(employeeSearchRequest.getDobTo());
            }
            if (employeeSearchRequest.getSalaryRange() != null) {
                switch (employeeSearchRequest.getSalaryRange()) {
                    case "lt5":
                        query += " and salary < 5000000";
                        break;
                    case "5-10":
                        query += " and salary >= 5000000 and salary <= 10000000";
                        break;
                    case "10-20":
                        query += " and salary >= 10000000 and salary <= 20000000";
                        break;
                    case "gt20":
                        query += " and salary >= 20000000";
                        break;
                }
            }

            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(query);

            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i) instanceof LocalDate) {
                    preparedStatement.setDate(i + 1, Date.valueOf((LocalDate) parameters.get(i)));
                } else if (parameters.get(i) instanceof Integer) {
                    preparedStatement.setInt(i + 1, (Integer) parameters.get(i));
                } else {
                    preparedStatement.setObject(i + 1, parameters.get(i));
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee(UUID.fromString(
                        resultSet.getString("id")),
                        resultSet.getString("name"),
                        resultSet.getDate("dateOfBirth").toLocalDate(),
                        Gender.valueOf(resultSet.getString("gender").toUpperCase()),
                        resultSet.getBigDecimal("salary"),
                        resultSet.getString("phone"),
                        resultSet.getInt("departmentId"));
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        try {
            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement("SELECT * FROM Employee WHERE id = ?");
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Employee employee = Employee.builder()
                        .id(UUID.fromString(resultSet.getString("id")))
                        .name(resultSet.getString("name"))
                        .dateOfBirth(resultSet.getDate("dateOfBirth").toLocalDate())
                        .gender(Gender.valueOf(resultSet.getString("gender").toUpperCase()))
                        .salary(resultSet.getBigDecimal("salary"))
                        .phone(resultSet.getString("phone"))
                        .departmentId(resultSet.getInt("departmentId"))
                        .build();
                return Optional.of(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Employee save(Employee employee) {
        try {
            boolean exists = employee.getId() != null && findById(employee.getId()).isPresent();

            if (!exists) {
                UUID newId = UUID.randomUUID();
                String sql = "INSERT INTO Employee (id,name, dateOfBirth, gender, salary, phone, departmentId) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, newId.toString()); // Gán giá trị UUID trước
                    preparedStatement.setString(2, employee.getName());
                    preparedStatement.setDate(3, Date.valueOf(employee.getDateOfBirth()));
                    preparedStatement.setString(4, employee.getGender().toString());
                    preparedStatement.setBigDecimal(5, employee.getSalary());
                    preparedStatement.setString(6, employee.getPhone());
                    preparedStatement.setInt(7, employee.getDepartmentId());

                    preparedStatement.executeUpdate();
                    employee.setId(newId);

                    try (ResultSet res = preparedStatement.getGeneratedKeys()) {
                        if (res.next()) {
                            employee.setId(UUID.fromString(res.getString(1)));
                        }
                    }
                }
            } else {
                String sql = "UPDATE Employee SET name=?,dateOfBirth=?, gender=?, salary=?, phone=?, departmentId=? WHERE id=?";
                try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(sql)) {
                    preparedStatement.setString(1, employee.getName());
                    preparedStatement.setDate(2, Date.valueOf(employee.getDateOfBirth()));
                    preparedStatement.setString(3, employee.getGender().toString());
                    preparedStatement.setBigDecimal(4, employee.getSalary());
                    preparedStatement.setString(5, employee.getPhone());
                    preparedStatement.setInt(6, employee.getDepartmentId());
                    preparedStatement.setString(7, employee.getId().toString());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employee;
    }

    @Override
    public void delete(UUID id) {
        try {
            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement("delete from Employee where id = ?");
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
