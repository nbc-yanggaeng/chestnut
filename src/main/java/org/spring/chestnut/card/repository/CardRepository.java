package org.spring.chestnut.card.repository;

import org.spring.chestnut.card.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardRepository extends JpaRepository<CardEntity, Long>, CardRepositoryQuery {

    @Query("select co.boardId from ColumnEntity co left join CardEntity ca on ca.columnId = co.id where ca.id = :cardId")
    Long findBoardIdByCardId(Long cardId);

    @Query("select c.boardId from ColumnEntity c where c.id = :columnId")
    Long findBoardIdByColumnId(Long columnId);

}
