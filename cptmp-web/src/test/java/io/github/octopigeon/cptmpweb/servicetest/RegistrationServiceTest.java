//package io.github.octopigeon.cptmpweb.servicetest;
//
//import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
//import io.github.octopigeon.cptmpdao.model.CptmpUser;
//import io.github.octopigeon.cptmpservice.constantclass.RoleEnum;
//import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
//import io.github.octopigeon.cptmpservice.dto.cptmpuser.EnterpriseAdminInfoDTO;
//import io.github.octopigeon.cptmpservice.dto.cptmpuser.StudentInfoDTO;
//import io.github.octopigeon.cptmpservice.dto.cptmpuser.TeacherInfoDTO;
//import io.github.octopigeon.cptmpservice.service.userinfo.RegistrationService;
//import io.github.octopigeon.cptmpweb.BaseTest;
//import org.apache.commons.lang.RandomStringUtils;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author 李国豪
// * @version 1.0
// * @date 2020/7/9
// * @last-check-in 李国豪
// * @date 2020/7/9
// */
//public class RegistrationServiceTest extends BaseTest {
//    @Autowired(required = false)
//    private RegistrationService registrationService;
//    @Autowired
//    private CptmpUserMapper cptmpUserMapper;
//
//    @Test
//    public void validateCodeTest()
//    {
//        BaseUserInfoDTO userInfo = new TeacherInfoDTO();
//        initData(userInfo, "123456", RoleEnum.ROLE_SCHOOL_TEACHER);
//        boolean result = registrationService.validateInvitationCode(userInfo);
//        Assertions.assertFalse(result);
//
//        initData(userInfo, "asdadd", RoleEnum.ROLE_SCHOOL_TEACHER);
//        result = registrationService.validateInvitationCode(userInfo);
//        Assertions.assertTrue(result);
//
//        initData(userInfo,"wqeqew", RoleEnum.ROLE_SCHOOL_TEACHER);
//        result = registrationService.validateInvitationCode(userInfo);
//        Assertions.assertTrue(result);
//
//        userInfo = new EnterpriseAdminInfoDTO();
//        initData(userInfo, "ewrwrw", RoleEnum.ROLE_ENTERPRISE_ADMIN);
//        result = registrationService.validateInvitationCode(userInfo);
//        Assertions.assertFalse(result);
//    }
//
//    @Test
//    public void personalRegistrationTest() throws Exception {
//        BaseUserInfoDTO userInfo = new TeacherInfoDTO();
//        initData(userInfo, "asdadd", RoleEnum.ROLE_SCHOOL_TEACHER);
//        TeacherInfoDTO teacherInfo = (TeacherInfoDTO)userInfo;
//        teacherInfo.setEmployeeId(BigInteger.valueOf(1223455L).toString());
//        teacherInfo.setName("赵春华");
//        teacherInfo.setSchoolName("武汉大学");
//        if(registrationService.validateInvitationCode(userInfo)){
//            registrationService.personalRegistration(userInfo);
//        }
//        List<CptmpUser> results = cptmpUserMapper.findAllUsers();
//        Assertions.assertEquals(8, results.size());
//    }
//
//    @Test
//    public void bulkRegistrationTest() throws Exception {
//        List<BaseUserInfoDTO> userInfos = new ArrayList<>();
//        userInfos = initDatas(userInfos);
//        registrationService.bulkRegistration(userInfos);
//        List<CptmpUser> results = cptmpUserMapper.findAllUsers();
//        Assertions.assertEquals(12, results.size());
//    }
//
//    private BaseUserInfoDTO initData(BaseUserInfoDTO userInfo, String code, RoleEnum role)
//    {
//        userInfo.setEmail("55666@qq.com");
//        userInfo.setRoleName(role.name());
//        userInfo.setUsername("lisa");
//        userInfo.setPassword("123456");
//        userInfo.setInvitationCode(code);
//        return userInfo;
//    }
//
//    private List<BaseUserInfoDTO> initDatas(List<BaseUserInfoDTO> userInfos)
//    {
//        for (int i=0; i<5; i++)
//        {
//            StudentInfoDTO userInfo = new StudentInfoDTO();
//            userInfo.setEmail("55666@qq.com");
//            userInfo.setRoleName(RoleEnum.ROLE_STUDENT_MEMBER.name());
//            userInfo.setUsername(RandomStringUtils.randomAlphabetic(3));
//            userInfo.setPassword("123456");
//            userInfo.setName("赵春华");
//            userInfo.setSchoolName("武汉大学");
//            userInfo.setStudentId(BigInteger.valueOf((long) (Math.random() * 1000)).toString());
//            userInfos.add(userInfo);
//        }
//        return userInfos;
//    }
//}
