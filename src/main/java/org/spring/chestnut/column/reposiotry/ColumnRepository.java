package org.spring.chestnut.column.reposiotry;

import java.util.List;
import java.util.Optional;
import org.spring.chestnut.column.entity.ColumnEntity;

public interface ColumnRepository {

    List<ColumnEntity> findBySequenceBetween(int startSequence, Integer endSequence);

    ColumnEntity save(ColumnEntity newColumn);

    Optional<ColumnEntity> findById(Long columnId);

    void deleteById(Long columnId);

    void saveAll(List<ColumnEntity> columnsToShiftLeft);

    Optional<ColumnEntity> findTopByBoardIdOrderBySequenceDesc(Long id);

    List<ColumnEntity> findAllByBoardIdOrderBySequence(Long boardId);
}
