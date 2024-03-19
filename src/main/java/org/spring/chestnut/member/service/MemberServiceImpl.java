package org.spring.chestnut.member.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.global.jwt.JwtProvider;
import org.spring.chestnut.global.jwt.entity.RefreshTokenEntity;
import org.spring.chestnut.global.jwt.repository.TokenRepository;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.spring.chestnut.member.dto.UpdatePasswordDto;
import org.spring.chestnut.member.dto.request.LoginRequestDto;
import org.spring.chestnut.member.dto.request.SignupDto;
import org.spring.chestnut.member.dto.request.SignupRequestDto;
import org.spring.chestnut.member.dto.request.UpdateRequestDto;
import org.spring.chestnut.member.dto.response.LoginResponseDto;
import org.spring.chestnut.member.dto.response.MemberResponseDto;
import org.spring.chestnut.member.entity.MemberEntity;
import org.spring.chestnut.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final TokenRepository tokenRepository;

  @Override
  public MemberResponseDto signup(SignupRequestDto dto) {

    if(memberRepository.checkEmail(dto.getEmail())) {
      throw new IllegalArgumentException("해당 이메일이 존재합니다.");
    }

    SignupDto signupDto = new SignupDto(dto.getEmail(), dto.getPassword());

    return memberRepository.signup(signupDto);
  }

  @Override
  @Transactional
  public LoginResponseDto login(LoginRequestDto dto) {
    MemberEntity member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(
        () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
    );

    if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    String token = generateToken(member.getId());
    return new LoginResponseDto(member.getEmail(), token);
  }

  @Override
  @Transactional
  public void logout(UserDetailsImpl userDetails) {
    List<RefreshTokenEntity> refreshTokenEntityList = tokenRepository.findAllByMemberId(
        userDetails.getMemberId());

    refreshTokenEntityList.forEach(tokenRepository::deleteToken);
  }

  @Override
  @Transactional
  public void updatePassword(Long memberId, UpdateRequestDto dto) {
    MemberEntity member = memberRepository.findByMemberId(memberId).orElseThrow(
        () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
    );

    if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto(dto);
    updatePasswordDto.checkChangePasswordEquals();

    member.updatePassword(passwordEncoder.encode(dto.getChangePassword()));
  }

  private String generateToken(Long memberId) {
    String refreshToken = jwtProvider.generateRefreshToken(memberId, "User");
    refreshToken = jwtProvider.substringToken(refreshToken);
    tokenRepository.register(memberId, refreshToken);

    return jwtProvider.generateAccessToken(memberId, "User");
  }
}
