package org.spring.chestnut.comment.repository;

import java.util.List;
import org.spring.chestnut.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByCardId(Long cardId);

}
