package com.techzen.management.dto;

import com.techzen.management.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class EmployeeSearchRequest {
    String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dobFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dobTo;

    Gender gender;

    String salaryRange;

    String phone;

    Integer departmentId;
}
