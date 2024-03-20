package org.spring.chestnut.column.service;

import org.spring.chestnut.column.dto.ColumnListResponseDto;
import org.spring.chestnut.column.dto.ColumnRequestDto;
import org.spring.chestnut.column.dto.ColumnSequenceRequestDto;
import org.spring.chestnut.column.entity.ColumnEntity;
import org.spring.chestnut.global.security.UserDetailsImpl;

public interface ColumnService {

    ColumnEntity createColumn(Long boardId, ColumnRequestDto requestDto,
        UserDetailsImpl userDetails);

    ColumnEntity updateColumn(Long columnId, ColumnRequestDto requestDto,
        UserDetailsImpl userDetails);

    ColumnEntity updateSecuence(Long columnId, ColumnSequenceRequestDto requestDto,
        UserDetailsImpl userDetails);

    void deleteColumn(Long columnId, UserDetailsImpl userDetails);

    ColumnListResponseDto getColumn(Long boardId, UserDetailsImpl userDetails);
}