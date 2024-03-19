package org.spring.chestnut.member.controller;

import lombok.RequiredArgsConstructor;
import org.spring.chestnut.global.dto.ResponseDto;
import org.spring.chestnut.member.dto.request.SignupRequestDto;
import org.spring.chestnut.member.dto.response.MemberResponseDto;
import org.spring.chestnut.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/signup")
  public ResponseEntity<ResponseDto<MemberResponseDto>> signup(
      @Validated @RequestBody SignupRequestDto dto
  ) {
    MemberResponseDto responseDto = memberService.signup(dto);
    return ResponseDto.ok("회원 가입에 성공하였습니다.", responseDto);
  }
}
