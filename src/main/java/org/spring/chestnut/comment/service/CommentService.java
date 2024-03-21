package org.spring.chestnut.comment.service;

import java.util.List;
import org.spring.chestnut.comment.dto.CommentCreateRequest;
import org.spring.chestnut.comment.dto.CommentRequest;
import org.spring.chestnut.comment.dto.CommentResponse;
import org.spring.chestnut.global.security.UserDetailsImpl;

public interface CommentService {

    CommentResponse createComment(CommentCreateRequest request, UserDetailsImpl userDetails);

    CommentResponse updateComment(Long commentId, CommentRequest request,
        UserDetailsImpl userDetails);

    void deleteComment(Long commentId, UserDetailsImpl userDetails);

    List<CommentResponse> getComments(Long cardId, UserDetailsImpl userDetails);
}
