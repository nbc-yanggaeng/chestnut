package org.spring.chestnut.board.repository;

import org.spring.chestnut.board.entity.CollaboratorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollaboratorRepository extends JpaRepository<CollaboratorEntity, Long> {

    boolean existsByMemberIdAndBoardId(Long memberId, Long boardId);
}
