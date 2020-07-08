package io.github.octopigeon.cptmpweb.servicetest;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in anlow
 * @date 2020/7/8
 */
public class CptmpUserDetailsServiceTest extends BaseTest {

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Test
    public void test() {
        CptmpUser cptmpUser = new CptmpUser();
        cptmpUser.setGmtCreate(new Date());
        cptmpUser.setUsername("test1");
        cptmpUser.setPassword("123456");
        cptmpUser.setEmail("111@11.com");
        cptmpUser.setRoleName("admin");
        cptmpUser.setEnabled(true);
        cptmpUser.setAccountNonExpired(true);
        cptmpUser.setCredentialsNonExpired(true);
        cptmpUser.setAccountNonLocked(true);

        cptmpUserMapper.removeAllUsers();
        cptmpUserMapper.addUser(cptmpUser);
        
    }

}
