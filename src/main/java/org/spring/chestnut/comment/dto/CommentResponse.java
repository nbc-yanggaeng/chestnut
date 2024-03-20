package org.spring.chestnut.comment.dto;

import lombok.Getter;
import org.spring.chestnut.comment.entity.CommentEntity;

@Getter
public class CommentResponse {

    private Long memberId;
    private String content;

    public CommentResponse(CommentEntity comment) {
        this.memberId = comment.getMemberId();
        this.content = comment.getContent();
    }
}
