package io.github.octopigeon.cptmpweb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/8
 * 自定义登录失败处理器
 * @last-check-in 魏啸冲
 * @date 2020/7/9
 */
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        RespBean respBean;
        // 捕获不同的登录异常，并返回相应的状态码
        if (e instanceof BadCredentialsException) {
            respBean = RespBean.error(CptmpStatusCode.AUTH_FAILED_BAD_CREDENTIALS, "authenticate failed, bad credentials, wrong password or username(best guess)");
        } else if (e instanceof RememberMeAuthenticationException) {
            respBean = RespBean.error(CptmpStatusCode.AUTH_FAILED_REMEMBER_ME_ERROR, "authenticate failed, remember me expired(best guess)");
        } else if (e instanceof CredentialsExpiredException) {
            respBean = RespBean.error(CptmpStatusCode.AUTH_FAILED_CREDENTIALS_EXPIRED, "authenticate failed, credentials expired");
        } else if (e instanceof AccountExpiredException) {
            respBean = RespBean.error(CptmpStatusCode.AUTH_FAILED_ACCOUNT_EXPIRED, "authenticate failed, account expired");
        } else if (e instanceof AccountStatusException) {
            respBean = RespBean.error(CptmpStatusCode.AUTH_FAILED_ACCOUNT_STATUS_ERROR, "authenticate failed, account status error, not enabled(best guess)");
        } else {
            respBean = RespBean.error(CptmpStatusCode.AUTH_FAILED_UNKNOWN_ERROR, "authenticate failed, unknown error");
        }
        PrintWriter out = httpServletResponse.getWriter();
        out.write(new ObjectMapper().writeValueAsString(respBean));
        out.flush();
        out.close();
    }
}
