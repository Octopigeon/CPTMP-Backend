package io.github.octopigeon.cptmpweb.controller;

import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in 魏啸冲
 * @date 2020/7/10
 */
@RestController
@ResponseBody
public class LoginController {

    /**
     * 用于向前端返回警告用户尚未登录
     * @return 提醒登录消息
     */
    @GetMapping("/api/guard")
    public RespBean login() {
        return RespBean.error(CptmpStatusCode.ACCESS_DENY_NOT_LOGIN, "not login");
    }

    /**
     * 用于测试，发布时可以去掉
     * @return 登录成功消息
     */
    @Deprecated
    @GetMapping("/api/access")
    public RespBean hello() {
        return RespBean.ok("access successfully");
    }

}
