package org.spring.chestnut.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardDto {

    private String title;
    private String backgroundColor;
    private String description;
    private Long memberId;
}
