package org.spring.chestnut.member.service;

import lombok.RequiredArgsConstructor;
import org.spring.chestnut.member.dto.request.SignupDto;
import org.spring.chestnut.member.dto.request.SignupRequestDto;
import org.spring.chestnut.member.dto.response.MemberResponseDto;
import org.spring.chestnut.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

  private final MemberRepository memberRepository;

  @Override
  public MemberResponseDto signup(SignupRequestDto dto) {

    if(memberRepository.checkEmail(dto.getEmail())) {
      throw new IllegalArgumentException("해당 이메일이 존재합니다.");
    }

    SignupDto signupDto = new SignupDto(dto.getEmail(), dto.getPassword());

    return memberRepository.signup(signupDto);
  }
}
