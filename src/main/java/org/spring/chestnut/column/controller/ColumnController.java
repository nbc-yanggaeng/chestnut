package org.spring.chestnut.column.controller;


import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.spring.chestnut.column.dto.ColumnRequestDto;
import org.spring.chestnut.column.dto.ColumnResponseDto;
import org.spring.chestnut.column.entity.ColumnEntity;
import org.spring.chestnut.column.service.ColumnService;
import org.spring.chestnut.global.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ColumnController {

    private final ColumnService columnService;

    @Autowired
    public ColumnController(ColumnService columnService) {
        this.columnService = columnService;
    }

    @PostMapping("boards/{boardId}/columns")
    public ResponseEntity<ResponseDto<List<ColumnResponseDto>>> createColumn(
        @PathVariable("boardId") Long boardId,
        ColumnRequestDto requestDto
    ) {
        ColumnEntity createdColumn = columnService.createColumn(boardId, requestDto);

        ColumnResponseDto columnResponseDto = new ColumnResponseDto(createdColumn.getId(),
            createdColumn.getTitle(), createdColumn.getSequence());

        List<ColumnResponseDto> columnList = Arrays.asList(columnResponseDto);

        return ResponseEntity.ok()
            .body(ResponseDto.<List<ColumnResponseDto>>builder()
                .message("컬럼을 생성했습니다.")
                .data(columnList)
                .build());
    }

    @PutMapping("columns/{columnId}")
    public ResponseEntity<ResponseDto<List<ColumnResponseDto>>> updateColumn(
        @PathVariable("columnId") Long columnId,
        ColumnRequestDto requestDto
    ) {
        ColumnEntity updatedColumn = columnService.updateColumn(columnId, requestDto);

        ColumnResponseDto columnResponseDto = new ColumnResponseDto(updatedColumn.getId(),
            updatedColumn.getTitle(), updatedColumn.getSequence());

        List<ColumnResponseDto> columnList = Arrays.asList(columnResponseDto);

        return ResponseEntity.ok().body(
            ResponseDto.<List<ColumnResponseDto>>builder()
                .message("컬럼을 수정했습니다.")
                .data(columnList)
                .build()
        );
    }

    @DeleteMapping("columns/{columnId}")
    public ResponseEntity<Void> deleteColumn(@PathVariable Long columnId) {
        columnService.deleteColumn(columnId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("columns/{columnId}/{sequence}")
    public ResponseEntity<ResponseDto<ColumnResponseDto>> updateSecuence(
        @PathVariable("columnId") Long columnId,
        @PathVariable("sequence") Integer sequence
    ) {

        ColumnEntity updatedColumn = columnService.updateSecuence(columnId, sequence);

        ColumnResponseDto columnResponseDto = new ColumnResponseDto(updatedColumn.getId(),
            updatedColumn.getTitle(), updatedColumn.getSequence());

        return ResponseEntity.ok().body(
            ResponseDto.<ColumnResponseDto>builder()
                .message("컬럼 순서를 수정했습니다.")
                .data(columnResponseDto)
                .build()
        );
    }
}