package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author 魏啸冲
 * @version 2.0
 * @date 2020/7/8
 *
 * @last-check-in 李国鹏
 * @date 2020/7/12
 */
public class CptmpUserMapperTest extends BaseTest {

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Test
    public void test() {
        /**
         * 设置数据
         */
        CptmpUser cptmpUser1 = new CptmpUser();
        cptmpUser1.setGmtCreate(new Date());
        cptmpUser1.setUsername("test1");
        cptmpUser1.setName("test1");
        cptmpUser1.setCommonId("test1");
        cptmpUser1.setOrganizationId(BigInteger.valueOf(1));
        cptmpUser1.updatePassword("123456");
        cptmpUser1.setEmail("111@11.com");
        cptmpUser1.setNickname("aaa");
        cptmpUser1.setRoleName("ROLE_SCHOOL_TEACHER");
        cptmpUser1.setEnabled(true);
        cptmpUser1.setAccountNonExpired(true);
        cptmpUser1.setCredentialsNonExpired(true);
        cptmpUser1.setAccountNonLocked(true);

        CptmpUser cptmpUser2 = new CptmpUser();
        cptmpUser2.setGmtCreate(new Date());
        cptmpUser2.setUsername("test2");
        cptmpUser2.setName("test2");
        cptmpUser2.setCommonId("test2");
        cptmpUser2.setOrganizationId(BigInteger.valueOf(2));
        cptmpUser2.updatePassword("123456");
        cptmpUser2.setEmail("121@11.com");
        cptmpUser2.setNickname("sfd");
        cptmpUser2.setRoleName("ROLE_SCHOOL_ADMIN");
        cptmpUser2.setEnabled(true);
        cptmpUser2.setAccountNonExpired(true);
        cptmpUser2.setCredentialsNonExpired(true);
        cptmpUser2.setAccountNonLocked(true);

        /**
         * 添加
         */
        cptmpUserMapper.removeAllUsersTest();
        cptmpUserMapper.addUser(cptmpUser1);
        cptmpUserMapper.addUser(cptmpUser2);
//        Assertions.assertEquals(2, cptmpUserMapper.findAllUsers().size());
//
//        cptmpUserMapper.removeUserById(cptmpUserMapper.findAllUsers().get(0).getId(),new Date());
//        Assertions.assertEquals(1, cptmpUserMapper.findAllUsers().size());
//
//        cptmpUserMapper.updateUserByUserName("test2",new Date(),"test3","test2", BigDecimal.valueOf(11),true, "test3","test3","test2","test2","test3","test3",true,
//                true,true,true);
//        Assertions.assertEquals("test3",cptmpUserMapper.findUserByUsername("test2").getIntroduction());

//        cptmpUserMapper.removeAllUsers(new Date());
//        Assertions.assertEquals(0,cptmpUserMapper.findAllUsers().size());
    }
}
