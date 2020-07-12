package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.OrganizationMapper;
import io.github.octopigeon.cptmpdao.model.Organization;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author 陈若琳
 * @version 1.0
 * @date 2020/07/12
 * @last-check-in 陈若琳
 * @date 2020/07/12
 */


public class OrganizationMapperTest extends BaseTest {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Test
    public void Test()
    {
        Organization organization1 = new Organization();
        organization1.setName("武汉大学");
        organization1.setGmtCreate(new Date());
        organization1.setDescription("不错");
        organization1.setWebsiteUrl("whu.edu.cn");
        organization1.setInvitationCode("999");
        organizationMapper.addOrganization(organization1);
        
    }



}
