package org.spring.chestnut.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.spring.chestnut.global.entity.Timestamped;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "card")
@SQLDelete(sql = "update card set deleted_at = NOW() where id = ?")
@SQLRestriction(value = "deleted_at is NULL")
public class CardEntity extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long boardId;

    @Column(nullable = false)
    private Long columnId;

    @ElementCollection
    @Column(name = "member_id")
    private List<Long> memberIds = new ArrayList<>();

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
}
