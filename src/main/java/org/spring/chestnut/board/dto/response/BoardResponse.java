package org.spring.chestnut.board.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spring.chestnut.board.entity.BoardEntity;
import org.spring.chestnut.column.dto.ColumnResponse;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardResponse {

    private Long boardId;
    private String title;
    private String backgroundColor;
    private String description;
    private Long createMemberId;
    private List<ColumnResponse> columnResponses;

    public BoardResponse(BoardEntity board, List<ColumnResponse> columnResponses) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.backgroundColor = board.getBackgroundColor();
        this.description = board.getDescription();
        this.createMemberId = board.getCreateMemberId();
        this.columnResponses = columnResponses;
    }
}
