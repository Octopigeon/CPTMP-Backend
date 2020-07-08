package io.github.octopigeon.cptmpweb.config;

import com.alibaba.fastjson.JSON;
import io.github.octopigeon.cptmpservice.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.LoginInfoDTO;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in anlow
 * @date 2020/7/8
 */
@Component
public class CptmpAuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        LoginInfoDTO loginInfoDTO = new LoginInfoDTO();
        loginInfoDTO.setLoginDate(new Date());
        if (e instanceof UsernameNotFoundException) {
            loginInfoDTO.setStatusCode(CptmpStatusCode.AUTH_FAILED_USERNAME_NOT_FOUND);
        } else if (e instanceof BadCredentialsException) {
            loginInfoDTO.setStatusCode(CptmpStatusCode.AUTH_FAILED_BAD_CREDENTIALS);
        } else if (e instanceof RememberMeAuthenticationException) {
            loginInfoDTO.setStatusCode(CptmpStatusCode.AUTH_FAILED_REMEMBER_ME_ERROR);
        } else if (e instanceof CredentialsExpiredException) {
            loginInfoDTO.setStatusCode(CptmpStatusCode.AUTH_FAILED_CREDENTIALS_EXPIRED);
        } else if (e instanceof AccountExpiredException) {
            loginInfoDTO.setStatusCode(CptmpStatusCode.AUTH_FAILED_ACCOUNT_EXPIRED);
        } else if (e instanceof AccountStatusException) {
            loginInfoDTO.setStatusCode(CptmpStatusCode.AUTH_FAILED_ACCOUNT_STATUS_ERROR);
        } else {
            loginInfoDTO.setStatusCode(CptmpStatusCode.AUTH_FAILED_UNKNOWN_ERROR);
        }
        httpServletResponse.getWriter().write(JSON.toJSONString(loginInfoDTO));
    }
}
