package org.spring.chestnut.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String backgroundColor;

    private String description;
}
