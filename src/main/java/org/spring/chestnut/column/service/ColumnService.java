package org.spring.chestnut.column.service;

import lombok.RequiredArgsConstructor;
import org.spring.chestnut.column.dto.ColumnRequestDto;
import org.spring.chestnut.column.entity.ColumnEntity;
import org.spring.chestnut.column.reposiotry.ColumnRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;

    public ColumnEntity createColumn(
        Long boardId,
        ColumnRequestDto requestDto
    ) {
        // 해당 보드의 마지막 컬럼 순서를 찾음
        Integer lastSequence = columnRepository.findLastSequenceByBoardId(boardId)
            .orElse(0); // 컬럼이 없을 경우 기본값으로 0을 반환

        // 새 컬럼 객체 생성
        ColumnEntity newColumn = new ColumnEntity(
            requestDto.getTitle(),
            lastSequence + 1, // 찾은 마지막 순서에 1을 더함
            boardId
        );

        // 컬럼 저장 및 반환
        return columnRepository.save(newColumn);
    }
}