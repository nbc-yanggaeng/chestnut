package org.spring.chestnut.card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CardRequest {

    @NotBlank
    @Size(max = 50)
    private String title;

    private String description;

    private String backgroundColor;

    private LocalDateTime deadline;

    private LocalDateTime startAt;

}
