package org.spring.chestnut.member.dto.response;

import lombok.Getter;

@Getter
public class MemberResponseDto {

    private final String email;

    public MemberResponseDto(String email) {
        this.email = email;
    }
}
