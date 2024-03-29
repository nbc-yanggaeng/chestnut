package org.spring.chestnut.column.reposiotry;


import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.column.entity.ColumnEntity;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ColumnRepositoryImpl implements ColumnRepository {

    private final ColumnJpaRepository columnJpaRepository;

    @Override
    public List<ColumnEntity> findBySequenceBetween(int startSequence, Integer endSequence) {
        return columnJpaRepository.findBySequenceBetween(startSequence, endSequence);
    }

    @Override
    public ColumnEntity save(ColumnEntity newColumn) {
        return columnJpaRepository.save(newColumn);
    }

    @Override
    public Optional<ColumnEntity> findById(Long columnId) {
        return columnJpaRepository.findById(columnId);
    }

    @Override
    public void saveAll(List<ColumnEntity> columnsToShiftLeft) {
        columnJpaRepository.saveAll(columnsToShiftLeft);
    }

    @Override
    public Optional<ColumnEntity> findTopByBoardIdOrderBySequenceDesc(Long id) {
        return columnJpaRepository.findTopByBoardIdOrderBySequenceDesc(id);
    }

    @Override
    public List<ColumnEntity> findAllByBoardIdOrderBySequence(Long boardId) {
        return columnJpaRepository.findAllByBoardIdOrderBySequence(boardId);
    }

    @Override
    public void deleteById(Long columnId) {
        columnJpaRepository.deleteById(columnId);
    }
}
