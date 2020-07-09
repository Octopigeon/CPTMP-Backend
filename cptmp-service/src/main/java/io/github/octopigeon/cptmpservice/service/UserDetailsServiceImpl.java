package io.github.octopigeon.cptmpservice.service;

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
 * @author anlow
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in anlow
 * @date 2020/7/9
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Current username: " + username);
        CptmpUser cptmpUser = cptmpUserMapper.findUserByUsername(username);
        Optional<CptmpUser> userOptional = Optional.ofNullable(cptmpUser);
        if (userOptional.isPresent()) {
            logger.info("Current password: " + cptmpUser.getPassword());
            return new User(username,
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
