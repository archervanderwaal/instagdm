package top.archer.instagdm.service;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.utils.SerializableCookieJar;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IGSerializeService {

    @Autowired
    private OkHttpClientService okHttpClientService;

    public Pair<byte[], byte[]> serialize(IGClient client) {
        byte[] clientBytes = SerializationUtils.serialize(client);
        byte[] cookieBytes = SerializationUtils.serialize((SerializableCookieJar) client.getHttpClient().cookieJar());
        return Pair.of(clientBytes, cookieBytes);
    }

    public IGClient deserialize(Pair<byte[], byte[]> bytes) {
        IGClient client = SerializationUtils.deserialize(bytes.getLeft());
        SerializableCookieJar cookieJar = SerializationUtils.deserialize(bytes.getRight());
        client.setHttpClient(okHttpClientService.createOkHttpClient(cookieJar));
        return client;
    }
}
