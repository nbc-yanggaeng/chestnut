package org.spring.chestnut.column.reposiotry;

import java.util.List;
import java.util.Optional;
import org.spring.chestnut.board.entity.BoardEntity;
import org.spring.chestnut.column.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ColumnRepository extends JpaRepository<ColumnEntity, Long> {

    List<ColumnEntity> findAllByBoard(BoardEntity board);
    Optional<Integer> findLastSequenceByBoardId(Long boardId);
}
