package org.spring.chestnut.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.board.dto.request.BoardRequestDto;
import org.spring.chestnut.board.dto.response.BoardResponse;
import org.spring.chestnut.board.service.BoardServiceImpl;
import org.spring.chestnut.global.dto.ResponseDto;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDto<BoardResponse>> getBoard(
        @PathVariable Long boardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        BoardResponse response =  boardService.getBoard(boardId, userDetails);
        return ResponseDto.ok("보드를 조회했습니다.", response);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createBoard(
        @Valid @RequestBody BoardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boardService.createBoard(requestDto, userDetails);
        return ResponseDto.ok("보드를 생성했습니다.", null);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<ResponseDto<Void>> updateBoard(
        @PathVariable Long boardId,
        @Valid @RequestBody BoardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boardService.updateBoard(boardId, requestDto, userDetails);
        return ResponseDto.ok("보드를 수정했습니다.", null);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseDto<Void>> deleteBoard(
        @PathVariable Long boardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boardService.deleteBoard(boardId, userDetails);
        return ResponseDto.ok("보드를 삭제했습니다.", null);
    }

    @PostMapping("/{boardId}/invite")
    public ResponseEntity<ResponseDto<Void>> inviteMember(
        @PathVariable Long boardId,
        @RequestParam Long memberId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boardService.inviteMember(boardId, memberId, userDetails);
        return ResponseDto.ok("보드 협력자를 추가했습니다", null);
    }
}
