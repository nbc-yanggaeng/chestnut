package org.spring.chestnut.member.dto;

import lombok.Getter;
import org.spring.chestnut.global.execption.custom.NotMatchException;
import org.spring.chestnut.member.dto.request.UpdateRequestDto;

@Getter
public class UpdatePasswordDto {

    private final String password;
    private final String changePassword;
    private final String rechangePassword;

    public UpdatePasswordDto(UpdateRequestDto dto) {
        password = dto.getPassword();
        changePassword = dto.getChangePassword();
        rechangePassword = dto.getRechangePassword();
    }

    public void checkChangePasswordEquals() {
        if (!changePassword.equals(rechangePassword)) {
            throw new NotMatchException("바꿀 비밀번호가 일치하지 않습니다.");
        }
    }
}
