package org.spring.chestnut.column.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ColumnListResponseDto {

    private Long boardId;
    private List<ColumnResponseDto> responseDtoList;

    public ColumnListResponseDto(Long boardId, List<ColumnResponseDto> responseDtoList) {
        this.boardId = boardId;
        this.responseDtoList = responseDtoList;
    }
}
