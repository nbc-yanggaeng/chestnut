package org.spring.chestnut.column.controller;


import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.spring.chestnut.column.dto.ColumnListResponseDto;
import org.spring.chestnut.column.dto.ColumnRequestDto;
import org.spring.chestnut.column.dto.ColumnResponseDto;
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
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ColumnController {

    private final ColumnServiceImpl columnServiceImpl;

    @Autowired
    public ColumnController(ColumnServiceImpl columnServiceImpl) {
        this.columnServiceImpl = columnServiceImpl;
    }

    @GetMapping("columns")
    public ResponseEntity<ResponseDto<List<ColumnListResponseDto>>> getColumn() {

        List<ColumnListResponseDto> responseDto = columnServiceImpl.getColumn();

        return ResponseEntity.ok().body(
            ResponseDto.<List<ColumnListResponseDto>>builder()
                .message("컬럼을 생성했습니다.")
                .data(responseDto)
                .build());
    }

    @PostMapping("boards/{boardId}/columns")
    public ResponseEntity<ResponseDto<List<ColumnResponseDto>>> createColumn(
        @PathVariable("boardId") Long boardId,
        ColumnRequestDto requestDto
    ) {
        ColumnEntity createdColumn = columnServiceImpl.createColumn(boardId, requestDto);

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
        ColumnEntity updatedColumn = columnServiceImpl.updateColumn(columnId, requestDto);

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
        columnServiceImpl.deleteColumn(columnId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("columns/{columnId}/{sequence}")
    public ResponseEntity<ResponseDto<ColumnResponseDto>> updateSecuence(
        @PathVariable("columnId") Long columnId,
        @PathVariable("sequence") Integer sequence
    ) {

        ColumnEntity updatedColumn = columnServiceImpl.updateSecuence(columnId, sequence);

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