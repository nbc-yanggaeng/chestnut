package org.spring.chestnut.comment.dto;

import lombok.Getter;
import org.spring.chestnut.comment.entity.CommentEntity;

@Getter
public class CommentResponse {

    private final String content;

    public CommentResponse(CommentEntity comment) {
        this.content = comment.getContent();
    }
}
