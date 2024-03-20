package org.spring.chestnut.column.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnSequenceRequestDto {

    @NotNull
    Integer sequence;
}
