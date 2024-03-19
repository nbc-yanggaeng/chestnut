package org.spring.chestnut.card.repository;

import org.spring.chestnut.card.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardEntity, Long>, CardRepositoryQuery {

}
