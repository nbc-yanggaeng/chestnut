package org.spring.chestnut.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

  @NotBlank
  @Email
  @Size(max = 50)
  private String email;

  @NotBlank
  @Size(min = 8, max = 15)
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]+$", message = "대,소문자 및 특수문자 숫자가 최소 하나씩 들어가야합니다.")
  private String password;
}
