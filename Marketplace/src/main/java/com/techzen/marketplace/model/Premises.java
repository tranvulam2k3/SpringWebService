package com.techzen.marketplace.model;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Premises {
    String id;
    String name;
    String address;
    int acreage;
    BigDecimal price;
    LocalDate rentalStartDate;
    int PremisesTypeId;

}
