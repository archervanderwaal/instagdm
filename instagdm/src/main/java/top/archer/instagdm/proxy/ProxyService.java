package top.archer.instagdm.proxy;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

@Service
@SuppressWarnings("all")
public class ProxyService {

    @Value("${proxy.host}")
    private String proxyHost;
    @Value("${proxy.port}")
    private int proxyPort;
    @Value("${proxy.username}")
    private String username;
    @Value("${proxy.password}")
    private String password;

    public OkHttpClient createClient() {
        return doCreateClient();
    }

    private OkHttpClient doCreateClient() {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

        Authenticator authenticator = new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(username, password);
                return response.request().newBuilder().header("Proxy-Authorization", credential).build();
            }
        };
        return new OkHttpClient.Builder().proxy(proxy).proxyAuthenticator(authenticator).build();
    }
}
