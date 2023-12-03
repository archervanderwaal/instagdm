package top.archer.instagdm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.archer.instagdm.dto.DirectMessageDTO;
import top.archer.instagdm.dto.ThreadsCreateGroupDTO;
import top.archer.instagdm.result.Result;
import top.archer.instagdm.service.DirectMessageService;
import top.archer.instagdm.service.DirectThreadsService;

@CrossOrigin
@RestController
@RequestMapping(value = "/ins")
public class InstagdmController {

    @Autowired
    private DirectMessageService messageService;

    @Autowired
    private DirectThreadsService threadsService;

    @PostMapping(value = "/direct/message/send")
    @ResponseBody
    public Result<String> sendDirectMessage(@RequestBody DirectMessageDTO messageDTO) {
        return messageService.sendDirectMessage(messageDTO);
    }

    @PostMapping(value = "/threads/create")
    @ResponseBody
    public Result<String> createThreadsGroup(@RequestBody ThreadsCreateGroupDTO dto) {
        return threadsService.createThreadsGroup(dto);
    }
}
