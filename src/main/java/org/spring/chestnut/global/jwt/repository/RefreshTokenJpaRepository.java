package org.spring.chestnut.global.jwt.repository;

import java.util.List;
import java.util.Optional;
import org.spring.chestnut.global.jwt.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByMemberId(Long id);

    List<RefreshTokenEntity> findAllByMemberId(Long id);
}
