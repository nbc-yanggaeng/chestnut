package org.spring.chestnut.card.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.card.dto.CardRequest;
import org.spring.chestnut.card.dto.CardResponse;
import org.spring.chestnut.card.dto.WorkerRequest;
import org.spring.chestnut.card.entity.CardEntity;
import org.spring.chestnut.card.entity.WorkerEntity;
import org.spring.chestnut.card.repository.CardRepository;
import org.spring.chestnut.card.repository.WorkerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final WorkerRepository workerRepository;

    @Override
    public CardResponse createCard(Long boardId, Long columnId, CardRequest request,
        WorkerRequest workerRequest, UserDetails member) {

        CardEntity cardEntity = CardEntity.of(columnId, request);

        CardEntity save = cardRepository.save(cardEntity);

        List<Long> workerList = workerRequest.getWorkerList().stream()
            .map(t -> {
                WorkerEntity workerEntity = WorkerEntity.of(columnId, t);
                workerRepository.save(workerEntity);
                return workerEntity.getMemberId();
            })
            .toList();

        return new CardResponse(save, workerList);
    }

    @Override
    public CardResponse updateCard(Long cardId, CardRequest request, WorkerRequest workerRequest,
        UserDetails member) {

        CardEntity cardEntity = cardRepository.findById(cardId)
            .orElseThrow(() -> new IllegalArgumentException("없는 카드입니다."));

        cardEntity.updateCard(request);
        CardEntity updateCard = cardRepository.saveAndFlush(cardEntity);

        List<Long> workerList = workerRequest.getWorkerList();
        List<WorkerEntity> workers = workerRepository.findByCardId(cardId);

        workers.forEach(t -> {
            if (workerList.contains(t.getMemberId())) {
                workerList.remove(t.getMemberId());
            } else {
                workerRepository.delete(t);
            }
        });

        workerList.forEach(
            t -> workerRepository.save(WorkerEntity.of(cardId, t))
        );

        List<Long> updateWorkers = workerRepository.findByCardId(cardId).stream()
            .map(WorkerEntity::getMemberId)
            .toList();

        return new CardResponse(updateCard, updateWorkers);
    }

    @Override
    public void deleteCard(Long cardId, UserDetails member) {

        CardEntity cardEntity = cardRepository.findById(cardId)
            .orElseThrow(() -> new IllegalArgumentException("없는 카드입니다."));

        cardRepository.delete(cardEntity);
        workerRepository.deleteByCardId(cardId);
    }

}
