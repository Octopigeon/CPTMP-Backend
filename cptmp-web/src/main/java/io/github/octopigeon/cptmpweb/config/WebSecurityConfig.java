package io.github.octopigeon.cptmpweb.config;

import io.github.octopigeon.cptmpservice.service.CptmpUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
 * @author anlow
 * @version 1.0
 * @date 2020/7/8
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CptmpAuthenticationSuccessHandlerImpl cptmpAuthenticationSuccessHandler;

    @Autowired
    private CptmpAuthenticationFailureHandlerImpl cptmpAuthenticationFailureHandler;

    @Autowired
    private CptmpUserDetailsServiceImpl cptmpUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        return new CptmpUserDetailsServiceImpl();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/home").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .successHandler(cptmpAuthenticationSuccessHandler)
//                .failureHandler(cptmpAuthenticationFailureHandler)
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();

        // 配置filter
        CptmpLoginAuthenticationFilter cptmpLoginAuthenticationFilter = new CptmpLoginAuthenticationFilter("/login", HttpMethod.POST.name());
        cptmpLoginAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        cptmpLoginAuthenticationFilter.setAuthenticationSuccessHandler(cptmpAuthenticationSuccessHandler);
        cptmpLoginAuthenticationFilter.setAuthenticationFailureHandler(cptmpAuthenticationFailureHandler);
        // 配置provider
        CptmpLoginAuthenticationProvider cptmpLoginAuthenticationProvider = new CptmpLoginAuthenticationProvider();
        cptmpLoginAuthenticationProvider.setCptmpUserDetailsService(cptmpUserDetailsService);

        http.authenticationProvider(cptmpLoginAuthenticationProvider).addFilterAfter(cptmpLoginAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//    }

}
