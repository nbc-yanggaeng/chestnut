package org.spring.chestnut.comment.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.comment.dto.CommentRequest;
import org.spring.chestnut.comment.dto.CommentResponse;
import org.spring.chestnut.comment.entity.CommentEntity;
import org.spring.chestnut.comment.repository.CommentRepository;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public CommentResponse createComment(Long cardId, CommentRequest request,
        UserDetailsImpl userDetails) {

        CommentEntity comment = CommentEntity.of(cardId, request, userDetails.getMemberId());

        return new CommentResponse(commentRepository.save(comment));
    }

    @Override
    public CommentResponse updateComment(Long commentId, CommentRequest request,
        UserDetailsImpl userDetails) {

        CommentEntity comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new NullPointerException("없는 댓글 입니다"));

        comment.updateComment(request);
        return new CommentResponse(commentRepository.saveAndFlush(comment));
    }

    @Override
    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {
        CommentEntity comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new NullPointerException("없는 댓글 입니다"));

        commentRepository.delete(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long cardId, UserDetailsImpl userDetails) {
        List<CommentEntity> comments = commentRepository.findByCardId(cardId);

        return comments.stream()
            .map(CommentResponse::new)
            .toList();
    }
}
