package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.mapper.OrganizationMapper;
import io.github.octopigeon.cptmpdao.mapper.PasswordResetTokenMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.Organization;
import io.github.octopigeon.cptmpdao.model.PasswordResetToken;
import io.github.octopigeon.cptmpservice.utils.Utils;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/9
 * @last-check-in 李国鹏
 * @date 2020/7/19
 */
public class PasswordResetTokenMapperTest extends BaseTest {

    @Autowired
    private PasswordResetTokenMapper passwordResetTokenMapper;

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

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

        //组织
        organizationMapper.removeAllOrganizationTest();
        Organization organization = new Organization();
        organization.setGmtCreate(new Date());
        organization.setRealName("test1");
        organization.setName("test1");
        organizationMapper.addOrganization(organization);
        organization.setRealName("test2");
        organization.setName("test2");
        organizationMapper.addOrganization(organization);
        Assertions.assertEquals(2,organizationMapper.findAllOrganization().size());
        Assertions.assertEquals(5, Utils.getNullPropertyNames(organizationMapper.findAllOrganization().get(0)).length);

        // 创建用户
        CptmpUser cptmpUser = new CptmpUser();
        cptmpUser.setGmtCreate(new Date());
        cptmpUser.setUsername("test1");
        cptmpUser.updatePassword("123456");
        cptmpUser.setEmail(userEmail);
        cptmpUser.setRoleName("ROLE_SCHOOL_TEACHER");
        cptmpUser.setCommonId("124124");
        cptmpUser.setName("wxc");
        cptmpUser.setOrganizationId(organization.getId());
        cptmpUser.setEnabled(true);
        cptmpUser.setAccountNonExpired(true);
        cptmpUser.setCredentialsNonExpired(true);
        cptmpUser.setAccountNonLocked(true);
        cptmpUserMapper.removeAllUsersTest();
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

        Assertions.assertEquals(1,passwordResetTokenMapper.findAllPasswordResetTokens().size());
        Assertions.assertEquals(userEmail,passwordResetTokenMapper.findPasswordResetTokenByToken(token).getEmail());
        passwordResetTokenMapper.removePasswordResetTokensByEmail(userEmail);
        Assertions.assertEquals(0,passwordResetTokenMapper.findAllPasswordResetTokens().size());


    }

}
