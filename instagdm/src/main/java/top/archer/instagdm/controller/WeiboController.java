package top.archer.instagdm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.archer.instagdm.result.Result;
import top.archer.instagdm.result.ResultBuilder;
import top.archer.instagdm.task.WeiboTask;

@CrossOrigin
@RestController
@RequestMapping(value = "/weibo")
public class WeiboController {

    @Autowired
    private WeiboTask weiboTask;

    @GetMapping(value = "/task")
    public Result<String> startTask() {
        weiboTask.startTask();
        return ResultBuilder.success("提交成功");
    }
}