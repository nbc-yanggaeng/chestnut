package org.spring.chestnut.board.service;

import java.util.List;
import org.spring.chestnut.board.dto.request.BoardRequestDto;
import org.spring.chestnut.board.dto.response.BoardListDto;
import org.spring.chestnut.board.dto.response.BoardResponse;
import org.spring.chestnut.global.security.UserDetailsImpl;

public interface BoardService {

    List<BoardListDto> getBoardList(Long memberId);

    BoardResponse getBoard(Long boardId, UserDetailsImpl userDetails);

    void createBoard(BoardRequestDto requestDto, UserDetailsImpl userDetails);

    void updateBoard(Long boardId, BoardRequestDto requestDto, UserDetailsImpl userDetails);

    void deleteBoard(Long boardId, UserDetailsImpl userDetails);

    void inviteMember(Long boardId, Long memberId, UserDetailsImpl userDetails);
}
