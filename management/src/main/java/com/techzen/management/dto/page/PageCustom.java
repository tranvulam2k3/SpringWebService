package com.techzen.management.dto.page;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageCustom <T>{
    int page;
    int size;
    int totalElement;
    int totalPages;

    public PageCustom(Page<T> page) {
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElement = page.getTotalPages();
    }
}
