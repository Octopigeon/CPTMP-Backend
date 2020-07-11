package io.github.octopigeon.cptmpservice.service;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.mapper.EnterpriseAdminMapper;
import io.github.octopigeon.cptmpdao.mapper.SchoolInstructorMapper;
import io.github.octopigeon.cptmpdao.mapper.SchoolStudentMapper;
import io.github.octopigeon.cptmpdao.model.CptmpUser;
import io.github.octopigeon.cptmpservice.dto.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.EnterpriseAdminInfoDTO;
import io.github.octopigeon.cptmpservice.dto.StudentInfoDTO;
import io.github.octopigeon.cptmpservice.dto.TeacherInfoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.github.octopigeon.cptmpservice.RoleEnum;

import java.math.BigInteger;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * 用于处理前端对用户信息的请求(/api/me)
 * @last-check-in anlow
 * @date 2020/7/11
 */
@Service
public class UserInfoService {

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Autowired
    private EnterpriseAdminMapper enterpriseAdminMapper;

    @Autowired
    private SchoolStudentMapper schoolStudentMapper;

    @Autowired
    private SchoolInstructorMapper schoolInstructorMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户名得到用户基本信息，以及角色类型
     * @param username 用户名
     * @return 返回一个父抽象类型，然后controller根据roleName转换成相应的子类
     */
    public BaseUserInfoDTO findBaseUserInfoByUsername(String username) {
        BaseUserInfoDTO baseUserInfoDTO;
        CptmpUser cptmpUser = cptmpUserMapper.findUserByUsername(username);
        String roleName = cptmpUser.getRoleName();
        BigInteger userId = cptmpUser.getId();
        // TODO 增加系统管理员
        if (roleName.equals(RoleEnum.ROLE_ENTERPRISE_ADMIN.name())) {
            EnterpriseAdminInfoDTO enterpriseAdminInfoDTO = new EnterpriseAdminInfoDTO();
            BeanUtils.copyProperties(cptmpUser, enterpriseAdminInfoDTO);
            BeanUtils.copyProperties(enterpriseAdminMapper.findEnterpriseAdminByUserId(userId)
                    , enterpriseAdminInfoDTO);
            baseUserInfoDTO = enterpriseAdminInfoDTO;
        } else if (roleName.equals(RoleEnum.ROLE_SCHOOL_ADMIN.name())
        || roleName.equals(RoleEnum.ROLE_SCHOOL_TEACHER.name())) {
            TeacherInfoDTO teacherInfoDTO = new TeacherInfoDTO();
            BeanUtils.copyProperties(cptmpUser, teacherInfoDTO);
            BeanUtils.copyProperties(schoolInstructorMapper.findSchoolInstructorByUserId(userId)
                    , teacherInfoDTO);
            baseUserInfoDTO = teacherInfoDTO;
        } else if (roleName.equals(RoleEnum.ROLE_STUDENT_MASTER.name())
        || roleName.equals(RoleEnum.ROLE_STUDENT_MEMBER.name())
        || roleName.equals(RoleEnum.ROLE_STUDENT_PM.name())
        || roleName.equals(RoleEnum.ROLE_STUDENT_PO.name())) {
            StudentInfoDTO studentInfoDTO = new StudentInfoDTO();
            BeanUtils.copyProperties(cptmpUser, studentInfoDTO);
            BeanUtils.copyProperties(schoolStudentMapper.findSchoolStudentByUserId(userId)
                    , studentInfoDTO);
            baseUserInfoDTO = studentInfoDTO;
        } else {
            baseUserInfoDTO = new BaseUserInfoDTO() {
            };
        }
        return baseUserInfoDTO;
    }

    /**
     * 用于验证用户输入的原密码是否正确
     * @param username 用户名
     * @param originPassword 用户输入的原密码
     * @return 是否正确
     */
    public Boolean validateOriginPassword(String username, String originPassword) {
        return passwordEncoder.matches(originPassword, cptmpUserMapper.findPasswordByUsername(username));
    }

    public void updatePassword(String username, String newPassword) {
        String newPasswordEncoded = new CptmpUser().updatePassword(newPassword).getPassword();
        cptmpUserMapper.updatePasswordByUsername(username, newPasswordEncoded);
    }

}
