package org.spring.chestnut.comment.dto;

import org.spring.chestnut.comment.entity.CommentEntity;

public class CommentResponse {

    private String content;

    public CommentResponse(CommentEntity comment) {
        this.content = comment.getContent();
    }
}
