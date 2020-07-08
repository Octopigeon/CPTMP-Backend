package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/8
 */
public class CptmpUserMapperTest extends BaseTest {

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Test
    public void test() {
        CptmpUser cptmpUser1 = new CptmpUser();
        cptmpUser1.setGmtCreate(new Date());
        cptmpUser1.setUsername("test1");
        cptmpUser1.setPassword("123456");
        cptmpUser1.setEmail("111@11.com");
        cptmpUser1.setRoleName("admin");
        cptmpUser1.setEnabled(true);
        cptmpUser1.setAccountNonExpired(true);
        cptmpUser1.setCredentialsNonExpired(true);
        cptmpUser1.setAccountNonLocked(true);
        CptmpUser cptmpUser2 = new CptmpUser();
        cptmpUser2.setGmtCreate(new Date());
        cptmpUser2.setUsername("test2");
        cptmpUser2.setPassword("123456");
        cptmpUser2.setEmail("111@11.com");
        cptmpUser2.setRoleName("admin");
        cptmpUser2.setEnabled(true);
        cptmpUser2.setAccountNonExpired(true);
        cptmpUser2.setCredentialsNonExpired(true);
        cptmpUser2.setAccountNonLocked(true);

        cptmpUserMapper.removeAllUsers();
        cptmpUserMapper.addUser(cptmpUser1);
        cptmpUserMapper.addUser(cptmpUser2);
        List<CptmpUser> cptmpUsers = cptmpUserMapper.findAllUsers();
        Assertions.assertEquals(2, cptmpUsers.size());
        cptmpUserMapper.removeAllUsers();
        cptmpUsers = cptmpUserMapper.findAllUsers();
        Assertions.assertEquals(0, cptmpUsers.size());

    }

}
