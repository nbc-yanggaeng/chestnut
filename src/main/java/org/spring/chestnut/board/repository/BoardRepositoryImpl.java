package org.spring.chestnut.board.repository;

import lombok.RequiredArgsConstructor;
import org.spring.chestnut.board.dto.request.BoardDto;
import org.spring.chestnut.board.entity.BoardEntity;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryImpl implements BoardRepository{

    private final BoardJpaRepository boardJpaRepository;

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
