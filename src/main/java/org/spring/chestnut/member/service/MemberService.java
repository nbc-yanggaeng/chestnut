package org.spring.chestnut.member.service;

import org.spring.chestnut.member.dto.request.SignupRequestDto;
import org.spring.chestnut.member.dto.response.MemberResponseDto;

public interface MemberService {

  MemberResponseDto signup(SignupRequestDto dto);
}
