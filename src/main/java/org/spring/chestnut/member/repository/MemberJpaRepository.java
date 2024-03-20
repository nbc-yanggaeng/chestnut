package org.spring.chestnut.member.repository;

import java.util.List;
import java.util.Optional;
import org.spring.chestnut.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByEmail(String email);

    @Modifying
    @Query(value = "delete from CollaboratorEntity c where c.memberId = :memberId")
    void deleteCollaborator(Long memberId);

    @Modifying
    @Query(value = "delete from WorkerEntity w where w.memberId = :memberId")
    void deleteWorkers(Long memberId);

    @Modifying
    @Query(value = "delete from BoardEntity b where b.createMemberId = :memberId")
    void deleteBoard(Long memberId);

    @Modifying
    @Query(value = "delete from ColumnEntity c where c.boardId = :boardId")
    void deleteColumn(Long boardId);

    @Modifying
    @Query(value = "delete from CardEntity c where c.columnId in (select c.id from ColumnEntity c where c.boardId = :boardId)")
    void deleteCard(Long boardId);

    @Modifying
    @Query(value = "delete from CommentEntity c where c.memberId = :memberId")
    void deleteComment(Long memberId);

    @Modifying
    @Query(value = "select b.id from BoardEntity b where b.createMemberId = :memberId")
    List<Long> findBoardIds(Long memberId);
}
