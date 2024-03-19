package org.spring.chestnut.board.service;

import org.spring.chestnut.board.dto.request.BoardRequestDto;
import org.spring.chestnut.global.security.UserDetailsImpl;

public interface BoardService {

    void getBoard(Long boardId, UserDetailsImpl userDetails);

    void createBoard(BoardRequestDto requestDto, UserDetailsImpl userDetails);

    void updateBoard(Long boardId, BoardRequestDto requestDto, UserDetailsImpl userDetails);

    void deleteBoard(Long boardId, UserDetailsImpl userDetails);

    void inviteMember(Long boardId, Long memberId, UserDetailsImpl userDetails);
}
