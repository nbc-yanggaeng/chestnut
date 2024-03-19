package org.spring.chestnut.board.repository;

import org.spring.chestnut.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<BoardEntity, Long> {

}
