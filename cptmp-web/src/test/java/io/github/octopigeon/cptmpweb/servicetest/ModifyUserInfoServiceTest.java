package io.github.octopigeon.cptmpweb.servicetest;

import io.github.octopigeon.cptmpservice.dto.cptmpuser.EnterpriseAdminInfoDTO;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.StudentInfoDTO;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.TeacherInfoDTO;
import io.github.octopigeon.cptmpservice.service.userinfo.UserInfoService;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.math.BigInteger;

/**
 * 个人信息修改测试
 * @author Ruby
 * @version 1.0
 * @date 2020/07/10
 * @last-check-in Ruby
 * @date 2020/07/10
 */
public class ModifyUserInfoServiceTest extends BaseTest {

    @Autowired
    private UserInfoService userInfoService;


    @Test
    @Rollback(false)
    public void ModifyStudentServiceTest() throws Exception {
        StudentInfoDTO studentInfo = new StudentInfoDTO();
        studentInfo.setUserId(BigInteger.ONE);
        studentInfo.setUsername("key");
        studentInfo.setGender(false);
        studentInfo.setRoleName("ROLE_STUDENT_MEMBER");
        studentInfo.setIntroduction("不错不错");
        studentInfo.setNickname("manta");
        studentInfo.setSchoolName("清华大学");
        studentInfo.setName("testname");
        studentInfo.setStudentFace("face");
        studentInfo.setStudentId("1");
        userInfoService.modifyUserInfo(studentInfo);
    }

    @Test
    @Rollback(false)
    public void ModifyTeacherServiceTest() throws Exception {
        TeacherInfoDTO teacherInfo = new TeacherInfoDTO();
        teacherInfo.setUserId(BigInteger.valueOf(2));
        teacherInfo.setUsername("test1");
        teacherInfo.setGender(false);
        teacherInfo.setRoleName("ROLE_SCHOOL_TEACHER");
        teacherInfo.setIntroduction("不错");
        teacherInfo.setNickname("manta");
        teacherInfo.setSchoolName("武汉大学");
        teacherInfo.setName("testname");
        teacherInfo.setEmployeeId("33");
        userInfoService.modifyUserInfo(teacherInfo);
    }

    @Test
    @Rollback(false)
    public void ModifyEnterpriseAdminInfoServiceTest() throws Exception {
        EnterpriseAdminInfoDTO enterpriseAdminInfo = new EnterpriseAdminInfoDTO();
        enterpriseAdminInfo.setUserId(BigInteger.valueOf(3));
        enterpriseAdminInfo.setUsername("test2");
        enterpriseAdminInfo.setGender(true);
        enterpriseAdminInfo.setRoleName("ROLE_ENTERPRISE_ADMIN");
        enterpriseAdminInfo.setIntroduction("我是员工");
        enterpriseAdminInfo.setNickname("manta");
        enterpriseAdminInfo.setName("员工二号");
        enterpriseAdminInfo.setEmployeeId("22");
        userInfoService.modifyUserInfo(enterpriseAdminInfo);
    }
}
