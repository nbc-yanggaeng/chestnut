package org.spring.chestnut.column.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnRequestDto {

    @NotBlank
    @Size(max = 50)
    String title;
}
