package com.techzen.marketplace.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PremisesType {
    Integer premisesTypeId;
    String premisesTypeName;
}
