package org.spring.chestnut.card.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.card.dto.CardRequest;
import org.spring.chestnut.card.dto.CardResponse;
import org.spring.chestnut.card.dto.WorkerRequest;
import org.spring.chestnut.card.service.CardService;
import org.spring.chestnut.global.dto.ResponseDto;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CardController {

    private final CardService cardService;

    @PostMapping("/board/{boardId}/columns/{columnId}/cards")
    public ResponseEntity<ResponseDto<CardResponse>> createCard(
        @PathVariable Long boardId,
        @PathVariable Long columnId,
        @Valid @RequestBody CardRequest cardRequest,
        @RequestBody WorkerRequest workerRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        CardResponse cardResponse = cardService.createCard(boardId, columnId, cardRequest,
            workerRequest, userDetails);

        return ResponseDto.ok("카드 생성 성공", cardResponse);
    }

    @PutMapping("/cards/{cardId}")
    public ResponseEntity<ResponseDto<CardResponse>> updateCard(
        @PathVariable Long cardId,
        @RequestBody CardRequest cardRequest,
        @RequestBody WorkerRequest workerRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        CardResponse response = cardService.updateCard(cardId, cardRequest, workerRequest,
            userDetails);

        return ResponseDto.ok("카드 수정 성공", response);
    }

    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<ResponseDto<Void>> deleteCard(
        @PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        cardService.deleteCard(cardId, userDetails);

        return ResponseDto.ok("카드 삭제 성공", null);
    }

}
