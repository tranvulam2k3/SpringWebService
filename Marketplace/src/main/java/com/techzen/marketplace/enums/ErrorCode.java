package com.techzen.marketplace.enums;

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
    PREMISES_NOT_EXIST(40401, "Premises is not exist", HttpStatus.NOT_FOUND),
    PREMISES_ALREADY_EXIST(50001, "Premises code already exists", HttpStatus.NOT_FOUND)
    ;
    int code;
    String message;
    HttpStatus status;
}
