package org.spring.chestnut.column.reposiotry;

import java.util.List;
import org.spring.chestnut.column.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnJpaRepository extends JpaRepository<ColumnEntity, Long> {

    Integer countByBoardId(Long boardId);

    List<ColumnEntity> findAllByBoardId(Long boardId);

    List<ColumnEntity> findBySequenceBetween(int startSequence, Integer endSequence);
}
