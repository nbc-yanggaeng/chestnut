package org.spring.chestnut.comment.repository;

import java.util.List;
import org.spring.chestnut.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByCardId(Long cardId);

    @Query("select co.boardId from ColumnEntity co left join CardEntity ca on ca.columnId = co.id where ca.id = :cardId")
    Long findBoardIdByCardId(Long cardId);

}
