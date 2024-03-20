package org.spring.chestnut.member.dto.request;

import lombok.Getter;

@Getter
public class LoginRequestDto {

    private String email;
    private String password;
}
