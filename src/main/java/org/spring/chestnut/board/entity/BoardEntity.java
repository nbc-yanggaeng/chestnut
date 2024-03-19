package org.spring.chestnut.board.entity;

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
import org.spring.chestnut.global.entity.Timestamped;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "update board set deleted_at = NOW() where id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@Table(name = "board")
public class BoardEntity extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String backgroundColor;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long createMemberId;

    public static BoardEntity of(String title, String backgroundColor, String description, Long memberId) {
        return BoardEntity.builder()
            .title(title)
            .backgroundColor(backgroundColor)
            .description(description)
            .createMemberId(memberId)
            .build();
    }
}
