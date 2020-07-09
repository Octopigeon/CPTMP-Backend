package io.github.octopigeon.cptmpweb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.octopigeon.cptmpservice.CptmpStatusCode;
import io.github.octopigeon.cptmpweb.bean.RespBean;
import lombok.extern.slf4j.Slf4j;
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
 * @author anlow
 * @version 1.0
 * @date 2020/7/7
 * @last-check-in anlow
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
        out.write(new ObjectMapper().writeValueAsString(RespBean.ok("login successfully")));
        out.flush();
        out.close();
    }
}
