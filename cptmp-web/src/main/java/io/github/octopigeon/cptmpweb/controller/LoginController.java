package io.github.octopigeon.cptmpweb.controller;

import io.github.octopigeon.cptmpservice.CptmpStatusCode;
import io.github.octopigeon.cptmpweb.bean.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in anlow
 * @date 2020/7/9
 */
@RestController
@ResponseBody
public class LoginController {

    @GetMapping("/guard")
    public RespBean login() {
        return RespBean.error("尚未登录，请登录");
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
