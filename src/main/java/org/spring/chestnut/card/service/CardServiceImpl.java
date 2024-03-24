package org.spring.chestnut.card.service;

import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.board.repository.CollaboratorRepository;
import org.spring.chestnut.card.dto.CardMoveRequest;
import org.spring.chestnut.card.dto.CardRequest;
import org.spring.chestnut.card.dto.CardResponse;
import org.spring.chestnut.card.dto.WorKerRequest;
import org.spring.chestnut.card.entity.CardEntity;
import org.spring.chestnut.card.entity.WorkerEntity;
import org.spring.chestnut.card.repository.CardRepository;
import org.spring.chestnut.card.repository.WorkerRepository;
import org.spring.chestnut.global.aop.Lockable;
import org.spring.chestnut.global.execption.custom.NotFoundException;
import org.spring.chestnut.global.execption.custom.WorkerException;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final WorkerRepository workerRepository;
    private final CollaboratorRepository collaboratorRepository;

    @Override
    public CardResponse createCard(Long boardId, Long columnId, CardRequest request,
        UserDetailsImpl userDetails) {

        if (!collaboratorRepository.existsByMemberIdAndBoardId(userDetails.getMemberId(),
            boardId)) {
            throw new IllegalArgumentException("협력자만 가능합니다");
        }

        CardEntity cardEntity = CardEntity.of(columnId, request);

        CardEntity save = cardRepository.save(cardEntity);

        return new CardResponse(save);
    }

    @Override
    public CardResponse updateCard(Long cardId, CardRequest request,
        UserDetailsImpl userDetails) {

        CardEntity cardEntity = cardRepository.findById(cardId)
            .orElseThrow(() -> new NotFoundException("없는 카드입니다."));

        isCollaborator(cardId, userDetails.getMemberId());

        cardEntity.updateCard(request);
        CardEntity updateCard = cardRepository.save(cardEntity);

        List<Long> workerList = workerRepository.findByCardId(cardId).stream()
            .map(WorkerEntity::getMemberId)
            .toList();

        return new CardResponse(updateCard, workerList);
    }

    @Override
    public void deleteCard(Long cardId, UserDetailsImpl userDetails) {

        CardEntity cardEntity = cardRepository.findById(cardId)
            .orElseThrow(() -> new NotFoundException("없는 카드입니다."));

        isCollaborator(cardId, userDetails.getMemberId());

        cardRepository.delete(cardEntity);
        workerRepository.deleteByCardId(cardId);
    }

    @Override
    @Transactional(readOnly = true)
    public CardResponse getCardByCardId(Long cardId, UserDetailsImpl userDetails) {

        CardEntity cardEntity = cardRepository.findById(cardId)
            .orElseThrow(() -> new NotFoundException("없는 카드입니다."));

        isCollaborator(cardId, userDetails.getMemberId());

        List<Long> workers = workerRepository.findByCardId(cardId).stream()
            .map(WorkerEntity::getMemberId)
            .toList();

        return new CardResponse(cardEntity, workers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardResponse> getCardsByColumnId(Long columnId, UserDetailsImpl userDetails) {

        Long boardId = cardRepository.findBoardIdByColumnId(columnId);
        if (boardId == null) {
            throw new NotFoundException("보드 데이터가 없습니다.");
        }

        if (!collaboratorRepository.existsByMemberIdAndBoardId(userDetails.getMemberId(),
            boardId)) {
            throw new IllegalArgumentException("협력자만 가능합니다");
        }

        return cardRepository.findAllCardsByColumnId(columnId);
    }

    @Override
    public CardResponse updateWorkers(Long cardId, WorKerRequest worKerRequest,
        UserDetailsImpl userDetails) {

        CardEntity card = cardRepository.findById(cardId)
            .orElseThrow(() -> new NotFoundException("없는 카드입니다."));

        Long boardId = cardRepository.findBoardIdByCardId(cardId);

        if (!collaboratorRepository.existsByMemberIdAndBoardId(userDetails.getMemberId(),
            boardId)) {
            throw new IllegalArgumentException("협력자만 가능합니다");
        }

        List<WorkerEntity> workerLists = workerRepository.findByCardIdOrderByMemberId(cardId);

        List<Long> collect = workerLists.stream()
            .map(WorkerEntity::getMemberId)
            .toList();

        if (worKerRequest.getAddRequest() != null
            && worKerRequest.getAddRequest().getWorkerList().size() != 0) {
            List<Long> addRequest = worKerRequest.getAddRequest().getWorkerList();

            if (!collaboratorRepository.existsByBoardIdAndMemberIdIn(boardId, addRequest)) {
                throw new IllegalArgumentException("협력자만 등록 가능합니다");
            }

            if (!addRequest.isEmpty() && new HashSet<>(collect).containsAll(addRequest)) {
                throw new WorkerException("이미 있는 작업자입니다.");
            } else {
                addRequest.forEach(
                    t -> workerRepository.save(WorkerEntity.of(cardId, t))
                );
            }
        }

        if (worKerRequest.getRemoveRequest() != null) {
            List<Long> removeRequest = worKerRequest.getRemoveRequest().getWorkerList();
            if (!new HashSet<>(collect).containsAll(removeRequest)) {
                throw new WorkerException("없는 작업자입니다.");
            } else {
                workerLists.stream().filter(
                    t -> removeRequest.contains(t.getMemberId())
                ).forEach(workerRepository::delete);
            }
        }

        List<Long> updateWorkers = workerRepository.findByCardId(cardId).stream()
            .map(WorkerEntity::getMemberId)
            .toList();

        return new CardResponse(card, updateWorkers);
    }

    @Override
    @Lockable(value = "moveCard", waitTime = 5000, leaseTime = 100)
    public CardResponse moveCard(Long cardId, CardMoveRequest request,
        UserDetailsImpl userDetails) {

        CardEntity card = cardRepository.findById(cardId)
            .orElseThrow(() -> new NotFoundException("없는 카드입니다."));

        isCollaborator(cardId, userDetails.getMemberId());

        card.moveCard(request.getMoveTo());
        CardEntity save = cardRepository.save(card);

        List<Long> workers = workerRepository.findByCardId(cardId)
            .stream().map(WorkerEntity::getMemberId)
            .toList();

        return new CardResponse(save, workers);
    }

    private void isCollaborator(Long cardId, Long memberId) {
        Long boardId = cardRepository.findBoardIdByCardId(cardId);

        if (!collaboratorRepository.existsByMemberIdAndBoardId(memberId, boardId)) {
            throw new IllegalArgumentException("협력자만 가능합니다");
        }
    }
}
