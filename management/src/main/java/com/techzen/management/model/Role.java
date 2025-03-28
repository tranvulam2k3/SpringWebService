package com.techzen.management.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name; // ADMIN, MEMBER,...

    @ManyToMany(mappedBy = "roles") // Bên này sẽ không tạo cột mà chỉ ánh xạ theo bảng trung gian
    Set<User> users = new HashSet<>();
}