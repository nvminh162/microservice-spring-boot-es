package com.nvminh162.bookservice.query.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseModel {
    String id;
    String name;
    String author;
    Boolean isReady;
}
