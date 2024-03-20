package org.spring.chestnut.member.dto.request;

import lombok.Getter;

@Getter
public class SignupDto {

    private final String email;
    private final String password;

    public SignupDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
