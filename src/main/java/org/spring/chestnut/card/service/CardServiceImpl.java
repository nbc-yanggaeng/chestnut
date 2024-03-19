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

}
