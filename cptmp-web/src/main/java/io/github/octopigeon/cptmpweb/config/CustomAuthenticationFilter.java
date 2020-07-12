package io.github.octopigeon.cptmpweb.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.octopigeon.cptmpweb.bean.request.AuthenticationBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/9
 * 自定义用于验证过滤器，用于拦截前端发来的登录请求，
 * 由于默认的过滤器采用参数传递用户名和密码的方式，所以
 * 需要重写认证方法，采用接收包含username和password的
 * json的方式
 * @last-check-in 魏啸冲
 * @date 2020/7/10
 */
@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected CustomAuthenticationFilter() {
        super(new RegexRequestMatcher("/api/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("Attempt authenticate...");
        String t = request.getContentType();
        // 处理内容为json的body
        if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            // 解析json
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()) {
                AuthenticationBean authenticationBean = mapper.readerFor(AuthenticationBean.class).readValue(is);
                // 生成一个包含json中的用户名和密码的token，交给后面的认证处理器比对密码
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.getUsername(), authenticationBean.getPassword());

                // 原有方法
                setDetails(request, authRequest);
                // authenticate方法用于对用户名和密码与数据库中的数据比对，进行验证，并将验证结果返回
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");

                // 原有方法
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } else {
            return attemptUsernamePasswordAuthentication(request, response);
        }
    }

    // 一下均为从UsernamePasswordAuthentication中直接copy过来的部分

    /**
     * 原生的UsernamePasswordAuthentication中的验证方法
     */
    public Authentication attemptUsernamePasswordAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        boolean postOnly = true;
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String username = this.obtainUsername(request);
            String password = this.obtainPassword(request);
            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        String passwordParameter = "password";
        return request.getParameter(passwordParameter);
    }

    @Nullable
    protected String obtainUsername(HttpServletRequest request) {
        String usernameParameter = "username";
        return request.getParameter(usernameParameter);
    }

}