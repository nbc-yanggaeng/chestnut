package org.spring.chestnut.card.repository;

import org.spring.chestnut.card.entity.WorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<WorkerEntity, Long> {

}
