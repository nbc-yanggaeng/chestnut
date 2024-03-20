package org.spring.chestnut.card.repository;

import java.util.List;
import org.spring.chestnut.card.dto.CardResponse;

public interface CardRepositoryQuery {

    List<CardResponse> findAllCardsByColumnId(Long columnId);

}
