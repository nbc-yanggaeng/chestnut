package org.spring.chestnut.member.repository;

import java.util.Optional;
import org.spring.chestnut.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

  Optional<MemberEntity> findByEmail(String email);
}
