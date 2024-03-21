package org.spring.chestnut.global.aop;

import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
@Slf4j(topic = "LockAspect")
public class LockAspect {

    private final RedissonClient redissonClient;

    @Around("@annotation(lockable)")
    public Object applyLock(ProceedingJoinPoint joinPoint, Lockable lockable) throws Throwable {
        RLock lock = redissonClient.getFairLock(lockable.value());
        try {
            // 락 획득 시도
            if (lock.tryLock(5000, 100, TimeUnit.MILLISECONDS)) {
                log.info("get lock");
                return joinPoint.proceed();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("lock 획득 실패2");
        } finally {
            log.info("unlock");
            lock.unlock();
        }
        return null;
    }
}
