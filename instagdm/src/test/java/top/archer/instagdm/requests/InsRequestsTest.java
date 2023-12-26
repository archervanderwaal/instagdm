package top.archer.instagdm.requests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.archer.instagdm.service.IGSerializeService;
import top.archer.instagdm.service.LoginService;

@SpringBootTest
public class InsRequestsTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private IGSerializeService serializeService;

    @Test
    public void testSearchUser() {

    }
}
