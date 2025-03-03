package com.techzen.management.model;

import com.techzen.management.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class Employee {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    @Column(name = "name")
    String name;

    @Column(name = "date_of_birth")
    LocalDate date_of_birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "VARCHAR(20)")
    Gender gender;

    @Column(name = "salary")
    BigDecimal salary;

    @Column(name = "phone")
    String phone;

    @ManyToOne
    @JoinColumn(name = "department_id")
    Department department_id;

}
