package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.OrganizationMapper;
import io.github.octopigeon.cptmpdao.model.Organization;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author 陈若琳
 * @version 2.0
 * @date 2020/07/12
 * @last-check-in 李国鹏
 * @date 2020/07/12
 */


public class OrganizationMapperTest extends BaseTest {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Test
    public void Test()
    {
        /**
         * 设置数据
         */
        Organization organization1 = new Organization();
        organization1.setName("test1");
        organization1.setRealName("test1");
        organization1.setId(BigInteger.valueOf(1));
        organization1.setGmtCreate(new Date());
        organization1.setDescription("test1");
        organization1.setWebsiteUrl("test1");
        organization1.setInvitationCode("test1");

        Organization organization2 = new Organization();
        organization1.setId(BigInteger.valueOf(2));
        organization2.setName("test2");
        organization2.setRealName("test2");
        organization2.setGmtCreate(new Date());
        organization2.setDescription("test2");
        organization2.setWebsiteUrl("test2");
        organization2.setInvitationCode("test2");

        /**
         * 添加
         */
        organizationMapper.removeAllOrganizationTest();
        organizationMapper.addOrganization(organization1);
        organizationMapper.addOrganization(organization2);
        Assertions.assertEquals(2,organizationMapper.findAllOrganization().size());

        /**
         * 删除
         */
        organizationMapper.removeOrganizationById(organizationMapper.findAllOrganization().get(0).getId(),new Date());
        Assertions.assertEquals(1,organizationMapper.findAllOrganization().size());

        /**
         * 更新
         */
        Organization organization3=organizationMapper.findAllOrganization().get(0);
        organization3.setWebsiteUrl("test3");
        organizationMapper.updateOrganizationById(organization3);
        Assertions.assertEquals("test3",organizationMapper.findAllOrganization().get(0).getWebsiteUrl());

        organizationMapper.removeAllOrganization(new Date());
        Assertions.assertEquals(0,organizationMapper.findAllOrganization().size());
    }



}
