package com.techzen.management.dto.employee;

import com.techzen.management.dto.department.DepartmentResponse;
import com.techzen.management.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class EmployeeResponse {

    UUID id;
    String name;
    LocalDate date_of_birth;

    @Enumerated(EnumType.STRING)
    Gender gender;
    BigDecimal salary;
    String phone;

//    DepartmentRequest department;
    DepartmentResponse department;

}
