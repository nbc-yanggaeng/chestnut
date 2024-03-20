package org.spring.chestnut.member.repository;

import java.util.Optional;
import org.spring.chestnut.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByEmail(String email);

    @Query(value = "delete from CollaboratorEntity c where c.memberId = :memberId")
    void deleteCollaborator(Long memberId);

    @Query(value = "delete from WorkerEntity w where w.memberId = :memberId")
    void deleteWorkers(Long memberId);
}
