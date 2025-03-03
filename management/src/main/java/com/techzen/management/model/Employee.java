package com.techzen.management.model;

import com.techzen.management.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
    UUID id;
    String name;
    LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "NVARCHAR(10)")
    Gender gender;

    BigDecimal salary;
    String phone;

    @ManyToOne
    Department departmentId;

}
