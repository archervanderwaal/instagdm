package top.archer.instagdm.spider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeiboSpiderTest {

    @Autowired
    private WeiboSpider weiboSpider;

    @Test
    public void testGetFollowListByUid() {
        //System.out.println(weiboSpider.queryFollowListByUid("5813490912"));
    }
}
