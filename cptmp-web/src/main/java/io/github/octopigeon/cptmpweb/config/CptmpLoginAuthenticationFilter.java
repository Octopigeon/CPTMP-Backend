package io.github.octopigeon.cptmpweb.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in anlow
 * @date 2020/7/8
 */
@Slf4j
public class CptmpLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected CptmpLoginAuthenticationFilter(String webLoginUrl, String httpMethod) {
        super(new AntPathRequestMatcher(webLoginUrl, httpMethod));
        logger.info("Cptmp login-auth-filter loading...");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        // 仅支持POST方法
        if (!httpServletRequest.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Only support POST method");
        }

        // 得到用户名
        String username = obtainUsername(httpServletRequest);
        // 得到密码
        String password = obtainPassword(httpServletRequest);

        // 生成认证Token
        CptmpLoginAuthenticationToken cptmpLoginAuthenticationToken = new CptmpLoginAuthenticationToken(username);

        // 让后面的过滤器完善Token
        cptmpLoginAuthenticationToken.setDetails(authenticationDetailsSource.buildDetails(httpServletRequest));

        return null;
    }

    /**
     * RequestBody为json格式，获取username和password等登录信息
     * @param httpServletRequest request
     * @return password
     */
    private String obtainPassword(HttpServletRequest httpServletRequest) throws IOException {
        StringBuilder json = new StringBuilder();
        String str = "";
        while ((str = httpServletRequest.getReader().readLine()) != null) {
            json.append(str);
        }
        return JSON.parseObject(json.toString()).getString("password");
    }

    /**
     * RequestBody为json格式，获取username和password等登录信息
     * @param httpServletRequest request
     * @return username
     */
    private String obtainUsername(HttpServletRequest httpServletRequest) throws IOException {
        StringBuilder json = new StringBuilder();
        String str = "";
        while ((str = httpServletRequest.getReader().readLine()) != null) {
            json.append(str);
        }
        return JSON.parseObject(json.toString()).getString("username");
    }

}
