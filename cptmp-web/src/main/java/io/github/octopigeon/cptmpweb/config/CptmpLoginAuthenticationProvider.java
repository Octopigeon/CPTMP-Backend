package io.github.octopigeon.cptmpweb.config;

import io.github.octopigeon.cptmpservice.service.CptmpUserDetailsServiceImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in anlow
 * @date 2020/7/8
 */
@Slf4j
public class CptmpLoginAuthenticationProvider implements AuthenticationProvider {

    @Getter
    @Setter
    private CptmpUserDetailsServiceImpl cptmpUserDetailsService;

    public CptmpLoginAuthenticationProvider() {
        log.info("Cptmp login-auth-provider loading...");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 拿到过滤器生成的token，准备进行认证
        CptmpLoginAuthenticationToken cptmpLoginAuthenticationToken = (CptmpLoginAuthenticationToken) authentication;
        UserDetails userDetails = cptmpUserDetailsService.loadUserByUsername((String) authentication.getPrincipal());
        // 认证失败
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("Authenticated failed, unreachable user info");
        }
        // 认证通过
        CptmpLoginAuthenticationToken cptmpLoginAuthenticationResult = new CptmpLoginAuthenticationToken(userDetails, userDetails.getAuthorities());
        cptmpLoginAuthenticationResult.setDetails(cptmpLoginAuthenticationToken.getDetails());

        return cptmpLoginAuthenticationResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CptmpLoginAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
