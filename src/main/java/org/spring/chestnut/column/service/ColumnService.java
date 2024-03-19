package org.spring.chestnut.column.service;

import org.spring.chestnut.column.dto.ColumnListResponseDto;
import org.spring.chestnut.column.dto.ColumnRequestDto;
import org.spring.chestnut.column.entity.ColumnEntity;

public interface ColumnService {

    ColumnEntity createColumn(Long boardId, ColumnRequestDto requestDto);
    ColumnEntity updateColumn(Long columnId, ColumnRequestDto requestDto);
    ColumnEntity updateSecuence(Long columnId, Integer newSequence);
    void deleteColumn(Long columnId);
    ColumnListResponseDto getColumn(Long boardId);
}
