package org.spring.chestnut.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.board.dto.request.BoardRequestDto;
import org.spring.chestnut.board.dto.response.BoardResponse;
import org.spring.chestnut.board.service.BoardServiceImpl;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("boards")
public class BoardController {

    private final BoardServiceImpl boardService;

    @GetMapping("/{boardId}")
    public BoardResponse getBoard(
        @PathVariable Long boardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return boardService.getBoard(boardId, userDetails);
    }

    @PostMapping
    public void createBoard(
        @Valid @RequestBody BoardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boardService.createBoard(requestDto, userDetails);
    }

    @PutMapping("/{boardId}")
    public void updateBoard(
        @PathVariable Long boardId,
        @Valid @RequestBody BoardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boardService.updateBoard(boardId, requestDto, userDetails);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(
        @PathVariable Long boardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boardService.deleteBoard(boardId, userDetails);
    }

    @PostMapping("/{boardId}/invite")
    public void inviteMember(
        @PathVariable Long boardId,
        @RequestParam Long memberId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boardService.inviteMember(boardId, memberId, userDetails);
    }
}
