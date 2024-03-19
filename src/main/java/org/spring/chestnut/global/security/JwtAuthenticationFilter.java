package org.spring.chestnut.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.spring.chestnut.global.dto.ResponseDto;
import org.spring.chestnut.global.jwt.JwtProvider;
import org.spring.chestnut.global.jwt.repository.TokenRepository;
import org.spring.chestnut.member.dto.LoginRequestDto;
import org.spring.chestnut.member.entity.MemberEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtProvider jwtProvider;

  private final TokenRepository tokenRepository;

  ObjectMapper objectMapper = new ObjectMapper();

  public JwtAuthenticationFilter(JwtProvider jwtProvider, TokenRepository tokenRepository) {
    this.jwtProvider = jwtProvider;
    this.tokenRepository = tokenRepository;
    setFilterProcessesUrl("/members/login"); // 수정할 수 있음
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
          LoginRequestDto.class);

      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(
              requestDto.getEmail(),
              requestDto.getPassword(),
              null
          )
      );
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException {
    MemberEntity member = ((UserDetailsImpl) authResult.getPrincipal()).getMember();

    String token = jwtProvider.generateAccessToken(member.getEmail(), "User");
    String refreshToken = jwtProvider.generateRefreshToken(member.getEmail(), "User");
    refreshToken = jwtProvider.substringToken(refreshToken);
    tokenRepository.register(member.getId(), refreshToken);
    response.addHeader(JwtProvider.AUTHORIZATION_ACCESS_TOKEN_HEADER_KEY, token);
    response.setStatus(HttpServletResponse.SC_OK);

    String jsonResponse = objectMapper.writeValueAsString(
        ResponseDto.ok("로그인에 실패하였습니다.", null));

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(jsonResponse);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed) throws IOException {
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

    String jsonResponse = objectMapper.writeValueAsString(
        ResponseDto.badRequest("로그인에 실패하였습니다."));

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(jsonResponse);
  }

}
