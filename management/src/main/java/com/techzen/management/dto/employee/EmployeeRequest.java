package com.techzen.management.dto.employee;

import com.techzen.management.enums.Gender;
import com.techzen.management.model.Department;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class EmployeeRequest {

    @NotBlank(message = "Tên không được để trống !!")
    @Size(min = 3, message = "tối thiếu 3 kí tự")
    @Pattern(regexp = "[a-zA-ZÀ-ỹ ]*", message = "Không được phép chứa ký tự đặc biệt")
    String name;
    LocalDate date_of_birth;

    @Enumerated(EnumType.STRING)
    Gender gender;

    BigDecimal salary;
    String phone;
    Department department_id;

}

