package top.archer.instagdm.service;

import com.github.instagram4j.instagram4j.utils.IGUtils;
import com.github.instagram4j.instagram4j.utils.SerializableCookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.archer.instagdm.limiter.InsReqLimiter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

@Service
@SuppressWarnings("all")
public class OkHttpClientService {

    @Autowired
    private InsReqLimiter limiter;

    private static final Proxy PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("43.137.8.86", 8123));

    public OkHttpClient createOkHttpClient(SerializableCookieJar cookieJar) {
        Interceptor interceptor = new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
                Request request = chain.request();
                String path = request.url().encodedPath();
                limiter.acquire(path);
                return chain.proceed(request);
            }
        };
        return IGUtils.defaultHttpClientBuilder()
                .cookieJar(cookieJar)
                .proxy(PROXY)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(240, TimeUnit.SECONDS)
                .build();
    }
}
