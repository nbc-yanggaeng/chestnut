package org.spring.chestnut.column.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.board.entity.BoardEntity;
import org.spring.chestnut.board.repository.BoardRepository;
import org.spring.chestnut.board.repository.CollaboratorRepository;
import org.spring.chestnut.column.dto.ColumnListResponseDto;
import org.spring.chestnut.column.dto.ColumnRequestDto;
import org.spring.chestnut.column.dto.ColumnResponseDto;
import org.spring.chestnut.column.dto.ColumnSequenceRequestDto;
import org.spring.chestnut.column.entity.ColumnEntity;
import org.spring.chestnut.column.reposiotry.ColumnRepository;
import org.spring.chestnut.global.execption.ColumnNotFoundException;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColumnServiceImpl implements ColumnService {

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final CollaboratorRepository collaboratorRepository;

    @Override
    @Transactional
    public ColumnEntity createColumn(Long boardId, ColumnRequestDto requestDto,
        UserDetailsImpl userDetails) {
        // boardId로 BoardEntity 존재 여부 확인
        BoardEntity board = boardRepository.findById(boardId);

        // 협력자인지 확인
        validateCollaborator(userDetails.getMemberId(), boardId);

        // 해당 보드의 마지막 컬럼 순서를 찾기
        ColumnEntity lastColumn = columnRepository.findTopByBoardIdOrderBySequenceDesc(
                board.getId())
            .orElse(null); // 마지막 컬럼이 없는 경우를 대비해 null 처리

        int lastSequence =
            (lastColumn != null) ? lastColumn.getSequence() : 0; // 마지막 컬럼이 없다면 0으로 시작

        // 새 컬럼 객체 생성
        ColumnEntity newColumn = new ColumnEntity(requestDto.getTitle(), lastSequence + 1,
            boardId); // 찾은 마지막 순서에 +1

        // 컬럼 저장 및 반환
        return columnRepository.save(newColumn);
    }

    @Override
    @Transactional
    public ColumnEntity updateColumn(Long columnId, ColumnRequestDto requestDto,
        UserDetailsImpl userDetails) {
        ColumnEntity column = validateColumn(columnId);

        // 협력자인지 확인
        validateCollaborator(userDetails.getMemberId(), column.getBoardId());

        column.setTitle(requestDto.getTitle());
        return columnRepository.save(column);
    }

    @Override
    @Transactional
    public void deleteColumn(Long columnId, UserDetailsImpl userDetails) {
        // 협력자인지 확인
        ColumnEntity columnEntity = validateColumn(columnId);
        validateCollaborator(userDetails.getMemberId(), columnEntity.getBoardId());

        columnRepository.deleteById(columnId);
    }

    @Override
    @Transactional
    public ColumnEntity updateSecuence(Long columnId, ColumnSequenceRequestDto requestDto,
        UserDetailsImpl userDetails) {

        ColumnEntity columnToMove = validateColumn(columnId);

        // 협력자인지 확인
        validateCollaborator(userDetails.getMemberId(), columnToMove.getBoardId());

        Integer newSequence = requestDto.getSequence();

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
    public ColumnListResponseDto getColumn(Long boardId, UserDetailsImpl userDetails) {
        List<ColumnEntity> columnEntityList = columnRepository.findAllByBoardIdOrderBySequence(
            boardId);

        // 협력자인지 확인
        validateCollaborator(userDetails.getMemberId(), boardId);

        List<ColumnResponseDto> columnResponseDtoList = columnEntityList.stream().map(
            x -> new ColumnResponseDto(x.getId(), x.getTitle(), x.getSequence())).toList();
        return new ColumnListResponseDto(boardId, columnResponseDtoList);
    }

    private ColumnEntity validateColumn(Long columnId) {
        return columnRepository.findById(columnId)
            .orElseThrow(() -> new ColumnNotFoundException("Column을 찾을 수 없습니다."));
    }

    private void validateCollaborator(Long memberId, Long boardId) {
        // 협력자인지 확인
        if (!collaboratorRepository.existsByMemberIdAndBoardId(memberId, boardId)) {
            throw new IllegalArgumentException("협력자가 아닌 멤버입니다");
        }
    }
}

