package com.techzen.management.exception;

import com.techzen.management.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiException extends RuntimeException {
    ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
