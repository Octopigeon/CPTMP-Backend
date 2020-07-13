package io.github.octopigeon.cptmpweb.config;

import io.github.octopigeon.cptmpservice.service.otherservice.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/8
 * 用于网站的完全配置
 * @last-check-in 魏啸冲
 * @date 2020/7/9
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /** 自定义登录验证失败处理器 */
    @Autowired
    private AuthenticationFailureHandlerImpl authenticationFailureHandler;

    /** 自定义登录成功处理器 */
    @Autowired
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /** 加在自定义用户验证机制以及加密方法 */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/user/student/invite")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/api/login")
                .permitAll()  // 没有permitAll()会导致重定向死循环
                .loginPage("/api/guard")  // 当用于未登录并尝试访问网站内部时，将跳转到此位置，返回一段提醒登录的信息
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/api/logout")  // 登出api
                .permitAll()
                .and().csrf().disable();
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 将自定义用户验证过滤器注册进bean
     * @return 一个配置了自定义登录成功/失败处理器，并注册到认证管理器中
     * 的滴定仪验证过滤器
     */
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}