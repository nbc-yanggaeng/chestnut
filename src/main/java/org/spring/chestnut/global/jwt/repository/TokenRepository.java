package org.spring.chestnut.global.jwt.repository;

import org.spring.chestnut.global.jwt.entity.RefreshTokenEntity;

public interface TokenRepository {

  void register(Long memberId, String token);

  RefreshTokenEntity findByMemberId(Long memberId);

  void deleteToken(RefreshTokenEntity token);
}
