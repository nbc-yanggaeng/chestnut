package org.spring.chestnut.column.reposiotry;

import java.util.Optional;
import org.spring.chestnut.column.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<ColumnEntity, Long> {
    Optional<Integer> findLastSequenceByBoardId(Long boardId);


}
