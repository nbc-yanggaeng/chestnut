package org.spring.chestnut.card.controller;


import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.card.dto.CardMoveRequest;
import org.spring.chestnut.card.dto.CardRequest;
import org.spring.chestnut.card.dto.CardResponse;
import org.spring.chestnut.card.dto.WorKerRequest;
import org.spring.chestnut.card.service.CardService;
import org.spring.chestnut.global.dto.ResponseDto;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CardResponse cardResponse = cardService.createCard(boardId, columnId, cardRequest,
            userDetails);

        return ResponseDto.ok("카드 생성 성공", cardResponse);
    }

    @PutMapping("/cards/{cardId}")
    public ResponseEntity<ResponseDto<CardResponse>> updateCard(
        @PathVariable Long cardId,
        @Valid @RequestBody CardRequest cardRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        CardResponse response = cardService.updateCard(cardId, cardRequest, userDetails);

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

    @GetMapping("/cards/{cardId}")
    public ResponseEntity<ResponseDto<CardResponse>> getCardByCardId(
        @PathVariable Long cardId
    ) {
        CardResponse response = cardService.getCardByCardId(cardId);

        return ResponseDto.ok("카드 조회 성공", response);
    }

    @GetMapping("/column/{columnId}/cards")
    public ResponseEntity<ResponseDto<List<CardResponse>>> getCardsByColumnId(
        @PathVariable Long columnId
    ) {
        List<CardResponse> responseList = cardService.getCardsByColumnId(columnId);

        return ResponseDto.ok("카드 전체 조회 성공", responseList);
    }

    @PutMapping("/cards/{cardId}/workers")
    public ResponseEntity<ResponseDto<CardResponse>> updateWorkers(
        @PathVariable Long cardId,
        @RequestBody WorKerRequest worKerRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CardResponse response = cardService.updateWorkers(cardId, worKerRequest, userDetails);

        return ResponseDto.ok("작업자 수정 완료", response);
    }

    @PutMapping("/cards/{cardId}/move")
    public ResponseEntity<ResponseDto<CardResponse>> moveCard(
        @PathVariable Long cardId,
        @RequestBody CardMoveRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CardResponse response = cardService.moveCard(cardId, request, userDetails);

        return ResponseDto.ok("카드 위치 변경완료", response);
    }

}
