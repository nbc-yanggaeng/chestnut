package org.spring.chestnut.card.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spring.chestnut.card.entity.CardEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CardResponse {

    private Long cardId;
    private Long columnId;
    private String title;
    private String description;
    private String backgroundColor;
    private LocalDateTime deadline;
    private LocalDateTime startAt;
    private List<Long> workerResponse;

    public CardResponse(CardEntity card) {
        this.cardId = card.getId();
        this.columnId = card.getColumnId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.backgroundColor = card.getBackgroundColor();
        this.deadline = card.getDeadline();
        this.startAt = card.getStartAt();
    }

    public CardResponse(CardEntity card, List<Long> workerResponse) {
        this.cardId = card.getId();
        this.columnId = card.getColumnId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.backgroundColor = card.getBackgroundColor();
        this.deadline = card.getDeadline();
        this.startAt = card.getStartAt();
        this.workerResponse = workerResponse;
    }

}
