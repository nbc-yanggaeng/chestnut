package org.spring.chestnut.column.controller;


import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.spring.chestnut.column.dto.ColumnListResponseDto;
import org.spring.chestnut.column.dto.ColumnRequestDto;
import org.spring.chestnut.column.dto.ColumnResponseDto;
import org.spring.chestnut.column.dto.ColumnSequenceRequestDto;
import org.spring.chestnut.column.entity.ColumnEntity;
import org.spring.chestnut.column.service.ColumnServiceImpl;
import org.spring.chestnut.global.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ColumnController {

    private final ColumnServiceImpl columnServiceImpl;

    @Autowired
    public ColumnController(ColumnServiceImpl columnServiceImpl) {
        this.columnServiceImpl = columnServiceImpl;
    }

    @GetMapping("/boards/{boardId}/columns")
    public ResponseEntity<ResponseDto<ColumnListResponseDto>> getColumn(
        @PathVariable("boardId") Long boardId
    ) {
        ColumnListResponseDto responseDto = columnServiceImpl.getColumn(boardId);
        return ResponseDto.ok("컬럼 조회에 성공했습니다.", responseDto);
    }

    @PostMapping("/boards/{boardId}/columns")
    public ResponseEntity<ResponseDto<ColumnResponseDto>> createColumn(
        @PathVariable("boardId") Long boardId,
        @RequestBody ColumnRequestDto requestDto
    ) {
        ColumnEntity createdColumn = columnServiceImpl.createColumn(boardId, requestDto);

        ColumnResponseDto columnResponseDto = new ColumnResponseDto(createdColumn.getId(),
            createdColumn.getTitle(), createdColumn.getSequence());

        return ResponseDto.ok("컬럼을 생성했습니다.", columnResponseDto);
    }

    @PutMapping("/columns/{columnId}")
    public ResponseEntity<ResponseDto<List<ColumnResponseDto>>> updateColumn(
        @PathVariable("columnId") Long columnId,
        @RequestBody ColumnRequestDto requestDto
    ) {
        ColumnEntity updatedColumn = columnServiceImpl.updateColumn(columnId, requestDto);
        ColumnResponseDto columnResponseDto = new ColumnResponseDto(updatedColumn.getId(),
            updatedColumn.getTitle(), updatedColumn.getSequence());

        List<ColumnResponseDto> columnList = List.of(columnResponseDto);
        return ResponseDto.ok("컬럼을 수정했습니다.", columnList);
    }

    @DeleteMapping("/columns/{columnId}")
    public ResponseEntity<Void> deleteColumn(
        @PathVariable Long columnId
    ) {
        columnServiceImpl.deleteColumn(columnId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/columns/{columnId}/sequences")
    public ResponseEntity<ResponseDto<ColumnResponseDto>> updateSecuence(
        @PathVariable("columnId") Long columnId,
        @RequestBody ColumnSequenceRequestDto requestDto
    ) {
        ColumnEntity updatedColumn = columnServiceImpl.updateSecuence(columnId, requestDto);
        ColumnResponseDto columnResponseDto = new ColumnResponseDto(updatedColumn.getId(),
            updatedColumn.getTitle(), updatedColumn.getSequence());

        return ResponseDto.ok("컬럼 순서를 수정했습니다.", columnResponseDto);
    }
}
