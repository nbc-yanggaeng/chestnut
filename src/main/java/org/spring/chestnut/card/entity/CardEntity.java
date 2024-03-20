package org.spring.chestnut.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.spring.chestnut.card.dto.CardRequest;
import org.spring.chestnut.global.entity.Timestamped;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
@SQLDelete(sql = "update card set deleted_at = NOW() where id = ?")
@SQLRestriction(value = "deleted_at is NULL")
public class CardEntity extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long columnId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column
    private String description;

    @Column
    private String backgroundColor;

    @Column
    private LocalDateTime deadline;

    @Column
    private LocalDateTime startAt;

    public static CardEntity of(Long columnId, CardRequest request) {
        return CardEntity.builder()
            .columnId(columnId)
            .title(request.getTitle())
            .description(request.getDescription())
            .backgroundColor(request.getBackgroundColor())
            .deadline(request.getDeadline())
            .startAt(request.getStartAt())
            .build();
    }

    public void updateCard(CardRequest request) {
        this.title = Objects.requireNonNullElse(request.getTitle(), this.title);
        if (request.getDescription() != null) {
            this.description = request.getDescription();
        }
        if (request.getBackgroundColor() != null) {
            this.backgroundColor = request.getBackgroundColor();
        }
        if (request.getDeadline() != null) {
            this.deadline = request.getDeadline();
        }
        if (request.getStartAt() != null) {
            this.startAt = request.getStartAt();
        }
    }

    public void moveCard(Long moveTo) {
        this.columnId = moveTo;
    }
}
