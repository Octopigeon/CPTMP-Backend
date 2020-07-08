package io.github.octopigeon.cptmpweb.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in anlow
 * @date 2020/7/8
 */
@Slf4j
public class CptmpLoginAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    public CptmpLoginAuthenticationToken(String username) {
        super(null);
        this.principal = username;
        this.setAuthenticated(false);
        log.info("Unauthenticated cptmp login-auth-token generated...");
    }

    public CptmpLoginAuthenticationToken(Object principal,
                                         Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
        log.info("Cptmp login-auth-token has been authenticated...");
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        if (authenticated) {
            throw new IllegalArgumentException();
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
