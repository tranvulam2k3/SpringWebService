package com.techzen.marketplace.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PremisesSearchRequest {
    String id;
    String name;
    String address;
    Integer acreageFrom;
    Integer acreageTo;
    String rent;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dorFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dorTo;

    Integer premisesTypeId;

}
