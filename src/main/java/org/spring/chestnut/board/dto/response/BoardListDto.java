package org.spring.chestnut.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardListDto {

    private Long id;
    private String title;
    private String description;
    private String backgroundColor;
}
