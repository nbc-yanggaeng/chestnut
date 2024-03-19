package org.spring.chestnut.comment.service;

import lombok.RequiredArgsConstructor;
import org.spring.chestnut.comment.dto.CommentRequest;
import org.spring.chestnut.comment.dto.CommentResponse;
import org.spring.chestnut.comment.entity.CommentEntity;
import org.spring.chestnut.comment.repository.CommentRepository;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public CommentResponse createComment(Long cardId, CommentRequest request,
        UserDetailsImpl userDetails) {

        CommentEntity comment = CommentEntity.of(cardId, request, userDetails.getMemberId());

        return new CommentResponse(commentRepository.save(comment));
    }
}
