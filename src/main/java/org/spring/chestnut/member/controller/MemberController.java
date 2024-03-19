package org.spring.chestnut.member.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.global.dto.ResponseDto;
import org.spring.chestnut.global.jwt.JwtProvider;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.spring.chestnut.member.dto.request.LoginRequestDto;
import org.spring.chestnut.member.dto.request.SignupRequestDto;
import org.spring.chestnut.member.dto.request.UpdateRequestDto;
import org.spring.chestnut.member.dto.response.LoginResponseDto;
import org.spring.chestnut.member.dto.response.MemberResponseDto;
import org.spring.chestnut.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @PostMapping("/login")
  public ResponseEntity<ResponseDto<String>> login(
      @Validated @RequestBody LoginRequestDto dto, HttpServletResponse response
  ) {
    LoginResponseDto responseDto = memberService.login(dto);
    response.addHeader(JwtProvider.AUTHORIZATION_ACCESS_TOKEN_HEADER_KEY, responseDto.getToken());
    return ResponseDto.ok("로그인에 성공하였습니다.", responseDto.getUsername());
  }

  @PostMapping("/logout")
  public ResponseEntity<ResponseDto<Void>> logout(
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    memberService.logout(userDetails);
    return ResponseDto.ok("로그아웃에 성공하였습니다.", null);
  }

  @PutMapping
  public ResponseEntity<ResponseDto<Void>> updatePassword(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody UpdateRequestDto dto
  ) {
    memberService.updatePassword(userDetails.getMemberId(), dto);
    return ResponseDto.ok("회원의 비밀번호를 수정하였습니다.", null);
  }
}
