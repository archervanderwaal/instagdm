package top.archer.instagdm.service;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.utils.SerializableCookieJar;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.archer.instagdm.mapper.InsClientMapper;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@SuppressWarnings("all")
public class LoginService {

    @Autowired
    private InsClientMapper clientMapper;

    @Autowired
    private OkHttpClientService okHttpClientService;

    @Autowired
    private IGSerializeService serializeService;

    private static final Cache<String, IGClient> cache = CacheBuilder.newBuilder()
            .maximumSize(100000)
            .expireAfterAccess(1, TimeUnit.DAYS)
            .build();

    public Optional<IGClient> login(String username, String passwd) {
        try {
            String cacheKey = generateCacheKey(username, passwd);
            IGClient ig;
            if ((ig = cache.getIfPresent(cacheKey)) != null) {
                return Optional.of(ig);
            }
            ig = IGClient.builder().username(username).password(passwd)
                    .client(okHttpClientService.createOkHttpClient(new SerializableCookieJar()))
                    .login();
            cache.put(cacheKey, ig);
            return Optional.ofNullable(ig);
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    private static String generateCacheKey(String username, String password) {
        return String.join("$", username, password);
    }
}
