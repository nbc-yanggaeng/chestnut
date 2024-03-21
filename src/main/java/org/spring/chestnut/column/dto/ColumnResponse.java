package org.spring.chestnut.column.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spring.chestnut.card.dto.CardResponse;
import org.spring.chestnut.column.entity.ColumnEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ColumnResponse {

    private Long columnId;
    private Long boardId;
    private String title;
    private Integer sequence;
    private List<CardResponse> cardResponses;

    public ColumnResponse(ColumnEntity column, List<CardResponse> cardResponses) {
        this.columnId = column.getId();
        this.boardId = column.getBoardId();
        this.title = column.getTitle();
        this.sequence = column.getSequence();
        this.cardResponses = cardResponses;
    }
}
