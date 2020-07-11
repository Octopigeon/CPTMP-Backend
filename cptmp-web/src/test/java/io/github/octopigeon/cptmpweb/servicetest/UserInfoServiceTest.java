package io.github.octopigeon.cptmpweb.servicetest;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.mapper.EnterpriseAdminMapper;
import io.github.octopigeon.cptmpdao.mapper.SchoolInstructorMapper;
import io.github.octopigeon.cptmpdao.mapper.SchoolStudentMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.EnterpriseAdmin;
import io.github.octopigeon.cptmpdao.model.SchoolInstructor;
import io.github.octopigeon.cptmpdao.model.SchoolStudent;
import io.github.octopigeon.cptmpservice.RoleEnum;
import io.github.octopigeon.cptmpservice.dto.EnterpriseAdminInfoDTO;
import io.github.octopigeon.cptmpservice.dto.StudentInfoDTO;
import io.github.octopigeon.cptmpservice.dto.TeacherInfoDTO;
import io.github.octopigeon.cptmpservice.service.UserInfoService;
import io.github.octopigeon.cptmpweb.BaseTest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in anlow
 * @date 2020/7/10
 */
public class UserInfoServiceTest extends BaseTest {

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Autowired
    private EnterpriseAdminMapper enterpriseAdminMapper;

    @Autowired
    private SchoolStudentMapper schoolStudentMapper;

    @Autowired
    private SchoolInstructorMapper schoolInstructorMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void test() {
        CptmpUser cptmpUser = new CptmpUser();
        cptmpUser.setGmtCreate(new Date());
        cptmpUser.setUsername("test1");
        cptmpUser.updatePassword("123456");
        cptmpUser.setEmail("111@11.com");
        cptmpUser.setNickname("aaa");
        cptmpUser.setRoleName(RoleEnum.ROLE_SCHOOL_TEACHER.name());
        cptmpUser.setEnabled(true);
        cptmpUser.setAccountNonExpired(true);
        cptmpUser.setCredentialsNonExpired(true);
        cptmpUser.setAccountNonLocked(true);
        cptmpUserMapper.removeAllUsers();
        // 测试老师
        cptmpUserMapper.addUser(cptmpUser);
        SchoolInstructor schoolInstructor = new SchoolInstructor();
        schoolInstructor.setGmtCreate(new Date());
        schoolInstructor.setEmployeeId("123");
        schoolInstructor.setName("wxc");
        schoolInstructor.setSchoolName("wasdgf");
        schoolInstructor.setUserId(cptmpUserMapper.findUserByUsername("test1").getId());
        schoolInstructorMapper.addSchoolInstructor(schoolInstructor);
        TeacherInfoDTO teacherInfoDTO = (TeacherInfoDTO)  userInfoService.findBaseUserInfoByUsername("test1");
        Assertions.assertEquals("wxc", teacherInfoDTO.getName());
        Assertions.assertEquals("123", teacherInfoDTO.getEmployeeId());
        Assertions.assertEquals("wasdgf", teacherInfoDTO.getSchoolName());
        Assertions.assertEquals(cptmpUserMapper.findUserByUsername("test1").getId(), teacherInfoDTO.getUserId());
        // 测试学生
        cptmpUser.setUsername("test2");
        cptmpUser.setEmail("123@adsf.com");
        cptmpUser.setRoleName(RoleEnum.ROLE_STUDENT_MEMBER.name());
        cptmpUserMapper.addUser(cptmpUser);
        SchoolStudent schoolStudent = new SchoolStudent();
        schoolStudent.setGmtCreate(new Date());
        schoolStudent.setName("lgp");
        schoolStudent.setSchoolName("wasdgf");
        schoolStudent.setStudentId("2143241");
        schoolStudent.setUserId(cptmpUserMapper.findUserByUsername("test2").getId());
        schoolStudentMapper.addSchoolStudent(schoolStudent);
        StudentInfoDTO studentInfoDTO = (StudentInfoDTO) userInfoService.findBaseUserInfoByUsername("test2");
        Assertions.assertEquals("lgp", studentInfoDTO.getName());
        Assertions.assertEquals("2143241", studentInfoDTO.getStudentId());
        Assertions.assertEquals("wasdgf", studentInfoDTO.getSchoolName());
        Assertions.assertEquals(cptmpUserMapper.findUserByUsername("test2").getId(), studentInfoDTO.getUserId());
        // 测试企业管理员
        cptmpUser.setUsername("test3");
        cptmpUser.setEmail("143@adsf.com");
        cptmpUser.setRoleName(RoleEnum.ROLE_ENTERPRISE_ADMIN.name());
        cptmpUserMapper.addUser(cptmpUser);
        EnterpriseAdmin enterpriseAdmin = new EnterpriseAdmin();
        enterpriseAdmin.setGmtCreate(new Date());
        enterpriseAdmin.setEmployeeId("sdafasdf");
        enterpriseAdmin.setName("lhr");
        enterpriseAdmin.setUserId(cptmpUserMapper.findUserByUsername("test3").getId());
        enterpriseAdminMapper.addEnterpriseAdmin(enterpriseAdmin);
        EnterpriseAdminInfoDTO enterpriseAdminInfoDTO = (EnterpriseAdminInfoDTO) userInfoService.findBaseUserInfoByUsername("test3");
        Assertions.assertEquals("lhr", enterpriseAdminInfoDTO.getName());
        Assertions.assertEquals("sdafasdf", enterpriseAdminInfoDTO.getEmployeeId());
        Assertions.assertEquals(cptmpUserMapper.findUserByUsername("test3").getId(), enterpriseAdminInfoDTO.getUserId());
    }

}