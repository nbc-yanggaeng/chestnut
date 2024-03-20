package org.spring.chestnut.card.service;

import java.util.List;
import org.spring.chestnut.card.dto.CardMoveRequest;
import org.spring.chestnut.card.dto.CardRequest;
import org.spring.chestnut.card.dto.CardResponse;
import org.spring.chestnut.card.dto.WorKerRequest;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;

public interface CardService {

    CardResponse createCard(Long boardId, Long columnId, CardRequest request,
        UserDetails member);

    CardResponse updateCard(Long cardId, CardRequest request, UserDetails member);

    void deleteCard(Long cardId, UserDetails member);

    CardResponse getCardByCardId(Long cardId);

    List<CardResponse> getCardsByColumnId(Long columnId);

    CardResponse updateWorkers(Long cardId, WorKerRequest worKerRequest,
        UserDetailsImpl userDetails);

    CardResponse moveCard(Long cardId, CardMoveRequest request, UserDetailsImpl userDetails);
}
