package org.spring.chestnut.column.reposiotry;

import java.util.List;
import java.util.Optional;
import org.spring.chestnut.column.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnJpaRepository extends JpaRepository<ColumnEntity, Long> {

    List<ColumnEntity> findAllByBoardId(Long boardId);

    List<ColumnEntity> findBySequenceBetween(int startSequence, Integer endSequence);

    Optional<ColumnEntity> findTopByBoardIdOrderBySequenceDesc(Long id);
}
