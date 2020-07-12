package io.github.octopigeon.cptmpweb.servicetest;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.mapper.PasswordResetTokenMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.PasswordResetToken;
import io.github.octopigeon.cptmpservice.service.passwordtoken.PasswordResetService;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in 魏啸冲
 * @date 2020/7/10
 */
public class PasswordResetServiceTest extends BaseTest {

    @Autowired
    private PasswordResetTokenMapper passwordResetTokenMapper;

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Autowired
    private PasswordResetService passwordResetService;

    @Test
    public void test() {
        String userEmail = "123@qq.com";
        // 创建用户
        CptmpUser cptmpUser = new CptmpUser();
        cptmpUser.setGmtCreate(new Date());
        cptmpUser.setUsername("test1");
        cptmpUser.updatePassword("123456");
        cptmpUser.setEmail(userEmail);
        cptmpUser.setRoleName("ROLE_SCHOOL_TEACHER");
        cptmpUser.setEnabled(true);
        cptmpUser.setAccountNonExpired(true);
        cptmpUser.setCredentialsNonExpired(true);
        cptmpUser.setAccountNonLocked(true);
        cptmpUserMapper.removeAllUsersTest();
        cptmpUserMapper.addUser(cptmpUser);

        passwordResetTokenMapper.removeAllPasswordResetTokens();
        passwordResetService.createPasswordResetTokenForUser(userEmail);
        List<PasswordResetToken> passwordResetTokens = passwordResetTokenMapper.findAllPasswordResetTokens();
        Assertions.assertEquals(1, passwordResetTokenMapper.findAllPasswordResetTokens().size());
        String token = passwordResetTokenMapper.findAllPasswordResetTokens().get(0).getToken();
        Assertions.assertFalse(passwordResetService.authToken(token, userEmail));
        Assertions.assertEquals(0, passwordResetTokenMapper.findAllPasswordResetTokens().size());



    }

}
