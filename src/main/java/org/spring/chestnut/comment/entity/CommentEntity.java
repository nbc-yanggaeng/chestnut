package org.spring.chestnut.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.spring.chestnut.comment.dto.CommentCreateRequest;
import org.spring.chestnut.comment.dto.CommentRequest;
import org.spring.chestnut.global.entity.Timestamped;


@Getter
@Entity
@Table(name = "comment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "update comment set deleted_at = NOW() where id = ?")
@SQLRestriction(value = "deleted_at is NULL")
public class CommentEntity extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(nullable = false)
    private Long cardId;

    @Column(nullable = false)
    private Long memberId;

    public static CommentEntity of(Long cardId, CommentRequest request, Long memberId) {
        return CommentEntity.builder()
            .content(request.getContent())
            .cardId(cardId)
            .memberId(memberId)
            .build();
    }

    public static CommentEntity of(Long cardId, CommentCreateRequest request, Long memberId) {
        return CommentEntity.builder()
            .content(request.getContent())
            .cardId(cardId)
            .memberId(memberId)
            .build();
    }

    public void updateComment(CommentRequest request) {
        this.content = request.getContent();
    }
}
