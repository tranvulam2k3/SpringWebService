package com.techzen.management.dto.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    List<T> content;
    PageCustom<T> page;

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.page = new PageCustom<>(page);
    }
}
