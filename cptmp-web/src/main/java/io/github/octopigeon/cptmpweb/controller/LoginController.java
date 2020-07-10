package io.github.octopigeon.cptmpweb.controller;

import io.github.octopigeon.cptmpservice.CptmpStatusCode;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import io.github.octopigeon.cptmpweb.bean.response.RespBeanWithObj;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/api/guard")
    public RespBean login() {
        return RespBean.error(CptmpStatusCode.ACCESS_DENY_NOT_LOGIN, "not login");
    }
    @GetMapping("/api/access")
    public RespBean hello() {
        return RespBean.ok("access successfully");
    }
    @GetMapping("/api/me")
    public RespBeanWithObj<Authentication> getProfile() {
        RespBeanWithObj<Authentication> authenticationRespBeanWithObj = new RespBeanWithObj<>();
        BeanUtils.copyProperties(RespBean.ok(CptmpStatusCode.OK, "get profile success"), authenticationRespBeanWithObj);
        authenticationRespBeanWithObj.setObj(SecurityContextHolder.getContext().getAuthentication());
        return authenticationRespBeanWithObj;
    }

}
