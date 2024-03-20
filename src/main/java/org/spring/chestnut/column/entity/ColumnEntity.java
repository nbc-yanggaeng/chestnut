package org.spring.chestnut.column.entity;

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

@Builder
@AllArgsConstructor
@Getter
@Entity
@Table(name = "columns")
@SQLDelete(sql = "update columns set deleted_at = NOW() where id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@NoArgsConstructor
public class ColumnEntity extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private Integer sequence;

    @Column(nullable = false)
    private Long boardId;


    public static ColumnEntity of(String title, Integer sequence) {
        return ColumnEntity.builder()
            .title(title)
            .sequence(sequence)
            .build();
    }

    public ColumnEntity(String title, Integer sequence, Long boardId) {
        this.title = title;
        this.sequence = sequence;
        this.boardId = boardId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSequence(Integer newSequence) {
        this.sequence = newSequence;
    }
}
