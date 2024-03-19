package org.spring.chestnut.column.service;

import java.util.List;
import org.spring.chestnut.column.dto.ColumnListResponseDto;
import org.spring.chestnut.column.dto.ColumnRequestDto;
import org.spring.chestnut.column.entity.ColumnEntity;

public interface ColumnService {

    public ColumnEntity createColumn(Long boardId, ColumnRequestDto requestDto);


    public ColumnEntity updateColumn(Long columnId, ColumnRequestDto requestDto);


    public ColumnEntity updateSecuence(Long columnId, Integer newSequence);

    public void deleteColumn(Long columnId);

    public List<ColumnListResponseDto> getColumn();
}
