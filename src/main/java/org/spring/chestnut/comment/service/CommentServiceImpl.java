package org.spring.chestnut.comment.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.board.repository.CollaboratorRepository;
import org.spring.chestnut.card.repository.CardRepository;
import org.spring.chestnut.comment.dto.CommentRequest;
import org.spring.chestnut.comment.dto.CommentResponse;
import org.spring.chestnut.comment.entity.CommentEntity;
import org.spring.chestnut.comment.repository.CommentRepository;
import org.spring.chestnut.global.execption.custom.NotFoundException;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final CardRepository cardRepository;

    @Override
    public CommentResponse createComment(Long cardId, CommentRequest request,
        UserDetailsImpl userDetails) {

        cardRepository.findById(cardId).orElseThrow(() -> new NotFoundException("없는 카드입니다."));

        isCollaborator(cardId, userDetails.getMemberId());

        CommentEntity comment = CommentEntity.of(cardId, request, userDetails.getMemberId());

        return new CommentResponse(commentRepository.save(comment));
    }

    @Override
    public CommentResponse updateComment(Long commentId, CommentRequest request,
        UserDetailsImpl userDetails) {

        CommentEntity comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new NotFoundException("없는 댓글 입니다"));

        isCollaborator(comment.getCardId(), userDetails.getMemberId());

        comment.updateComment(request);
        return new CommentResponse(commentRepository.saveAndFlush(comment));
    }

    @Override
    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {
        CommentEntity comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new NotFoundException("없는 댓글 입니다"));

        isCollaborator(comment.getCardId(), userDetails.getMemberId());

        commentRepository.delete(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long cardId, UserDetailsImpl userDetails) {

        cardRepository.findById(cardId).orElseThrow(() -> new NotFoundException("없는 카드입니다."));

        isCollaborator(cardId, userDetails.getMemberId());

        List<CommentEntity> comments = commentRepository.findByCardId(cardId);

        return comments.stream()
            .map(CommentResponse::new)
            .toList();
    }

    private void isCollaborator(Long cardId, Long memberId) {
        Long boardId = commentRepository.findBoardIdByCardId(cardId);

        if (!collaboratorRepository.existsByMemberIdAndBoardId(memberId, boardId)) {
            throw new IllegalArgumentException("협력자만 가능합니다");
        }
    }
}
