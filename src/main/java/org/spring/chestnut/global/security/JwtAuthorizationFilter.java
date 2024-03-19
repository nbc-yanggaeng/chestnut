package org.spring.chestnut.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.chestnut.global.dto.ResponseDto;
import org.spring.chestnut.global.jwt.JwtProvider;
import org.spring.chestnut.global.jwt.TokenState;
import org.spring.chestnut.global.jwt.entity.RefreshTokenEntity;
import org.spring.chestnut.global.jwt.repository.TokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final TokenRepository tokenRepository;
  private final UserDetailsServiceImpl userDetailsService;

  ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String tokenValue = jwtProvider.getAccessTokenFromRequest(request);

    if (!StringUtils.hasText(tokenValue)) {
      filterChain.doFilter(request, response);
      return;
    }

    TokenState state = jwtProvider.validateToken(tokenValue);

    // 토큰이 불일치라면?
    if (state.equals(TokenState.INVALID)) {
      log.error("Token Error");
      return;
    }

    // 토큰이 만료가 된다면?
    if (state.equals(TokenState.EXPIRED)) {
      refreshTokenAndHandleException(tokenValue, response);
      return;
    }

    Claims info = jwtProvider.getMemberInfoFromToken(tokenValue);

    try {
      setAuthentication(info.getSubject());
    } catch (Exception e) {
      log.error(e.getMessage());
      return;
    }

    filterChain.doFilter(request, response);
  }

  // 인증 처리
  public void setAuthentication(String username) {

    SecurityContext context = SecurityContextHolder.createEmptyContext();
    Authentication authentication = createAuthentication(username);
    context.setAuthentication(authentication);

    SecurityContextHolder.setContext(context);
  }

  // 인증 객체 생성
  private Authentication createAuthentication(String username) {

    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  // 토큰을 재발급 및 예외처리하는 메서드
  private void refreshTokenAndHandleException(String tokenValue, HttpServletResponse response) {

    try {
      Claims info = jwtProvider.getMemberInfoFromExpiredToken(tokenValue);
      UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(
          info.getSubject());
      RefreshTokenEntity refreshToken = tokenRepository.findByMemberId(
          userDetails.getMember().getId());

      TokenState refreshState = jwtProvider.validateToken(refreshToken.getToken());

      if (refreshState.equals(TokenState.VALID)) {
        refreshAccessToken(response, userDetails);
      } else {
        handleExpiredToken(response, refreshToken);
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  // 이미 토큰 저장소에 있는 토큰이 없을 경우에 대한 메서드
  private void handleExpiredToken(HttpServletResponse response, RefreshTokenEntity refreshToken) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    String jsonResponse = objectMapper.writeValueAsString(
        ResponseDto.of(HttpStatus.UNAUTHORIZED, "모든 토큰이 만료되었습니다.", null));
    tokenRepository.deleteToken(refreshToken);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(jsonResponse);
  }

  // 토큰 저장소에 토큰이 남아 있을 경우에 대한 메서드
  private void refreshAccessToken(HttpServletResponse response, UserDetailsImpl userDetails)
      throws IOException {

    String newAccessToken = jwtProvider.generateRefreshToken(userDetails.getUsername(), "User");
    response.addHeader(JwtProvider.AUTHORIZATION_ACCESS_TOKEN_HEADER_KEY, newAccessToken);
    response.setStatus(HttpServletResponse.SC_OK);

    String jsonResponse = objectMapper.writeValueAsString(
        ResponseDto.ok("성공적으로 토큰을 발급하였습니다.", null));

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(jsonResponse);
  }
}
