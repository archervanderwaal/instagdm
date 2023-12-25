package top.archer.instagdm.spider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Credit51SpiderTest {

    @Autowired
    private Credit51Spider credit51Spider;

    @Test
    public void testQueryThreads() {
        credit51Spider.queryThreads(1);
    }
}
