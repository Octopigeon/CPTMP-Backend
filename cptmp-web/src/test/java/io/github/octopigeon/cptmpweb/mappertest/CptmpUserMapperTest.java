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

/**
 * @author 魏啸冲
 * @version 2.0
 * @date 2020/7/8
 *
 * @last-check-in 魏啸冲
 * @date 2020/7/12
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
        organizationDTO = organizationService.findByName("WHU");

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

    }
}
