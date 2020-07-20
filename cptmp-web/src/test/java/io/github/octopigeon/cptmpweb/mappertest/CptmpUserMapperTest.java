package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.mapper.OrganizationMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpservice.constantclass.CptmpRole;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.organization.OrganizationDTO;
import io.github.octopigeon.cptmpservice.service.organization.OrganizationService;
import io.github.octopigeon.cptmpservice.service.userinfo.UserInfoService;
import io.github.octopigeon.cptmpservice.utils.Utils;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author 魏啸冲
 * @version 2.0
 * @date 2020/7/8
 *
 * @last-check-in 李国鹏
 * @date 2020/7/19
 */
public class CptmpUserMapperTest extends BaseTest {


    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Test
    public void test() throws Exception {
        cptmpUserMapper.removeAllUsersTest();
        organizationMapper.removeAllOrganizationTest();

        // 创建学校
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setName("WHU");
        organizationDTO.setRealName("武汉大学");
        organizationDTO.setDescription("湖北省武汉市武汉大学");
        organizationDTO.setWebsiteUrl("www.whu.edu.cn");
        organizationService.add(organizationDTO);
        organizationDTO = organizationService.findByName(1,1,"WHU").getList().get(0);

        // 创建用户
        BaseUserInfoDTO baseUserInfoDTO = new BaseUserInfoDTO();
        baseUserInfoDTO.setUsername("WHU-2018302060342");
        baseUserInfoDTO.setCommonId("2018302060342");
        baseUserInfoDTO.setRoleName(CptmpRole.ROLE_SCHOOL_TEACHER);
        baseUserInfoDTO.setName("魏啸冲");
        baseUserInfoDTO.setPassword("123");
        baseUserInfoDTO.setEmail("wxcnb@qq.com");
        baseUserInfoDTO.setOrganizationId(organizationDTO.getId());
        userInfoService.add(baseUserInfoDTO);
        CptmpUser cptmpUser = cptmpUserMapper.findUserByUsername("WHU-2018302060342");
        Assertions.assertEquals(5, Utils.getNullPropertyNames(cptmpUser).length);

        cptmpUserMapper.hideAllUsers(new Date());
        List<CptmpUser> cptmpUsers = cptmpUserMapper.findAllUsers();
        Assertions.assertEquals(0, cptmpUsers.size());
        cptmpUserMapper.restoreAllUsers();
        cptmpUsers = cptmpUserMapper.findAllUsers();
        cptmpUserMapper.removeAllUsers(new Date());
        cptmpUsers = cptmpUserMapper.findAllUsers();
        cptmpUserMapper.restoreUserByUsername(cptmpUser.getUsername());
        cptmpUsers = cptmpUserMapper.findAllUsers();
        CptmpUser cptmpUser1 = cptmpUserMapper.findAllUsers().get(0);
        Assertions.assertEquals(7, Utils.getNullPropertyNames(cptmpUser1).length);
        cptmpUserMapper.removeAllUsersTest();
        cptmpUserMapper.addUser(cptmpUser);
        cptmpUser = cptmpUserMapper.findAllUsers().get(0);
        cptmpUserMapper.hideUserById(cptmpUser.getId(), new Date());
        Assertions.assertEquals(0, cptmpUserMapper.findAllUsers().size());
        cptmpUserMapper.restoreUserById(cptmpUser.getId());
        cptmpUser1 = cptmpUserMapper.findUserById(cptmpUser.getId());
        Assertions.assertEquals(5, Utils.getNullPropertyNames(cptmpUser1).length);
        cptmpUser1 = cptmpUserMapper.findUserByUsername(cptmpUser.getUsername());
        Assertions.assertEquals(5, Utils.getNullPropertyNames(cptmpUser1).length);
        cptmpUser1 = cptmpUserMapper.findUserByEmail(cptmpUser.getEmail());
        Assertions.assertEquals(5, Utils.getNullPropertyNames(cptmpUser1).length);
        cptmpUser.setName("魏冲冲");
        cptmpUser.setUsername("WHU-123");
        cptmpUser.setEmail("312321@qq.com");
        cptmpUserMapper.addUser(cptmpUser);
        cptmpUsers = cptmpUserMapper.findUsersByName("冲");
        Assertions.assertEquals(2, cptmpUsers.size());
        cptmpUsers = cptmpUserMapper.findUsersByName("魏冲");
        Assertions.assertEquals(1, cptmpUsers.size());
        cptmpUsers = cptmpUserMapper.findUsersByGroupFilter(cptmpUser.getOrganizationId(), cptmpUser.getRoleName());
        Assertions.assertEquals(2, cptmpUsers.size());
        cptmpUser.setName("李国鹏");
        cptmpUserMapper.updateUserById(cptmpUser);
        Assertions.assertEquals(1, cptmpUserMapper.findUsersByName("李").size());
        cptmpUser.setName("童源");
        cptmpUserMapper.updateUserByUserName(cptmpUser);
        Assertions.assertEquals(1, cptmpUserMapper.findUsersByName("童").size());
        cptmpUserMapper.updateEnabledByUsername(cptmpUser.getUsername(), false);
        cptmpUser1 = cptmpUserMapper.findAllUsers().get(1);
        Assertions.assertEquals(false, cptmpUser1.getEnabled());
    }
}
