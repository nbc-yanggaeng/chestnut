package org.spring.chestnut.card.service;

import java.util.List;
import org.spring.chestnut.card.dto.CardMoveRequest;
import org.spring.chestnut.card.dto.CardRequest;
import org.spring.chestnut.card.dto.CardResponse;
import org.spring.chestnut.card.dto.WorKerRequest;
import org.spring.chestnut.global.security.UserDetailsImpl;

public interface CardService {

    CardResponse createCard(Long boardId, Long columnId, CardRequest request,
        UserDetailsImpl userDetails);

    CardResponse updateCard(Long cardId, CardRequest request, UserDetailsImpl userDetails);

    void deleteCard(Long cardId, UserDetailsImpl userDetails);

    CardResponse getCardByCardId(Long cardId, UserDetailsImpl userDetails);

    List<CardResponse> getCardsByColumnId(Long columnId, UserDetailsImpl userDetails);

    CardResponse updateWorkers(Long cardId, WorKerRequest worKerRequest,
        UserDetailsImpl userDetails);

    CardResponse moveCard(Long cardId, CardMoveRequest request, UserDetailsImpl userDetails);
}
