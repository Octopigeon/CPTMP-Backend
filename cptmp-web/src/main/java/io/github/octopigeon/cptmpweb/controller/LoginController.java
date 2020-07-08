package io.github.octopigeon.cptmpweb.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in anlow
 * @date 2020/7/8
 */
@RestController
public class LoginController {

    /**
     * 处理登录请求，返回体由登陆成功/失败处理器提交
     * @param json 前端提交的用户填写的登录信息
     */
    @PostMapping("/login")
    public void doPostLogin(@RequestParam(name = "username") String username,
                            @RequestParam(name = "password") String password) {

    }

}
