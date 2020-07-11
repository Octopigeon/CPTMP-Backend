package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.mapper.PasswordResetTokenMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.PasswordResetToken;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/9
 * @last-check-in anlow
 * @date 2020/7/9
 */
public class PasswordResetTokenMapperTest extends BaseTest {

    @Autowired
    private PasswordResetTokenMapper passwordResetTokenMapper;

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Test
    public void test() {
        // 创建密码重置token
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setGmtCreate(new Date());
        // 设置UUID的Token
        String token = UUID.randomUUID().toString();
        String userEmail = "123@qq.com";
        passwordResetToken.setToken(token);
        passwordResetToken.setEmail(userEmail);

        // 创建用户
        CptmpUser cptmpUser = new CptmpUser();
        cptmpUser.setGmtCreate(new Date());
        cptmpUser.setUsername("test1");
        cptmpUser.updatePassword("123456");
        cptmpUser.setEmail(userEmail);
        cptmpUser.setRoleName("ROLE_SCHOOL_TEACHER");
        cptmpUser.setNickname("test");
        cptmpUser.setEnabled(true);
        cptmpUser.setAccountNonExpired(true);
        cptmpUser.setCredentialsNonExpired(true);
        cptmpUser.setAccountNonLocked(true);
        cptmpUserMapper.removeAllUsers();
        cptmpUserMapper.addUser(cptmpUser);

        passwordResetTokenMapper.removeAllPasswordResetTokens();
        Assertions.assertEquals(0, passwordResetTokenMapper.findAllPasswordResetTokens().size());
        passwordResetTokenMapper.addPasswordResetToken(passwordResetToken);
        passwordResetTokenMapper.updateTokenByEmail(token, userEmail);
        List<PasswordResetToken> passwordResetTokens = passwordResetTokenMapper.findAllPasswordResetTokens();
        Assertions.assertEquals(1, passwordResetTokens.size());
        Assertions.assertNotNull(passwordResetTokens.get(0).getId());
        Assertions.assertNotNull(passwordResetTokens.get(0).getEmail());
        Assertions.assertNotNull(passwordResetTokens.get(0).getGmtCreate());
        Assertions.assertNull(passwordResetTokens.get(0).getGmtModified());
        Assertions.assertNotNull(passwordResetTokens.get(0).getToken());
    }

}
