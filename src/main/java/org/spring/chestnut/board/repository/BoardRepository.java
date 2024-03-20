package org.spring.chestnut.board.repository;

import org.spring.chestnut.board.dto.request.BoardDto;
import org.spring.chestnut.board.entity.BoardEntity;

public interface BoardRepository {

    BoardEntity save(BoardDto boardDto);

    BoardEntity findBoard(Long boardId);

    void deleteById(Long boardId);
}
