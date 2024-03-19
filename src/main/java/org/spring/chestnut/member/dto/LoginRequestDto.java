package org.spring.chestnut.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

  private String email;
  private String password;
}
