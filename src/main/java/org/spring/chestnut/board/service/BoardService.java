package org.spring.chestnut.board.service;

import org.spring.chestnut.board.dto.BoardRequestDto;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.User;

public interface BoardService {

    public void getBoard(Long boardId, UserDetailsImpl userDetails);

    public void createBoard(BoardRequestDto requestDto, UserDetailsImpl userDetails);

    public void updateBoard(Long boardId, BoardRequestDto requestDto, UserDetailsImpl userDetails);

    public void deleteBoard(Long boardId, UserDetailsImpl userDetails);

    public void inviteMember(Long boardId, Long memberId, UserDetailsImpl userDetails);
}
