package org.spring.chestnut.card.concurrency;

import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;
import org.spring.chestnut.card.dto.CardMoveRequest;
import org.spring.chestnut.card.service.CardService;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
public class ConcurrencyTest {

    @Autowired
    private CardService cardService;

    private UserDetailsImpl userDetails = new UserDetailsImpl(1L);

    private static int count = 0;

    @Test
    void concurrencyTest() {
        LongStream.range(0, 1000).parallel().forEach(
            i -> {
                i = i % 4 + 1;
                CardMoveRequest cardMoveRequest = new CardMoveRequest();
                ReflectionTestUtils.setField(cardMoveRequest, "moveTo", i);
                cardService.moveCard(1L, cardMoveRequest, userDetails);
                System.out.printf("\n\n\n\n카드가 %d로 옮겨졌습니다. %d번 실행 완료\n\n\n\n", i, count);
                count++;
            }
        );

        System.out.println(count + "번 실행 완료");

    }
}
