package io.github.octopigeon.cptmpweb.mappertest;

import io.github.octopigeon.cptmpdao.mapper.EnterpriseAdminMapper;
import io.github.octopigeon.cptmpdao.model.EnterpriseAdmin;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/8
 */
public class EnterpriseAdminMapperTest extends BaseTest  {
    @Autowired
    private EnterpriseAdminMapper enterpriseAdminMapper;

    @Test
    public void test()
    {
        initData();
        List<EnterpriseAdmin> admins;

        admins = enterpriseAdminMapper.findAllEnterpriseAdmins();
        Assertions.assertEquals(2, admins.size());

        EnterpriseAdmin admin = enterpriseAdminMapper.findEnterpriseAdminByUserId(new BigInteger(String.valueOf(1)));
        admin.setEmployeeId(new BigInteger(String.valueOf(32424)));
        enterpriseAdminMapper.updateEnterpriseAdminByUserId(admin.getUserId(), new Date(), admin.getName(), admin.getEmployeeId());

        enterpriseAdminMapper.removeEnterpriseAdminByUserId(new BigInteger(String.valueOf(2)));
        admins = enterpriseAdminMapper.findAllEnterpriseAdmins();
        Assertions.assertEquals(1, admins.size());
    }

    /**
     * 假设user表中已经存在id为1和2的用户
     */
    @Test
    public void initData()
    {
        EnterpriseAdmin admin1 = new EnterpriseAdmin();
        admin1.setGmtCreate(new Date());
        admin1.setUserId(new BigInteger(String.valueOf(1)));
        admin1.setName("lisa");
        admin1.setEmployeeId(new BigInteger(String.valueOf(12345)));

        EnterpriseAdmin admin2 = new EnterpriseAdmin();
        admin2.setGmtCreate(new Date());
        admin2.setUserId(new BigInteger(String.valueOf(2)));
        admin2.setName("mary");
        admin2.setEmployeeId(new BigInteger(String.valueOf(123445)));

        enterpriseAdminMapper.addEnterpriseAdmin(admin1);
        enterpriseAdminMapper.addEnterpriseAdmin(admin2);
    }
}
