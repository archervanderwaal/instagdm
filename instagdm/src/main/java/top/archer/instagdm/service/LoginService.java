package top.archer.instagdm.service;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.utils.IGUtils;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginService {

    private final Map<String, IGClient> clientCache = new ConcurrentHashMap<>();

    private static final Proxy PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("43.137.8.86", 8123));

    public Optional<IGClient> login(String username, String passwd) {
        try {
            IGClient client = null;
            if ((client = clientCache.get(username + passwd)) != null) {
                return Optional.of(client);
            }
            client = IGClient.builder().username(username).password(passwd)
                    .client(okHttpClientWithProxy())
                    .login();
            if (client != null) {
                clientCache.put(username + passwd, client);
            }
            return Optional.ofNullable(client);
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    private static OkHttpClient okHttpClientWithProxy() {
        return IGUtils.defaultHttpClientBuilder()
                .proxy(PROXY)
                .build();
    }
}
