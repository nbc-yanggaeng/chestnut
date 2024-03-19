package org.spring.chestnut.column.service;

import lombok.RequiredArgsConstructor;
import org.spring.chestnut.board.entity.BoardEntity;
import org.spring.chestnut.column.dto.ColumnRequestDto;
import org.spring.chestnut.column.entity.ColumnEntity;
import org.spring.chestnut.column.reposiotry.BoardRepository;
import org.spring.chestnut.column.reposiotry.ColumnRepository;
import org.spring.chestnut.global.execption.ColumnNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;

    public ColumnEntity createColumn(
        Long boardId,
        ColumnRequestDto requestDto
    ) {
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

    public ColumnEntity updateColumn(
        Long columnId,
        ColumnRequestDto requestDto
    ) {
        ColumnEntity column = columnRepository.findById(columnId)
            .orElseThrow(() -> new ColumnNotFoundException("Column을 찾을 수 없습니다."));

        column.setTitle(requestDto.getTitle());
        return columnRepository.save(column);
    }

    public void deleteColumn(Long columnId) {
        columnRepository.deleteById(columnId);
    }
}