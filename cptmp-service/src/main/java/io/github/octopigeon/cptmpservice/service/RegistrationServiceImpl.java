package io.github.octopigeon.cptmpservice.service;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.mapper.EnterpriseAdminMapper;
import io.github.octopigeon.cptmpdao.mapper.SchoolInstructorMapper;
import io.github.octopigeon.cptmpdao.mapper.SchoolStudentMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpdao.model.EnterpriseAdmin;
import io.github.octopigeon.cptmpdao.model.SchoolInstructor;
import io.github.octopigeon.cptmpdao.model.SchoolStudent;
import io.github.octopigeon.cptmpservice.dto.BaseUserInfoDTO;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import io.github.octopigeon.cptmpservice.dto.EnterpriseAdminInfoDTO;
import io.github.octopigeon.cptmpservice.dto.StudentInfoDTO;
import io.github.octopigeon.cptmpservice.dto.TeacherInfoDTO;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.apache.commons.lang.*;

/**
 * 批量注册服务具体实现
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in Gh Li
 * @date 2020/7/8
 */
public class RegistrationServiceImpl implements RegistrationService {

    private CptmpUserMapper cptmpUserMapper;
    private EnterpriseAdminMapper enterpriseAdminMapper;
    private SchoolStudentMapper schoolStudentMapper;
    private SchoolInstructorMapper schoolInstructorMapper;

    /**
     * 个体注册
     * 默认生成项变成必填
     * @param userInfo ：用户
     * @return 注册之后的用户信息
     */
    @Override
    public BaseUserInfoDTO personalRegistration(BaseUserInfoDTO userInfo) throws Exception {

        try
        {
            BaseUserInfoDTO parsedUserInfo = parseUserInfo(userInfo);
            CptmpUser user = userInfoToUser(parsedUserInfo);
            //添加用户
            cptmpUserMapper.addUser(user);
            BigInteger userId = cptmpUserMapper.findUserByUsername(user.getUsername()).getId();
            parsedUserInfo.setUserId(userId);
            //添加角色
            addRole(parsedUserInfo);
            return parsedUserInfo;
        }catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 批量注册
     *
     * @param userInfos ：待注册列表
     * @return 注册好的列表
     */
    @Override
    public List<BaseUserInfoDTO> bulkRegistration(List<BaseUserInfoDTO> userInfos) throws Exception{
        try
        {
            for (BaseUserInfoDTO userInfo : userInfos) {
                int index = userInfos.indexOf(userInfo);
                userInfos.set(index, personalRegistration(userInfo));
            }
            return userInfos;
        }catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 为userInfo添加默认值
     * @param userInfo userInfo类
     * @return userInfo类
     */
    private BaseUserInfoDTO parseUserInfo(BaseUserInfoDTO userInfo) throws ValueException
    {
        if(userInfo.getPassword() == null)
        {
            userInfo.setPassword(productPassword());
        }
        if(userInfo.getUserName() == null)
        {
            userInfo.setUserName(productUserName());
        }
        if(!validateEmailFormat(userInfo.getEmail()))
        {
            throw new ValueException("E-mail format is invalidation");
        }
        if(userInfo.getPhoneNum()!=null && validatePhoneNumberFormat(userInfo.getPhoneNum().toString()))
        {
            throw new ValueException("Phone number format is invalidation");
        }
        return userInfo;
    }

    /**
     * 默认产生五位数随机密码
     * @return 随机密码
     */
    private String productPassword()
    {
        return RandomStringUtils.randomAlphanumeric(5);
    }

    /**
     * 产生8位字母命名
     * @return 随机用户名
     */
    private String productUserName()
    {
        return RandomStringUtils.randomAlphabetic(8);
    }

    private Boolean validateEmailFormat(String email)
    {
        String check = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4})*$";
        boolean tag = true;
        if (!email.matches(check)) {
            tag = false;
        }
        return tag;
    }

    private Boolean validatePhoneNumberFormat(String phoneNumber)
    {
        String check = "^(?:\\+?86)?1(?:3(?:4[^9\\D]|[5-9]\\d)|5[^3-6\\D]\\d|7[28]\\d|8[23478]\\d|9[578]\\d)\\d{7}$";
        boolean tag = true;
        if (!phoneNumber.matches(check)) {
            tag = false;
        }
        return tag;
    }

    /**
     * 将BaseUserInfoDTO转化成CptmpUser
     * @param userInfo；BaseUserInfoDTO类
     * @return CptmpUser
     */
    private CptmpUser userInfoToUser(BaseUserInfoDTO userInfo)
    {
        CptmpUser user = new CptmpUser();
        user.setEmail(userInfo.getEmail());
        user.setRoleName(userInfo.getRoleName());
        user.setPassword(userInfo.getPassword());
        user.setPhoneNumber(userInfo.getPhoneNum());
        user.setMale(userInfo.getGender());
        user.setUsername(userInfo.getUserName());
        user.setGmtCreate(new Date());
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        // TODO 默认头像链接待定
        user.setAvatar("");
        return user;
    }

    /**
     * 进行角色的添加
     * @param userInfo BaseUserInfoDTO类
     */
    private void addRole(BaseUserInfoDTO userInfo)
    {
        String roleName = userInfo.getRoleName();
        // TODO 等待常量类的定义
        // 学生
        if(roleName.equals("ROLE_STUDENT_MEMBER"))
        {
            addStudentRole(userInfo);
        }
        // 企业管理员
        else if(roleName.equals("ROLE_ENTERPRISE_ADMIN"))
        {
            addEnterpriseAdminRole(userInfo);
        }
        // 老师
        else if(roleName.equals("ROLE_SCHOOL_TEACHER"))
        {
            addTeacherRole(userInfo);
        }
    }

    private void addStudentRole(BaseUserInfoDTO userInfo)
    {
        StudentInfoDTO studentInfo = (StudentInfoDTO)userInfo;
        SchoolStudent student = new SchoolStudent();
        student.setStudentId(studentInfo.getStudentId());
        student.setName(studentInfo.getName());
        student.setSchoolName(studentInfo.getSchoolName());
        student.setUserId(studentInfo.getUserId());
        student.setGmtCreate(new Date());
        schoolStudentMapper.addSchoolStudent(student);
    }

    private void addEnterpriseAdminRole(BaseUserInfoDTO userInfo)
    {
        EnterpriseAdminInfoDTO adminInfo = (EnterpriseAdminInfoDTO)userInfo;
        EnterpriseAdmin admin = new EnterpriseAdmin();
        admin.setEmployeeId(adminInfo.getEmployeeId());
        admin.setName(adminInfo.getName());
        admin.setUserId(adminInfo.getUserId());
        admin.setGmtCreate(new Date());
        enterpriseAdminMapper.addEnterpriseAdmin(admin);
    }

    private void addTeacherRole(BaseUserInfoDTO userInfo)
    {
        TeacherInfoDTO teacherInfo = (TeacherInfoDTO)userInfo;
        SchoolInstructor teacher = new SchoolInstructor();
        teacher.setEmployeeId(teacherInfo.getEmployeeId());
        teacher.setName(teacherInfo.getName());
        teacher.setSchoolName(teacherInfo.getSchoolName());
        teacher.setUserId(teacherInfo.getUserId());
        teacher.setGmtCreate(new Date());
        schoolInstructorMapper.addSchoolInstructor(teacher);
    }
}
