package com.techzen.management.dto.user;

import com.techzen.management.dto.role.RoleRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String username;
    String password;
    Set<RoleRequest> roles = new HashSet<>();

}
