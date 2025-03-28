package com.techzen.management.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    EMPLOYEE_NOT_EXIST(40401, "Employee is not exist", HttpStatus.NOT_FOUND),
    DEPARTMENT_NOT_EXIST(40402, "Department is not exist", HttpStatus.NOT_FOUND),
    ID_DEPARTMENT_NOT_EXIST(40403, "ID Department is not exist", HttpStatus.NOT_FOUND),
    UNAUTHENTICATION(40101,"Username or Password is invalid",HttpStatus.UNAUTHORIZED);
    int code;
    String message;
    HttpStatus status;
}
