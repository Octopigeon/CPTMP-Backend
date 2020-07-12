package io.github.octopigeon.cptmpservice.service.otherservice;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in 魏啸冲
 * @date 2020/7/11
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 支持用户名或者邮箱登录
     * @param username 用户名或者邮箱
     * @return 返回UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CptmpUser cptmpUser = cptmpUserMapper.findUserByUsername(username);
        if (cptmpUser == null) {
            cptmpUser = cptmpUserMapper.findUserByEmail(username);
        }
        Optional<CptmpUser> userOptional = Optional.ofNullable(cptmpUser);
        if (userOptional.isPresent()) {
            return new User(cptmpUser.getUsername(),
                    cptmpUser.getPassword(),
                    cptmpUser.getEnabled(),
                    cptmpUser.getAccountNonExpired(),
                    cptmpUser.getCredentialsNonExpired(),
                    cptmpUser.getAccountNonLocked(),
                    AuthorityUtils.commaSeparatedStringToAuthorityList(cptmpUser.getRoleName()));
        } else {
            throw new UsernameNotFoundException("cannot find username:" + username);
        }
    }
}
