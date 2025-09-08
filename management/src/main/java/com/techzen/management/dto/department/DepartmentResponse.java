package com.techzen.management.dto.department;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class DepartmentResponse {

    Integer department_id;
    String department_name;
//    EmployeeResponse employees;

}
