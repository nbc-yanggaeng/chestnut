package org.spring.chestnut.global.jwt.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.global.jwt.entity.RefreshTokenEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository{

  private final RefreshTokenJpaRepository refreshTokenJpaRepository;

  @Override
  public void register(Long memberId, String token) {
    RefreshTokenEntity entity = RefreshTokenEntity.of(memberId, token);
    refreshTokenJpaRepository.save(entity);
  }

  @Override
  public RefreshTokenEntity findByMemberId(Long memberId) {
    return refreshTokenJpaRepository.findByMemberId(memberId).orElseThrow(
        () -> new IllegalArgumentException("존재하지 않은 토큰입니다.") // CustomException 해야함
    );
  }

  @Override
  public List<RefreshTokenEntity> findAllByMemberId(Long memberId) {
    return refreshTokenJpaRepository.findAllByMemberId(memberId);
  }

  @Override
  public void deleteToken(RefreshTokenEntity token) {
    refreshTokenJpaRepository.delete(token);
  }
}
