package io.github.octopigeon.cptmpweb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/7
 * 自定义登录成功验证
 * @last-check-in 魏啸冲
 * @date 2020/7/9
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter out = httpServletResponse.getWriter();
        // 状态为ok，返回一段成功消息
        out.write(new ObjectMapper().writeValueAsString(RespBean.ok("login successfully")));
        out.flush();
        out.close();
    }
}
