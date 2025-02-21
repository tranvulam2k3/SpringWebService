package com.techzen.management.exception;

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
    EMPLOYEE_NOT_EXIST(40401,"Employee is not exist",HttpStatus.NOT_FOUND),
    DEPARTMENT_NOT_EXIST(40402,"Department is not exist",HttpStatus.NOT_FOUND)
    ;
    int code;
    String message;
    HttpStatus status;
}
