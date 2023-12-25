package top.archer.instagdm.requests;

import com.github.instagram4j.instagram4j.IGClient;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.archer.instagdm.service.IGSerializeService;
import top.archer.instagdm.service.LoginService;

import java.util.Optional;

@SpringBootTest
public class InsRequestsTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private IGSerializeService serializeService;

    @Test
    public void testSearchUser() {
        Optional<IGClient> opClient = loginService.login("archervanderwaal", "ArcherVanderWaal0221@");
        Assertions.assertTrue(opClient.isPresent());
        Pair<byte[], byte[]> results = serializeService.serialize(opClient.get());
        IGClient cli = serializeService.deserialize(results);
        Assertions.assertEquals("archervanderwaal", cli.getSelfProfile().getUsername());
    }
}
