package io.github.octopigeon.cptmpweb.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.octopigeon.cptmpservice.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.LoginInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/7
 */
@Slf4j
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("Login status: " + CptmpStatusCode.OK);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        LoginInfoDTO loginInfoDTO = new LoginInfoDTO();
        loginInfoDTO.setLoginDate(new Date());
        loginInfoDTO.setStatusCode(CptmpStatusCode.OK);
        PrintWriter out = httpServletResponse.getWriter();
        out.write(JSON.toJSONString(loginInfoDTO));
        out.flush();
        out.close();
    }
}
