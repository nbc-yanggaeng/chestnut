package org.spring.chestnut.comment.dto;

import lombok.Getter;

@Getter
public class CommentCreateRequest {

    private String content;
    private Long cardId;

}
