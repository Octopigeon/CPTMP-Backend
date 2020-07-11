package io.github.octopigeon.cptmpservice.service;

import io.github.octopigeon.cptmpdao.mapper.CptmpUserMapper;
import io.github.octopigeon.cptmpdao.mapper.EnterpriseAdminMapper;
import io.github.octopigeon.cptmpdao.mapper.SchoolInstructorMapper;
import io.github.octopigeon.cptmpdao.mapper.SchoolStudentMapper;
import io.github.octopigeon.cptmpservice.dto.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.EnterpriseAdminInfoDTO;
import io.github.octopigeon.cptmpservice.dto.StudentInfoDTO;
import io.github.octopigeon.cptmpservice.dto.TeacherInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 更改个人信息服务实现类
 * @author Ruby
 * @version 1.0
 * @date 2020/07/10
 * @last-check-in anlow
 * @date 2020/07/11
 */

@Service
public class ModifyInfoServiceImpl implements ModifyInfoService{
    @Autowired
    private CptmpUserMapper cptmpUserMapper;
    @Autowired
    private EnterpriseAdminMapper enterpriseAdminMapper;
    @Autowired
    private SchoolStudentMapper schoolStudentMapper;
    @Autowired
    private SchoolInstructorMapper schoolInstructorMapper;


    /**
     * 修改用户信息
     *
     * @param baseUserInfo 修改后的用户信息
     * @return
     * 1:修改成功
     * 0:id或用户名为空
     */
    @Override
    public int modifyUserInfo(BaseUserInfoDTO baseUserInfo) throws Exception {

        if(baseUserInfo.getUserId()==null|| "".equals(baseUserInfo.getUsername()))
        {
            return 0;
        }

        try{
            cptmpUserMapper.updateUserInfoByUsername(baseUserInfo.getUsername(),baseUserInfo.getNickname(),
                    new Date(),baseUserInfo.getIntroduction(),baseUserInfo.getMale());
            return 1;
        }
        catch(Exception e)
        {
            throw new Exception(e);
        }

    }

    /**
     * 修改学生信息
     * @param studentInfo 修改后的学生信息
     * @return
     * 1:修改成功
     */
    @Override
    public int modifyStudentInfo(StudentInfoDTO studentInfo) throws Exception {

        try{
            schoolStudentMapper.updateSchoolStudetnByUserId(studentInfo.getUserId(),new Date(),
                    studentInfo.getName(),studentInfo.getStudentId(),studentInfo.getSchoolName(),studentInfo.getStudentFace());

            return 1;

        }catch(Exception e)
        {
            throw new Exception(e);
        }
    }

    /**
     * 修改教师信息
     * @param teacherInfo 修改后的教师信息
     * @return
     * 1:修改成功
     */
    @Override
    public int modifyTeacherInfo(TeacherInfoDTO teacherInfo) throws Exception {

        try{
            schoolInstructorMapper.updateSchoolInstructorByUserId(teacherInfo.getUserId(),new Date(),
                    teacherInfo.getName(),teacherInfo.getEmployeeId(),teacherInfo.getSchoolName());

            return 1;

        }catch(Exception e)
        {
            throw new Exception(e);
        }
    }

    /**
     * 修改企业人员信息
     * @param enterpriseAdminInfo 修改后的企业人员信息
     * @return
     * 1:修改成功
     */
    @Override
    public int modifyEnterpriseAdminInfo(EnterpriseAdminInfoDTO enterpriseAdminInfo) throws Exception {

        try{
            enterpriseAdminMapper.updateEnterprseAdminByUserId(enterpriseAdminInfo.getUserId(),new Date(),
                    enterpriseAdminInfo.getName(),enterpriseAdminInfo.getEmployeeId());
            return 1;

        }catch(Exception e)
        {
            throw new Exception(e);
        }
    }
}
