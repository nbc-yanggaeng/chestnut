package org.spring.chestnut.column.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.board.entity.BoardEntity;
import org.spring.chestnut.board.repository.BoardRepository;
import org.spring.chestnut.column.dto.ColumnListResponseDto;
import org.spring.chestnut.column.dto.ColumnRequestDto;
import org.spring.chestnut.column.dto.ColumnResponseDto;
import org.spring.chestnut.column.entity.ColumnEntity;
import org.spring.chestnut.column.reposiotry.ColumnRepository;
import org.spring.chestnut.global.execption.ColumnNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColumnServiceImpl implements ColumnService{

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public ColumnEntity createColumn(Long boardId, ColumnRequestDto requestDto) {
        // boardId로 BoardEntity 존재 여부 확인
        BoardEntity board = boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("해당 보드가 존재하지 않습니다."));

        // 해당 보드의 마지막 컬럼 순서를 찾기
        Integer lastSequence = columnRepository.findLastSequenceByBoardId(board.getId())
            .orElse(0); // 컬럼이 없을 경우 기본값으로 0을 반환

        // 새 컬럼 객체 생성
        ColumnEntity newColumn = new ColumnEntity(
            requestDto.getTitle(),
            lastSequence + 1, // 찾은 마지막 순서에 + 1
            boardId
        );

        // 컬럼 저장 및 반환
        return columnRepository.save(newColumn);
    }

    @Override
    @Transactional
    public ColumnEntity updateColumn(Long columnId, ColumnRequestDto requestDto) {
        ColumnEntity column = columnRepository.findById(columnId)
            .orElseThrow(() -> new ColumnNotFoundException("Column을 찾을 수 없습니다."));

        column.setTitle(requestDto.getTitle());
        return columnRepository.save(column);
    }

    @Override
    @Transactional
    public void deleteColumn(Long columnId) {
        columnRepository.deleteById(columnId);
    }

    @Override
    @Transactional
    public ColumnEntity updateSecuence(Long columnId, Integer newSequence) {
        ColumnEntity columnToMove = columnRepository.findById(columnId)
            .orElseThrow(() -> new ColumnNotFoundException("Column을 찾을 수 없습니다."));

        Integer oldSequence = columnToMove.getSequence();
        List<ColumnEntity> columnsToShiftLeft = new ArrayList<>();
        List<ColumnEntity> columnsToShiftRight = new ArrayList<>();

        // 새 순서가 이전 순서보다 큰 경우, 사이에 있는 칼럼들의 순서를 1씩 감소
        if (newSequence > oldSequence) {
            columnsToShiftLeft = columnRepository.findBySequenceBetween(
                oldSequence + 1, newSequence);
            for (ColumnEntity column : columnsToShiftLeft) {
                column.setSequence(column.getSequence() - 1);
            }
        }
        // 새 순서가 이전 순서보다 작은 경우, 사이에 있는 칼럼들의 순서를 1씩 증가
        else if (newSequence < oldSequence) {
            columnsToShiftRight = columnRepository.findBySequenceBetween(
                newSequence, oldSequence - 1);
            for (ColumnEntity column : columnsToShiftRight) {
                column.setSequence(column.getSequence() + 1);
            }
        }
        // 칼럼 순서 업데이트
        columnToMove.setSequence(newSequence);
        columnRepository.saveAll(columnsToShiftLeft);
        columnRepository.saveAll(columnsToShiftRight);
        columnRepository.save(columnToMove);

        return columnToMove;
    }

    @Override
    public List<ColumnListResponseDto> getColumn() {
        List<ColumnEntity> columnEntityList = columnRepository.findAll();
        List<ColumnListResponseDto> columnListResponseDtoList = new ArrayList<>();

        for (ColumnEntity columnEntity : columnEntityList) {
            List<ColumnResponseDto> columnResponseDtoList = new ArrayList<>();
            List<ColumnEntity> columnList = columnRepository.findAll();

            for (ColumnEntity column : columnList) {
                ColumnResponseDto columnResponseDto = new ColumnResponseDto(column.getId(), column.getTitle(), column.getSequence());
                columnResponseDtoList.add(columnResponseDto);
            }
            ColumnListResponseDto columnListResponseDto = new ColumnListResponseDto(columnEntity.getBoardId(), columnResponseDtoList);
            columnListResponseDtoList.add(columnListResponseDto);
        }
        return columnListResponseDtoList;
    }
}