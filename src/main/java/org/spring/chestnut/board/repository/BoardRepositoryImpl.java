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
    public BoardEntity findBoard(Long boardId) {
        return null;
    }
}