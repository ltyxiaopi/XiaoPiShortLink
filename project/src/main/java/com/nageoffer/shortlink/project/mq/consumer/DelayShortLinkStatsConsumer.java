package com.nageoffer.shortlink.project.mq.consumer;

import com.nageoffer.shortlink.project.dto.biz.ShortLinkStatsRecordDTO;
import com.nageoffer.shortlink.project.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

import static com.nageoffer.shortlink.project.common.constant.RedisKeyConstant.DELAY_QUEUE_STATS_KEY;

/**
 * 延迟记录短链接统计组件
 * 公众号：马丁玩编程，回复：加群，添加马哥微信（备注：link）获取项目资料
 */
@Component
@RequiredArgsConstructor
public class DelayShortLinkStatsConsumer implements InitializingBean {

    private final RedissonClient redissonClient;
    private final ShortLinkService shortLinkService;

    public void onMessage() {
        Executors.newSingleThreadExecutor(
                        runnable -> {
                            Thread thread = new Thread(runnable);
                            thread.setName("delay_short-link_stats_consumer");
                            thread.setDaemon(Boolean.TRUE);
                            return thread;
                        })
                .execute(() -> {
                    RBlockingDeque<ShortLinkStatsRecordDTO> blockingDeque = redissonClient.getBlockingDeque(DELAY_QUEUE_STATS_KEY);
                    RDelayedQueue<ShortLinkStatsRecordDTO> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
                    for (; ; ) {
                        try {
                            ShortLinkStatsRecordDTO statsRecord = delayedQueue.poll();
                            if (statsRecord != null) {
                                shortLinkService.shortLinkStats(null, null, statsRecord);
                                continue;
                            }
                            LockSupport.parkUntil(500);
                        } catch (Throwable ignored) {
                        }
                    }
                });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        onMessage();
    }
}
