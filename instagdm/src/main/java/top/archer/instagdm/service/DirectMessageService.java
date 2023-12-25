package top.archer.instagdm.service;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.requests.direct.DirectThreadsBroadcastRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import io.micrometer.common.util.StringUtils;
import top.archer.instagdm.dto.DirectMessageDTO;
import top.archer.instagdm.result.Result;
import top.archer.instagdm.result.ResultBuilder;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DirectMessageService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private InsApiService apiService;

    public Result<String> sendDirectMessage(DirectMessageDTO messageDTO) {
        Result<String> checkResult = checkDTO(messageDTO);
        if (checkResult.fail()) {
            return checkResult;
        }
        Optional<IGClient> opClient = loginService.login(messageDTO.getUsername(), messageDTO.getPassword());
        if (opClient.isEmpty()) {
            log.error("[requests] sendDirectMessage failed: 登录失败");
            return ResultBuilder.fail("登录Ins失败, 请检查用户名密码是否正确");
        }
        if (!CollectionUtils.isEmpty(messageDTO.getReceivers())) {
            sendMessageToUsers(opClient.get(), messageDTO.getReceivers(), messageDTO.getMessage());
        }
        if (!CollectionUtils.isEmpty(messageDTO.getThreadIds())) {
            sendMessageToThreads(opClient.get(), messageDTO.getThreadIds(), messageDTO.getMessage());
        }
        return ResultBuilder.success("群发消息任务已提交, 请检查Ins账户是否执行完成");
    }

    private void sendMessageToUsers(IGClient client, List<String> receivers, String message) {
        apiService.queryUserIds(client, receivers).thenAccept(userIds -> {
            client.sendRequest(new DirectThreadsBroadcastRequest(new DirectThreadsBroadcastRequest.BroadcastTextPayload(
                    message, userIds.stream().mapToLong(t -> t).toArray()
            ))).thenAccept(r -> log.info("[requests] sendMessageToUsers status: {}, message:{}", r.getStatus(), message));
        });
    }

    private void sendMessageToThreads(IGClient client, List<String> threadIds, String message) {
        client.sendRequest(new DirectThreadsBroadcastRequest(new DirectThreadsBroadcastRequest.BroadcastTextPayload(
                message, threadIds.toArray(new String[0])
        ))).thenAccept(r -> log.info("[requests] sendMessageToThreads status: {}, message:{}", r.getStatus(), message));
    }

    private static Result<String> checkDTO(DirectMessageDTO messageDTO) {
        if (CollectionUtils.isEmpty(messageDTO.getReceivers()) && CollectionUtils.isEmpty(messageDTO.getThreadIds())) {
            return ResultBuilder.fail("群发接收人/接收群不能都为空");
        }
        if (StringUtils.isBlank(messageDTO.getMessage())) {
            return ResultBuilder.fail("群发消息不能为空");
        }
        return ResultBuilder.success();
    }
}
