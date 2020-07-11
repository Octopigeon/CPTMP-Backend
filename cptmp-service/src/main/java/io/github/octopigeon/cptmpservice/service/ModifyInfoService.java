package io.github.octopigeon.cptmpservice.service;


import io.github.octopigeon.cptmpservice.dto.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.EnterpriseAdminInfoDTO;
import io.github.octopigeon.cptmpservice.dto.StudentInfoDTO;
import io.github.octopigeon.cptmpservice.dto.TeacherInfoDTO;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 更改个人信息服务接口
 * @author Ruby
 * @version 1.0
 * @date 2020/07/10
 * @last-check-in Ruby
 * @date 2020/07/10
 */


public interface ModifyInfoService {

    /**
     * 修改用户信息
     * @param baseUserInfo 修改后的用户信息
     * @return
     */
    int modifyUserInfo(BaseUserInfoDTO baseUserInfo) throws Exception;

    /**
     * 修改学生信息
     * @param studentInfo 修改后的学生信息
     * @return
     * 1:修改成功
     */
    int modifyStudentInfo(StudentInfoDTO studentInfo) throws Exception;

    /**
     * 修改教师信息
     * @param teacherInfo 修改后的教师信息
     * @return
     * 1:修改成功
     */
    int modifyTeacherInfo(TeacherInfoDTO teacherInfo) throws Exception;

    /**
     * 修改企业人员信息
     * @param enterpriseAdminInfo 修改后的企业人员信息
     * @return
     * 1:修改成功
     */
    int modifyEnterpriseAdminInfo(EnterpriseAdminInfoDTO enterpriseAdminInfo) throws Exception;
    }
