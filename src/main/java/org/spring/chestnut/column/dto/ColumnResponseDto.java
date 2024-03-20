package org.spring.chestnut.column.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ColumnResponseDto {

    private final Long id;
    private final String title;
    private final Integer sequence;
}