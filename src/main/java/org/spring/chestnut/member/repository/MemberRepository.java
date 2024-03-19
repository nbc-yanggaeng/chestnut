package org.spring.chestnut.member.repository;

import org.spring.chestnut.member.dto.request.SignupDto;
import org.spring.chestnut.member.dto.response.MemberResponseDto;

public interface MemberRepository {

  boolean checkEmail(String email);

  MemberResponseDto signup(SignupDto dto);
}
