package io.github.octopigeon.cptmpweb.controller;

import io.github.octopigeon.cptmpservice.CptmpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in anlow
 * @date 2020/7/8
 */
@RestController
@ResponseBody
public class LoginController {

    @GetMapping("/doLogin")
    public String doGetLogin() {
        return "{ status_code: " + CptmpStatusCode.ACCESS_DENY_NOT_LOGIN + " }";
    }

    @GetMapping("/test")
    public String doGetTest() {
        return "{ status_code: " + CptmpStatusCode.OK + " }";
    }

}
