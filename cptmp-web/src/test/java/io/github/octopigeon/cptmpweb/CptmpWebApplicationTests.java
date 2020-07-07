package io.github.octopigeon.cptmpweb;

import io.github.octopigeon.cptmpdao.mapper.UserMapper;
import io.github.octopigeon.cptmpdao.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CptmpWebApplicationTests {

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
