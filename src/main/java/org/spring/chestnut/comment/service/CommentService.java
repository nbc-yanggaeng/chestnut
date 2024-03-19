package org.spring.chestnut.comment.service;

import org.spring.chestnut.comment.dto.CommentRequest;
import org.spring.chestnut.comment.dto.CommentResponse;
import org.spring.chestnut.global.security.UserDetailsImpl;

public interface CommentService {

    CommentResponse createComment(Long cardId, CommentRequest request, UserDetailsImpl userDetails);

    CommentResponse updateComment(Long commentId, CommentRequest request,
        UserDetailsImpl userDetails);

    void deleteComment(Long commentId, UserDetailsImpl userDetails);
}
