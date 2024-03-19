package org.spring.chestnut.card.repository;

import static org.spring.chestnut.card.entity.QCardEntity.cardEntity;
import static org.spring.chestnut.card.entity.QWorkerEntity.workerEntity;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.card.dto.CardResponse;
import org.spring.chestnut.card.entity.CardEntity;

@RequiredArgsConstructor
public class CardRepositoryQueryImpl implements CardRepositoryQuery {

    private final JPAQueryFactory factory;


    @Override
    public List<CardResponse> findAllCardsByColumnId(Long columnId) {

        List<Tuple> query = factory.select(cardEntity, workerEntity)
            .from(cardEntity)
            .leftJoin(workerEntity).on(cardEntity.id.eq(workerEntity.cardId))
            .where(cardEntity.columnId.eq(columnId))
            .orderBy(cardEntity.deadline.desc())
            .orderBy(cardEntity.createdAt.asc())
            .orderBy(workerEntity.memberId.asc())
            .fetch();

        return query.stream()
            .collect(Collectors.groupingBy(
                t -> t.get(cardEntity),
                LinkedHashMap::new,
                Collectors.mapping(
                    t -> t.get(workerEntity),
                    Collectors.toList()
                )
            ))
            .entrySet().stream()
            .map(entry -> {
                CardEntity card = entry.getKey();
                List<Long> workers = entry.getValue().stream()
                    .map(worker -> worker != null ? worker.getMemberId() : null)
                    .toList();

                return new CardResponse(card, workers);
            })
            .collect(Collectors.toList());
    }
}
