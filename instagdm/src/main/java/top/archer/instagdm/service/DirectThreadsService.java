package top.archer.instagdm.service;

import com.alibaba.fastjson.JSON;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.requests.direct.DirectThreadsBroadcastRequest;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.archer.instagdm.dto.ThreadsCreateGroupDTO;
import top.archer.instagdm.mapper.ThreadsMapper;
import top.archer.instagdm.model.Threads;
import top.archer.instagdm.request.DirectCreateGroupThreadRequest;
import top.archer.instagdm.result.Result;
import top.archer.instagdm.result.ResultBuilder;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DirectThreadsService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private InsApiService apiService;

    @Autowired
    private ThreadsMapper threadsMapper;

    public Result<String> createThreadsGroup(ThreadsCreateGroupDTO groupDTO) {
        String username = groupDTO.getUsername();
        String password = groupDTO.getPassword();
        Result<String> checkResult = checkDTO(groupDTO);
        if (checkResult.fail()) {
            return checkResult;
        }
        Optional<IGClient> opClient = loginService.login(username, password);
        if (opClient.isEmpty()) {
            log.error("[requests] createThreadsGroup failed: 登录失败");
            return ResultBuilder.fail("登录Ins失败, 请检查用户名密码是否正确!");
        }
        String title = groupDTO.getTitle();
        apiService.queryUserIds(opClient.get(), groupDTO.getInviters()).thenAccept(userIds -> {
            String[] users = userIds.stream().map(String::valueOf).toList().toArray(new String[0]);
            opClient.get().sendRequest(new DirectCreateGroupThreadRequest(title, users)).thenAccept(r -> {
                opClient.get().sendRequest(new DirectThreadsBroadcastRequest(new DirectThreadsBroadcastRequest.BroadcastTextPayload(
                        "welcome everybody!", r.getThread_v2_id())
                )).thenAccept(rr -> {
                    log.info("[requests] createThreadsGroup status: {}, message:{}", rr.getStatus(), rr.getMessage());
                    if (rr.getStatus().equals("ok")) {
                        Threads threads = new Threads();
                        threads.setThreadId(String.valueOf(r.getExtraProperties().get("messaging_thread_key")));
                        threads.setThreadTitle(r.getThread_title());
                        threads.setCreatorUsername(username);
                        threads.setInvitationLink(String.valueOf(r.getExtraProperties().get("joinable_group_link")));
                        threadsMapper.insert(threads);
                    }
                });
            });
        });
        return ResultBuilder.success("创建群组任务已提交, 请检查Ins账户是否执行完成");
    }

    public List<Threads> listThreads() {
        return threadsMapper.queryAll();
    }

    private Result<String> checkDTO(ThreadsCreateGroupDTO groupDTO) {
        if (StringUtils.isBlank(groupDTO.getTitle())) {
            return ResultBuilder.fail("群组名称不能为空");
        }
        if (CollectionUtils.isEmpty(groupDTO.getInviters())) {
            return ResultBuilder.fail("群组成员不能为空");
        }
        return ResultBuilder.success();
    }
}
