package io.github.octopigeon.cptmpweb.controller;

import io.github.octopigeon.cptmpservice.CptmpStatusCode;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in anlow
 * @date 2020/7/10
 */
@RestController
@ResponseBody
public class LoginController {

    @GetMapping("/api/guard")
    public RespBean login() {
        return RespBean.error(CptmpStatusCode.ACCESS_DENY_NOT_LOGIN, "not login");
    }
    @GetMapping("/api/access")
    public RespBean hello() {
        return RespBean.ok("access successfully");
    }

}
