package org.spring.chestnut.comment.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.comment.dto.CommentCreateRequest;
import org.spring.chestnut.comment.dto.CommentRequest;
import org.spring.chestnut.comment.dto.CommentResponse;
import org.spring.chestnut.comment.service.CommentService;
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
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<ResponseDto<CommentResponse>> createComment(
        @RequestBody CommentCreateRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        CommentResponse response = commentService.createComment(request, userDetails);

        return ResponseDto.ok("댓글 작성 성공", response);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDto<CommentResponse>> updateComment(
        @PathVariable Long commentId,
        @RequestBody CommentRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        CommentResponse response = commentService.updateComment(commentId, request,
            userDetails);

        return ResponseDto.ok("댓글 수정 성공", response);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDto<CommentResponse>> deleteComment(
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        commentService.deleteComment(commentId, userDetails);

        return ResponseDto.ok("댓글 삭제 성공", null);
    }

    @GetMapping("/cards/{cardId}/comments")
    public ResponseEntity<ResponseDto<List<CommentResponse>>> getComment(
        @PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        List<CommentResponse> responses = commentService.getComments(cardId, userDetails);

        return ResponseDto.ok("댓글 조회 성공", responses);
    }

}
