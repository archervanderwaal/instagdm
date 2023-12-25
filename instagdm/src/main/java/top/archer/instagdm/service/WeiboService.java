package top.archer.instagdm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.archer.instagdm.data.WeiboUserInfo;
import top.archer.instagdm.mapper.WeiboUserMapper;
import top.archer.instagdm.model.WeiboUser;
import top.archer.instagdm.utils.PhoneUtils;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WeiboService {

    @Autowired
    private WeiboUserMapper userMapper;

    @SuppressWarnings("all")
    public Optional<WeiboUserInfo> getInfoByWeiboId(String weiboId) {
        try {
            List<WeiboUser> weiboUsers = userMapper.queryByUid(weiboId);
            return weiboUsers.stream().map(u -> {
                String phone = u.getPhone();
                String location = PhoneUtils.getAdd(phone, "+86");
                WeiboUserInfo userInfo = new WeiboUserInfo();
                userInfo.setWeiboId(weiboId);
                userInfo.setPhone(phone);
                userInfo.setPhoneLocation(location);
                return userInfo;
            }).findAny();
        } catch (Exception e) {
            log.error("[WeiboService] queryInfoByWeiboId exception: ", e);
            return Optional.empty();
        }
    }
}
