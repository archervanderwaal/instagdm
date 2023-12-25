package top.archer.instagdm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.archer.instagdm.dto.DirectMessageDTO;
import top.archer.instagdm.dto.FollowersQueryDTO;
import top.archer.instagdm.dto.ThreadsCreateGroupDTO;
import top.archer.instagdm.model.InsFollowers;
import top.archer.instagdm.model.Threads;
import top.archer.instagdm.result.Result;
import top.archer.instagdm.result.ResultBuilder;
import top.archer.instagdm.service.DirectMessageService;
import top.archer.instagdm.service.DirectThreadsService;
import top.archer.instagdm.service.FollowersService;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/ins")
public class InstagdmController {

    @Autowired
    private DirectMessageService messageService;

    @Autowired
    private DirectThreadsService threadsService;

    @Autowired
    private FollowersService followersService;

    @PostMapping(value = "/direct/message/send")
    @ResponseBody
    public Result<String> sendDirectMessage(@RequestBody DirectMessageDTO messageDTO) {
        log.info("[requests] url: /direct/message/send, dto: {}", messageDTO);
        return messageService.sendDirectMessage(messageDTO);
    }

    @PostMapping(value = "/threads/create")
    @ResponseBody
    public Result<String> createThreadsGroup(@RequestBody ThreadsCreateGroupDTO dto) {
        log.info("[requests] url: /threads/create, dto: {}", dto);
        return threadsService.createThreadsGroup(dto);
    }

    @GetMapping(value = "/followers/list")
    @ResponseBody
    public Result<List<InsFollowers>> listFollowers() {
        return ResultBuilder.success(followersService.listFollowers());
    }

    @GetMapping(value = "/threads/list")
    @ResponseBody
    public Result<List<Threads>> listThreads() {
        return ResultBuilder.success(threadsService.listThreads());
    }

    @PostMapping(value = "/followers/{u}")
    @ResponseBody
    public Result<String> queryFollowers(@PathVariable String u, @RequestBody FollowersQueryDTO dto) {
        log.info("[requests] url: /followers/{}, dto: {}", u, dto);
        return followersService.startQuery(dto, u);
    }
}
