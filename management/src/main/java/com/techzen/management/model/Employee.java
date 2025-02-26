package com.techzen.management.model;

import com.techzen.management.enums.Gender;
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
public class Employee {
    UUID id;
    String name;
    LocalDate dateOfBirth;
    Gender gender;
    BigDecimal salary;
    String phone;
    Integer departmentId;

}
