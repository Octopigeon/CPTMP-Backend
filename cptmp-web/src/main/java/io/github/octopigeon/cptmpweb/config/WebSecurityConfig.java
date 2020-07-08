package io.github.octopigeon.cptmpweb.config;

import io.github.octopigeon.cptmpservice.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/8
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandlerImpl cptmpAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandlerImpl cptmpAuthenticationFailureHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CptmpUsernamePasswordAuthenticationFilter loginFilter() throws Exception {
        CptmpUsernamePasswordAuthenticationFilter loginFilter = new CptmpUsernamePasswordAuthenticationFilter();
        loginFilter.setAuthenticationSuccessHandler(cptmpAuthenticationSuccessHandler);
        loginFilter.setAuthenticationFailureHandler(cptmpAuthenticationFailureHandler);
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/doLogin");
        return loginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable().exceptionHandling();
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * <code>AuthenticationManager</code>负责整个验证过程所需的东西的配置，
     * <code>userDetail()</code>用于定义<code>AbstractUserDetailsAuthenticationProvider</code>
     * 的<code>retrieveUser</code>方法，其子类<code>DaoAuthenticationProvider</code>中先拿到
     * <code>UserDetails</code>再进行密码比对，这一步就是<code>loadUserByUsername</code>，因此
     * 通过重写该方法，实现与我们自己数据库的对接。这里的<code>auth</code>与上面的认证过滤器
     * 是一起工作的
     *
     * @param auth 认证管理器
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

}
