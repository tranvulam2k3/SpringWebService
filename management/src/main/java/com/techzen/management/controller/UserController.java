package com.techzen.management.controller;

import com.techzen.management.dto.ApiResponse;
import com.techzen.management.dto.user.UserRequest;
import com.techzen.management.mapper.IUserMapper;
import com.techzen.management.model.User;
import com.techzen.management.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("${api.prefix}/users")
public class UserController {
    IUserService userService;
    IUserMapper userMapper;

    @PostMapping
    public ResponseEntity<?> create( @RequestBody UserRequest userRequest) {

        User user = userMapper.userRequestToUser(userRequest);
        user = userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder()
                        .data(userMapper.userToUserResponse(user))
                .build());
    }
}
