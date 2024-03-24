package org.spring.chestnut.board.repository;

import static org.spring.chestnut.board.entity.QBoardEntity.boardEntity;
import static org.spring.chestnut.card.entity.QCardEntity.cardEntity;
import static org.spring.chestnut.card.entity.QWorkerEntity.workerEntity;
import static org.spring.chestnut.column.entity.QColumnEntity.columnEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.board.dto.request.BoardDto;
import org.spring.chestnut.board.dto.response.BoardResponse;
import org.spring.chestnut.board.entity.BoardEntity;
import org.spring.chestnut.card.dto.CardResponse;
import org.spring.chestnut.card.entity.CardEntity;
import org.spring.chestnut.card.entity.WorkerEntity;
import org.spring.chestnut.column.dto.ColumnResponse;
import org.spring.chestnut.column.entity.ColumnEntity;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;
    private final JPAQueryFactory factory;

    @Override
    public BoardResponse findAllByBoardId(Long boardId) {
        // 보드 정보 조회
        BoardEntity board = factory.selectFrom(boardEntity)
            .where(boardEntity.id.eq(boardId))
            .fetchOne();

        // 보드가 존재하지 않는경우 exception
        if(Objects.isNull(board)) {
            throw new IllegalArgumentException("존재하지 않는 보드입니다");
        }

        // 컬럼 조회
        List<ColumnEntity> columns = factory.selectFrom(columnEntity)
            .where(columnEntity.boardId.eq(boardId))
            .fetch();

        // 카드 조회 (컬럼별로 그룹화)
        Map<Long, List<CardEntity>> cardsByColumn = factory.selectFrom(cardEntity)
            .where(cardEntity.columnId.in(
                columns.stream().map(ColumnEntity::getId).collect(Collectors.toList())))
            .fetch()
            .stream()
            .collect(Collectors.groupingBy(CardEntity::getColumnId));

        // 작업자 조회 (카드별로 그룹화)
        Map<Long, List<WorkerEntity>> workersByCard = factory.selectFrom(workerEntity)
            .where(workerEntity.cardId.in(
                cardsByColumn.values().stream().flatMap(List::stream).map(CardEntity::getId)
                    .collect(Collectors.toList())))
            .fetch()
            .stream()
            .collect(Collectors.groupingBy(WorkerEntity::getCardId));

        // 최종 결과 구조화
        List<ColumnResponse> columnResponses = columns.stream().map(column -> {
            List<CardResponse> cardResponses = cardsByColumn.getOrDefault(column.getId(),
                Collections.emptyList()).stream().map(card -> {
                List<Long> workerIds = workersByCard.getOrDefault(card.getId(),
                        Collections.emptyList()).stream().map(WorkerEntity::getMemberId)
                    .collect(Collectors.toList());
                return new CardResponse(card, workerIds); // CardResponse 생성자에 필요한 데이터 전달
            }).collect(Collectors.toList());
            return new ColumnResponse(column, cardResponses); // ColumnResponse 생성자에 필요한 데이터 전달
        }).collect(Collectors.toList());

        return new BoardResponse(board, columnResponses); // 최종 BoardResponse 객체 생성
    }

    @Override
    public BoardEntity save(BoardDto boardDto) {
        return boardJpaRepository.save(
            BoardEntity.of(boardDto.getTitle(), boardDto.getBackgroundColor(),
                boardDto.getDescription(), boardDto.getMemberId()));
    }

    @Override
    public void update(BoardDto boardDto, Long boardId) {
        BoardEntity board = findById(boardId);
        String description = boardDto.getDescription(); // null일 수 있음

        // description 이 null 이면 description 을 유지
        board.update(boardDto.getTitle(), boardDto.getBackgroundColor(),
            description != null ? description : board.getDescription());
    }

    @Override
    public void deleteById(Long boardId) {
        boardJpaRepository.deleteById(boardId);
    }

    @Override
    public BoardEntity findById(Long boardId) {
        return boardJpaRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("보드 데이터가 없습니다"));
    }
}
