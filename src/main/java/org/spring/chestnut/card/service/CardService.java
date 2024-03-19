package org.spring.chestnut.card.service;

import org.spring.chestnut.card.dto.CardRequest;
import org.spring.chestnut.card.dto.CardResponse;
import org.spring.chestnut.card.dto.WorkerRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface CardService {

    CardResponse createCard(Long boardId, Long columnId, CardRequest request,
        WorkerRequest workerRequest, UserDetails member);

}
