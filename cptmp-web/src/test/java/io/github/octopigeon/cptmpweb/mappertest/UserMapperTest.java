package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.UserMapper;
import io.github.octopigeon.cptmpdao.model.User;
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
public class UserMapperTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        User user1 = new User();
        user1.setGmtCreate(new Date());
        user1.setUsername("test1");
        user1.setPassword("123456");
        User user2 = new User();
        user2.setGmtCreate(new Date());
        user2.setUsername("test2");
        user2.setPassword("123456");

        userMapper.removeAllUsers();
        userMapper.addUser(user1);
        userMapper.addUser(user2);
        List<User> users = userMapper.findAllUsers();
        Assertions.assertEquals(2, users.size());
        userMapper.removeAllUsers();
        users = userMapper.findAllUsers();
        Assertions.assertEquals(0, users.size());

    }

}
