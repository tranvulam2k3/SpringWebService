package com.techzen.management.mapper;

import com.techzen.management.dto.user.UserRequest;
import com.techzen.management.dto.user.UserResponse;
import com.techzen.management.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    User userRequestToUser(UserRequest userRequest);
    UserResponse userToUserResponse(User user);
}
