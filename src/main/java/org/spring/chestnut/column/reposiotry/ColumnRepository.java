package org.spring.chestnut.column.reposiotry;

import java.util.List;
import org.spring.chestnut.column.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<ColumnEntity, Long> {

    Integer countByBoardId(Long boardId);

    List<ColumnEntity> findAllByBoardId(Long boardId);
    List<ColumnEntity> findBySequenceBetween(int i, Integer newSequence);


}
