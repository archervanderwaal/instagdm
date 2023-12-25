package top.archer.instagdm.limiter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@SuppressWarnings("all")
@Slf4j
public class InsReqLimiter {

    private final Cache<String, RateLimiter> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.DAYS)
            .maximumSize(1000000)
            .build();

    public void acquire(String url) {
        log.info("acquiring token for url: {}", url);
        getLimiter(url).acquire();
        log.info("acquired token for url: {}", url);
    }

    private RateLimiter getLimiter(String username) {
        RateLimiter limiter = cache.getIfPresent(username);
        if (limiter != null) {
            return limiter;
        }
        // 5s一次请求, 防止被ins风控
        limiter = RateLimiter.create(0.2);
        cache.put(username, limiter);
        return limiter;
    }
}
