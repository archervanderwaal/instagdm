package top.archer.instagdm.task;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import top.archer.instagdm.data.WeiboUserInfo;
import top.archer.instagdm.mapper.WeiboUserMapper;
import top.archer.instagdm.model.WeiboUser;
import top.archer.instagdm.service.WeiboService;
import top.archer.instagdm.spider.WeiboSpider;
import top.archer.instagdm.utils.PhoneUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class WeiboTask {

    @Autowired
    private WeiboSpider weiboSpider;

    @Autowired
    private WeiboService weiboService;

    @Autowired
    private WeiboUserMapper userMapper;

//    @PostConstruct
    public void startTask() {
        log.info("[Task] start weibo spider task...");
        new Thread(this::run).start();
        log.info("[Task] weibo spider task finished...");
    }

    public void startTaskByUids(List<Long> uids) {
        try {
            List<Long> allFansId = uids.stream()
                    .map(uid -> weiboSpider.queryFansByUid(String.valueOf(uid)))
                    .flatMap(Collection::stream).toList();
            for (Long fanId : allFansId) {
                Optional<WeiboUserInfo> opWeiboInfo = weiboService.getInfoByWeiboId(String.valueOf(fanId));
                opWeiboInfo.ifPresent(u -> userMapper.insertToOutput(u));
            }
        } catch (Exception ignored) {
        }
    }


    public void run() {
        int start = 1, limit = 1000;
        List<WeiboUser> users = userMapper.queryByLimit(start, limit);
        while (!CollectionUtils.isEmpty(users)) {
            run(users);
            start = limit;
            users = userMapper.queryByLimit(start, limit);
            System.gc();
        }
    }

    private void run(List<WeiboUser> users) {
        List<WeiboUser> list = users.stream()
                .filter(u -> PhoneUtils.getAdd(u.getPhone(), "+86")
                        .contains("北京")).toList();
        list.forEach(u -> {
            Set<String> follows = weiboSpider.queryFollowListByUid(u.getUid());
            log.info("[Task] query phone:{} follow list", u.getPhone());
            if (follows.stream().anyMatch(WeiboTask::focus)) {
                WeiboUserInfo userInfo = new WeiboUserInfo();
                userInfo.setWeiboId(u.getUid());
                userInfo.setPhone(u.getPhone());
                userInfo.setPhoneLocation(PhoneUtils.getAdd(u.getPhone(), "+86"));
                userMapper.insertToOutput(userInfo);
            }
        });
    }

















    private static boolean focus(String follow) {
        return follow.contains("借钱")
                || follow.contains("借款")
                || follow.contains("信用卡")
                || follow.contains("银行")
                || follow.contains("贷款")
                || follow.contains("网贷");
    }
}
