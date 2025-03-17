package com.techzen.management.dto.department;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techzen.management.dto.employee.EmployeeResponse;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class DepartmentResponse {

    Integer department_id;
    String department_name;
    EmployeeResponse employees;

}
