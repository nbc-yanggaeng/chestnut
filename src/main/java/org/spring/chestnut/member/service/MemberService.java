package org.spring.chestnut.member.service;

import org.spring.chestnut.member.dto.request.LoginRequestDto;
import org.spring.chestnut.member.dto.request.SignupRequestDto;
import org.spring.chestnut.member.dto.request.UpdateRequestDto;
import org.spring.chestnut.member.dto.response.LoginResponseDto;
import org.spring.chestnut.member.dto.response.MemberResponseDto;

public interface MemberService {

  MemberResponseDto signup(SignupRequestDto dto);

  LoginResponseDto login(LoginRequestDto dto);

  void logout(Long memberId);

  void updatePassword(Long memberId, UpdateRequestDto dto);

  void delete(Long memberId);
}
