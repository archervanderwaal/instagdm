package top.archer.instagdm.service;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.requests.friendships.FriendshipsFeedsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.archer.instagdm.dto.FollowersQueryDTO;
import top.archer.instagdm.mapper.InsFollowersMapper;
import top.archer.instagdm.model.InsFollowers;
import top.archer.instagdm.result.Result;
import top.archer.instagdm.result.ResultBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class FollowersService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private InsFollowersMapper followersMapper;

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public Result<String> startQuery(FollowersQueryDTO dto, String user) {
        Optional<IGClient> opClient = loginService.login(dto.getUsername(), dto.getPassword());
        if (opClient.isEmpty()) {
            log.error("[requests] startQuery failed: 登录失败");
            return ResultBuilder.fail("登录Ins失败, 请检查用户名密码是否正确");
        }
        executor.submit(() -> onQuery(user, opClient.get()).thenAccept(this::saveToMysql));
        return ResultBuilder.success("提交采集任务成功, 稍后在采集列表中查询结果");
    }

    public List<InsFollowers> listFollowers() {
        return followersMapper.queryAll();
    }

    private CompletableFuture<List<InsFollowers>> onQuery(String user, IGClient client) {
        return client.actions().users().findByUsername(user).thenCompose(result -> {
            FriendshipsFeedsRequest request = new FriendshipsFeedsRequest(result.getUser().getPk(), FriendshipsFeedsRequest.FriendshipsFeeds.FOLLOWERS);
            return request.execute(client).thenApply(res -> {
                List<InsFollowers> followers = new ArrayList<>();
                res.getUsers().stream().map(f -> new InsFollowers(user, f.getUsername(), f.getPk())).forEach(followers::add);
                return followers;
            });
        }).exceptionally(e -> {
            log.error("query followers exception, user: {} ", user, e);
            return new ArrayList<>();
        });
    }

    private void saveToMysql(List<InsFollowers> followers) {
        if (!CollectionUtils.isEmpty(followers)) {
            followersMapper.batchInsert(followers);
        }
    }
}
