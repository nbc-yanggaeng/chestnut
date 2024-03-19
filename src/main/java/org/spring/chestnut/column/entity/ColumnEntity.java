package org.spring.chestnut.column.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.spring.chestnut.global.entity.Timestamped;


@Getter
@Entity
@Table(name = "columns")
@SQLDelete(sql = "update column set deleted_at = NOW() where id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@NoArgsConstructor
public class ColumnEntity extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column
    private Integer sequence;

    @Column(nullable = false)
    private Long boardId;

    @Builder
    public static ColumnEntity of(String title, Integer sequence){
        return ColumnEntity.builder()
            .title(title)
            .sequence(sequence)
            .build();
    }
}
