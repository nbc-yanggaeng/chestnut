package org.spring.chestnut.board.repository;

import java.util.List;
import org.spring.chestnut.board.dto.request.BoardDto;
import org.spring.chestnut.board.dto.response.BoardListDto;
import org.spring.chestnut.board.dto.response.BoardResponse;
import org.spring.chestnut.board.entity.BoardEntity;

public interface BoardRepository {

    List<BoardListDto> findBoardsByMemberId(Long memberId);

    BoardResponse findAllByBoardId(Long boardId);

    BoardEntity save(BoardDto boardDto);

    void update(BoardDto boardDto, Long boardId);

    BoardEntity findById(Long boardId);

    void deleteById(Long boardId);
}
