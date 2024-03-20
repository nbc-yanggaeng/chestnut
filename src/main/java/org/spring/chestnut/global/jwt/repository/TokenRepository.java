package org.spring.chestnut.global.jwt.repository;

import java.util.List;
import org.spring.chestnut.global.jwt.entity.RefreshTokenEntity;

public interface TokenRepository {

    void register(Long memberId, String token);

    RefreshTokenEntity findByMemberId(Long memberId);

    List<RefreshTokenEntity> findAllByMemberId(Long memberId);

    void deleteToken(RefreshTokenEntity token);
}
