package top.archer.instagdm.service;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.requests.direct.DirectThreadsBroadcastRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.archer.instagdm.dto.ThreadsCreateGroupDTO;
import top.archer.instagdm.request.DirectCreateGroupThreadRequest;
import top.archer.instagdm.result.Result;
import top.archer.instagdm.result.ResultBuilder;

import java.util.Optional;

@Service
public class DirectThreadsService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private InsApiService apiService;

    public Result<String> createThreadsGroup(ThreadsCreateGroupDTO groupDTO) {
        String username = groupDTO.getUsername();
        String password = groupDTO.getPassword();
        Result<String> checkResult = checkDTO(groupDTO);
        if (checkResult.fail()) {
            return checkResult;
        }
        Optional<IGClient> opClient = loginService.login(username, password);
        if (opClient.isEmpty()) {
            return ResultBuilder.fail("登录Ins失败, 请检查用户名密码是否正确!");
        }
        String title = groupDTO.getTitle();
        apiService.queryUserIds(opClient.get(), groupDTO.getInviters()).thenAccept(userIds -> {
            String[] users = userIds.stream().map(String::valueOf).toList().toArray(new String[0]);
            opClient.get().sendRequest(new DirectCreateGroupThreadRequest(title, users)).thenAccept(r -> {
                opClient.get().sendRequest(new DirectThreadsBroadcastRequest(new DirectThreadsBroadcastRequest.BroadcastTextPayload(
                        "welcome everybody!", r.getThread_v2_id())
                ));
            });
        });
        return ResultBuilder.success("创建群组任务已提交, 请检查Ins账户是否执行完成");
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
