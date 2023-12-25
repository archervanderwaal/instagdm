package top.archer.instagdm.proxy;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ProxyServiceTest {

    @Autowired
    private ProxyService proxyService;

    @Test
    void testProxyClient() throws IOException {
        OkHttpClient client = proxyService.createClient();
        String targetUrl = "https://dev.kdlapi.com/testproxy";

        Request request = new Request.Builder()
                .url(targetUrl)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3100.0 Safari/537.36")
                .build();

        Response response = client.newCall(request).execute();
        try (ResponseBody body = response.body()) {
            Assertions.assertNotNull(body);
            Assertions.assertTrue(body.string().contains("client ip"));
        }
    }
}
